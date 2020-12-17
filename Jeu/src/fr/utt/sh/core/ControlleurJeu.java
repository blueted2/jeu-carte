/**
 * 
 */
package fr.utt.sh.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import fr.utt.sh.console_ui.VisitorAffichageString;
import fr.utt.sh.core.score.VisitorComptageScore;
import fr.utt.sh.core.score.VisitorComptageScoreStandard;
import fr.utt.sh.core.strategy.StrategyJoueurConsole;
import fr.utt.sh.core.strategy.StrategyTest;
import fr.utt.sh.core.tapis.Tapis;
import fr.utt.sh.core.tapis.Tapis.TypeTapis;
import fr.utt.sh.core.tapis.Tapis_5x3;
import fr.utt.sh.core.tapis.Tapis_Triangulaire;
import fr.utt.sh.core.tapis.Tapis_Rectangulaire;

import java.util.HashMap;

/**
 * Cette classe singleton se charge de controller le flux general du jeu, ainsi
 * de la logique des règles.
 * 
 * @author grego
 *
 */
public class ControlleurJeu {

	private static ControlleurJeu instance;

	private ArrayList<Carte>  cartesRestantes;
	private ArrayList<Joueur> joueurs;
	private Iterator<Joueur>  iteratorJoueurs;

	private Tapis tapis;

	private Joueur joueurActuel;

	private Regles regles;

	private boolean debutPartie                     = false;
	private boolean joueurActuelAPoseCarteCeTour    = false;
	private boolean joueurActuelAPiocheCarteCeTour  = false;
	private boolean joueurActuelADeplaceCarteCeTour = false;
	private boolean cartesVictoiresDistribues       = false;

	private ControlleurJeu() {
		cartesRestantes = new ArrayList<Carte>();
		joueurs         = new ArrayList<Joueur>();
	}

	public static boolean nombreDeJoueursValide(int joueursHumains, int joueursBots) {
		if (joueursHumains < 0 || joueursBots < 0)
			return false;

		if (joueursHumains > 3 || joueursBots > 3)
			return false;

		if (joueursBots + joueursHumains > 3)
			return false;

		if (joueursBots + joueursHumains < 2)
			return false;

		return true;
	}

	/**
	 * @return Donne l'instance de {@link ControlleurJeu}.
	 */
	public static ControlleurJeu getInstance() {
		if (instance == null) {
			instance = new ControlleurJeu();
		}
		return instance;

	}

	private void genererCartes() {
		cartesRestantes = new ArrayList<Carte>();
		for (Carte.Remplissage remplissage : Carte.Remplissage.values()) {
			for (Carte.Couleur couleur : Carte.Couleur.values()) {
				for (Carte.Forme forme : Carte.Forme.values()) {
					cartesRestantes.add(new Carte(couleur, remplissage, forme));
				}
			}
		}
	}

	private void genererJoueurs(int nombreDeJoueursHumains, int nombreDeJoueuersBots) {
		joueurs = new ArrayList<Joueur>();

		int nombreBotsAjoutes    = 0;
		int nombreHumainsAjoutes = 0;

		while (nombreBotsAjoutes + nombreHumainsAjoutes < nombreDeJoueuersBots + nombreDeJoueursHumains) {
			if (nombreBotsAjoutes < nombreDeJoueuersBots) {
				joueurs.add(new Joueur("Bot_" + nombreBotsAjoutes, new StrategyTest()));
				nombreBotsAjoutes++;
			}

			if (nombreHumainsAjoutes < nombreDeJoueursHumains) {
				joueurs.add(new Joueur("Humain_" + nombreHumainsAjoutes, new StrategyJoueurConsole()));
				nombreHumainsAjoutes++;
			}

			if (nombreBotsAjoutes < nombreDeJoueuersBots) {
				joueurs.add(new Joueur("Bot_" + nombreBotsAjoutes, new StrategyTest()));
				nombreBotsAjoutes++;
			}
		}

		return;
//		joueurs = new ArrayList<Joueur>();
//		for (int i = 0; i < nombreDeJoueurs; i++) {
//			joueurs.add(new Joueur(Integer.toString(i)));
////			joueurs.add(new Joueur());
//		}

	}

	/**
	 * 
	 * Commencer une nouvelle partie, et supprime celle deja en cours. Le nombre
	 * total de joueurs doit etre soit 2 ou 3, donc les combinaisons possibles de
	 * joueurs humains et bots sonts:
	 * 
	 * <pre>
	 * 0, 2
	 * 0, 3
	 * 1, 1
	 * 1, 2
	 * 2, 0
	 * 2, 1
	 * </pre>
	 * 
	 * Si incorrect, les nombres de joueurs seront corrigés.
	 * 
	 * 
	 * @param nbHumains Le nombre de joueurs humains.
	 * @param nbBots    Le nombre de joueurs bots.
	 * @param regles
	 * @param typeTapis
	 * @param largeur
	 * @param hauteur
	 */
	public void commencerNouvellePartie(int nbHumains, int nbBots, Regles regles, TypeTapis typeTapis, int largeur,
			int hauteur) {

		// Assurer que le nombre de joueurs soit correct.
		nbHumains = Math.max(0, nbHumains);
		nbHumains = Math.min(3, nbHumains);

		nbBots = Math.max(0, nbBots);
		nbBots = Math.min(3, nbBots);

		if (nbBots + nbHumains > 3)
			nbBots = 0;

		if (nbBots + nbHumains < 2)
			nbBots = 2 - (nbBots + nbHumains);

		this.regles = regles;

		switch (typeTapis) {
			case Triangulaire:
				tapis = new Tapis_Triangulaire(largeur);
				break;
			case Rectangulaire:
				tapis = new Tapis_Rectangulaire(largeur, hauteur);
				break;
			default:
				tapis = new Tapis_5x3();
				break;
		}

		genererJoueurs(nbHumains, nbBots);
		iteratorJoueurs = joueurs.iterator();

		debutPartie = true;

		// Au lieur d'aller chercher toutes les cartes chez les joueurs, simplement les
		// recreers.
		genererCartes();

		// Au debut de la partie, il faut jeter une carte.
		popCarteAleatoire();

		switch (regles) {
			case Standard:
				distribuerCartesVictoires();
				break;
			case Advanced:
				distribuerCartesDansMain();
				break;
			case Autre:
				throw new UnsupportedOperationException("regles par implémenté");
			default:
				break;

		}

		passerAuJoueurSuivant();
	}

	/**
	 * Vérifie si le joueur actuel a fini son tour, puis passe au joueur suivant.
	 * 
	 * @return {@code true} si on a pu passer au joueur suivant, {@code false}
	 *         sinon.
	 */
	public boolean passerAuJoueurSuivant() {

		// Si c'est le debut de la partie, on a pas besoin de verifier si le joueur
		// actuel a deja pioché/poser une carte...
		//
		if (!debutPartie) {
			if (!joueurActuelAPiocheCarteCeTour && ilResteDesCartes())
				return false;

			if (!joueurActuelAPoseCarteCeTour)
				return false;
		} else
			debutPartie = false;

		// Si on est a la fin de l'iterator, en créer un nouveau.
		if (!iteratorJoueurs.hasNext())
			iteratorJoueurs = joueurs.iterator();

		joueurActuel = iteratorJoueurs.next();

		joueurActuelAPiocheCarteCeTour  = false;
		joueurActuelAPoseCarteCeTour    = false;
		joueurActuelADeplaceCarteCeTour = false;

		System.out.println("-------------------------------------");
		System.out.println(String.format("A %s de jouer.", joueurActuel));

		afficherTapis();

		switch (regles) {
			case Standard:
				Carte carteVictoire = joueurActuel.getCarteVictoire();
				String stringCarte = carteVictoire.getStringCarte();

				System.out.println(String.format("Carte victoire: |%s|", stringCarte));
				break;

			case Advanced:
				afficherMainJoueurActuel();
				break;

			case Autre:
				break;
			default:
				break;
		}

		return true;
	}

	/**
	 * Obtenir une copie du {@link Joueur} actuel. Ce joueur est une copie pour la
	 * meme raison qu'on ne peut seuelement obtenir une copie du tapis: pour
	 * permettre un access et une modification du tapis de jeu actuel sans le risque
	 * de perturber le jeu actuel.
	 * 
	 * @return La copie {@link Joueur} actuel.
	 */
	public Joueur getJoueurActuel() {
		return joueurActuel.getClone();
	}

	public ArrayList<Joueur> getJoueurs() {
		ArrayList<Joueur> j = new ArrayList<>();

		for (Joueur joueur : joueurs) {
			j.add(joueur.getClone());
		}

		return j;
	}

	/**
	 * Faire piocher une carte au joueur actuel.
	 * 
	 * @return Si le joueur actuel a le droit de piocher une carte, {@code true},
	 *         sinon {@code false}.
	 */
	public boolean joueurActuelPiocheCarte() {
		if (tapis.estRempli())
			return false;

		if (joueurActuelAPiocheCarteCeTour)
			return false;

		if (regles == Regles.Advanced)
			// En advanced, le joueur doit poser une carte avant de pouvoir en piocher une.
			if (!joueurActuelAPoseCarteCeTour)
				return false;

		if (!ilResteDesCartes())
			return false;

		Carte c = popCarteAleatoire();

		String carteString = c.getStringCarte();

		System.out.println();
		System.out.println(String.format("%s a pioché un %s |%s|", joueurActuel, c.toString(), carteString));

		joueurActuelAPiocheCarteCeTour = true;

		switch (regles) {
			case Autre:
				break;
			case Advanced:
				joueurActuel.ajouterCarteDansMain(c);
				afficherMainJoueurActuel();
				return true;
			case Standard:
				joueurActuel.setCartePiochee(c);
				return true;
			default:
				break;
		}

		throw new UnsupportedOperationException("Regles non implémentéés");
	}

	/**
	 * Le joueur actuel tente de poser une carte dans sa main. Si la carte peut etre
	 * posé a l'emplacement indiqué, la carte sera enlevée de la main du joueur, et
	 * posée sur le tapis.
	 * 
	 * @param carte La {@link Carte} du joueur a poser.
	 * @param x     Abscisse de l'emplacement voulu.
	 * @param y     Ordonnée de l'emplacement voulu.
	 * @return {@code true} si la carte a pu etre posée, {@code false} sinon.
	 */
	public boolean joueurActuelPoseCarteDansMain(Carte carte, int x, int y) {

		if (joueurActuel.getNombreCartesDansMain() == 1)
			return false;

		if (!joueurActuel.hasCarte(carte))
			return false;

		if (!joueurActuelPoseCarte(carte, x, y))
			return false;

		joueurActuel.retirerCarteDansMain(carte);
		return true;
	}

	/**
	 * Fait poser la carte piochee du joueur actuel (en verifiant si il a deja
	 * pioché une carte ce tour).
	 * 
	 * @param x Abscisse de la postion de la carte.
	 * @param y Ordonné de la postion de la carte.
	 * @return {@code true} si la carte a pu etre posée, {@code false} sinon.
	 */
	public boolean joueurActuelPoseCartePiochee(int x, int y) {
		if (!joueurActuelPoseCarte(joueurActuel.getCartePiochee(), x, y))
			return false;

		joueurActuel.setCartePiochee(null);
		return true;

	}

	/*
	 * Poser une carte donnée. Cette méthode est privée car elle est plus generale.
	 * Les autres methodes comme joueurActuelPoseCartePiochee ou
	 * joueurActuelPoseCarteDansMain appelerons cette methode.
	 */
	private boolean joueurActuelPoseCarte(Carte carte, int x, int y) {
		if (joueurActuelAPoseCarteCeTour)
			return false;

		if (carte == null)
			return false;

		if (!tapis.poserCarte(carte, x, y))
			return false;

		System.out.println();
		System.out.println(String.format("%s a posé une carte a (%d, %d)", joueurActuel, x, y));

		if (regles == Regles.Standard)
			afficherPointsJoueurActuel();

		afficherTapis();

		joueurActuelAPoseCarteCeTour = true;
		return true;
	}

	// Choisi et enleve une carte aleatoire de la liste des cartes restantes.
	private Carte popCarteAleatoire() {
		if (!ilResteDesCartes())
			return null;
		int   i = new Random().nextInt(cartesRestantes.size());
		Carte c = cartesRestantes.get(i);
		cartesRestantes.remove(i);
		return c;
	}

	// Donner une carte victoire a chaque joueur. Seulement appelé en debut de
	// partie, et pour les regles standard.
	private boolean distribuerCartesVictoires() {
		if (cartesVictoiresDistribues)
			return false;

		for (Joueur joueur : joueurs) {
			joueur.setCarteVictoire(popCarteAleatoire());
		}
		cartesVictoiresDistribues = true;
		return true;
	}

	private void distribuerCartesDansMain() {
		for (Joueur joueur : joueurs) {
			for (int i = 0; i < 3; i++) {
				joueur.ajouterCarteDansMain(popCarteAleatoire());
			}
		}
	}

	/**
	 * Deplacer une carte sur le tapis, prennant en compte si le joueur actuel a
	 * deja deplacé une carte, si la nouvelle position a des voisins...
	 * 
	 * @param x1 Abscisse de depart de la carte.
	 * @param y1 Ordonnée de depart de la carte.
	 * @param x2 Abscisse d'arrivée de la carte.
	 * @param y2 Ordonnée d'arrivée de la carte.
	 * @return {@code true} si le deplacement a pu etre effectué, {@code false}
	 *         sinon.
	 */
	public boolean joueurActuelDeplaceCarte(int x1, int y1, int x2, int y2) {
		if (joueurActuelADeplaceCarteCeTour)
			return false;

		if (!tapis.deplacerCarte(x1, y1, x2, y2))
			return false;

		System.out.println();
		System.out
				.println(String.format("%s a deplacé une carte de (%d, %d) a (%d, %d)", joueurActuel, x1, y1, x2, y2));

		afficherTapis();

		joueurActuelADeplaceCarteCeTour = true;
		return true;

	}

	/**
	 * Est-ce-que le tapis du jeu actuel est rempli ?
	 * 
	 * @return {@code true} si le terrain ne peut plus accepter de cartes,
	 *         {@code false} sinon
	 */
	public boolean tapisEstRempli() {
		return tapis.estRempli();
	}

	public boolean tapisEstVide() {
		return tapis.estVide();
	}

	/**
	 * Le joueur actuel a-t-il le droit d'arreter son tour.
	 * 
	 * @return {@code true} si le joueur actuel peut arreter son tour, {@code false}
	 *         sinon.
	 */
	public boolean joueurActuelPeutFinir() {
		return (joueurActuelAPiocheCarteCeTour && joueurActuelAPoseCarteCeTour);
	}

	/**
	 * Faire jouer le joueur actuel. Si le joueur a fini son tour, on passe au
	 * joueur suivant.
	 * 
	 * @return {@code true} si le joueur actuel a fini son tour, {@code false}
	 *         sinon.
	 */
	public boolean jouer() {
		if (joueurActuel.jouer()) {
			if (jeuTermine()) {
				calculerScoresDesJoueurs();
				return false;
			}

			passerAuJoueurSuivant();
		}

		return true;
	}

	/**
	 * Obtenir une copie profonde du tapis, c'est-a-dire une copie de la liste des
	 * cartes stockés par les tapis, et non seuelement une copie de la reference a
	 * la liste. <br>
	 * Cela permet de modifier le tapis, sans influencer le jeu actuel.
	 * 
	 * @return Le {@link Tapis} clone du jeu actuel.
	 */
	public Tapis getTapis() {
		return tapis.getClone();
	}

	private void afficherTapis() {
		System.out.print(VisitorAffichageString.getRepresentationStringStatic(tapis));
	}

	private void afficherPointsJoueurActuel() {

		if (regles == Regles.Standard) {
			String stringCarteVictoire = joueurActuel.getCarteVictoire().getStringCarte();
			System.out.println(String.format("Score pour %s avec |%s|: %d", joueurActuel, stringCarteVictoire,
					getScorePourCarte(joueurActuel.getCarteVictoire())));
		}

	}

	private void afficherMainJoueurActuel() {
		System.out.println("Main:");
		System.out.println(joueurActuel.getStringCartesDansMain());
		System.out.println();
	}

	public void afficherScoresDesJoueurs() {
		joueurs.forEach(j -> {
			System.out.println(String.format("Score de %s: %d", j, j.getScore()));
		});
	}

	/**
	 * Obtenir le score d'une carte pour l'etat actuel du tapis.
	 * 
	 * @param carte La {@link Carte} pour laquelle on veut calculer le score.
	 * @return Le score pour la carte.
	 */
	public int getScorePourCarte(Carte carte) {
		VisitorComptageScore v = new VisitorComptageScoreStandard(carte);
		tapis.accept(v);

		return v.getPoints();
	}

	private void calculerScoresDesJoueurs() {
		for (Joueur joueur : joueurs) {
			Carte carteVictoire = null;

			switch (regles) {
				case Standard:
					carteVictoire = joueur.getCarteVictoire();
					break;
				case Advanced:
					carteVictoire = joueur.getCarteDansMain(0);
					break;
				case Autre:
					break;
				default:
					break;
			}

			int score = getScorePourCarte(carteVictoire);
			joueur.setScore(score);
		}
	}

	public boolean joueurActuelAPoseCarteCeTour() {
		return joueurActuelAPoseCarteCeTour;
	}

	public boolean joueurActuelAPiocheCarteCeTour() {
		return joueurActuelAPiocheCarteCeTour;
	}

	public boolean joueurActuelADeplaceCarteCeTour() {
		return joueurActuelADeplaceCarteCeTour;
	}

	public boolean ilResteDesCartes() {
		return cartesRestantes.size() != 0;
	}

	public boolean jeuTermine() {
		if (tapisEstRempli())
			return true;

		switch (regles) {
			case Standard:
				if (ilResteDesCartes())
					return false;

				if (!joueurActuelAPoseCarteCeTour)
					return false;

				return true;
			case Advanced:
				if (ilResteDesCartes())
					return false;

				for (Joueur joueur : joueurs) {
					if (joueur.getNombreCartesDansMain() > 1)
						return false;
				}

				return true;
			case Autre:
				break;
			default:
				break;
		}
		return false;

	}

	public Regles getRegles() {
		return regles;
	}

}
