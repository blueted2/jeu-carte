/**
 * 
 */
package fr.utt.sh.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import fr.utt.sh.console_ui.GenerateurString;
import fr.utt.sh.core.score.VisitorComptageScore;
import fr.utt.sh.core.score.VisitorComptageScoreStandard;
import fr.utt.sh.core.strategy.StrategyJoueurConsole;
import fr.utt.sh.core.strategy.StrategyTest;
import fr.utt.sh.core.tapis.Tapis;
import fr.utt.sh.core.tapis.Tapis.TypeTapis;
import fr.utt.sh.gui.InterfaceJeu;
import fr.utt.sh.core.tapis.Tapis_5x3;
import fr.utt.sh.core.tapis.Tapis_Triangulaire;
import fr.utt.sh.core.tapis.Tapis_Rectangulaire;

/**
 * Cette classe singleton se charge de controller le flux general du jeu, ainsi
 * de la logique des règles.
 * 
 * @author grego
 *
 */
public class ControlleurJeu extends Observable{

	private static ControlleurJeu instance;

	private ArrayList<Carte>  toutesCartes;
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

	private boolean _jeuTermine = false;

	private ControlleurJeu() {
		cartesRestantes = new ArrayList<Carte>();
		joueurs         = new ArrayList<Joueur>();
	}

	/**
	 * Une methodes utilitaire statique permettant de determiner si une certaine
	 * configuration du nombre de joueurs humains et oridnateur est valide.
	 * 
	 * @param joueursHumains Le nombre de joueurs humains dans la partie.
	 * @param joueursBots    Le nombre de joueurs bots dans la partie.
	 * @return {@code true} si la configuration est valide, {@code false} sinon.
	 */
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

	private void initialiserCartes() {
		if (toutesCartes == null) {

			toutesCartes = new ArrayList<Carte>();
			for (Carte.Remplissage remplissage : Carte.Remplissage.values()) {
				for (Carte.Couleur couleur : Carte.Couleur.values()) {
					for (Carte.Forme forme : Carte.Forme.values()) {
						Carte nouvelleCarte = new Carte(couleur, remplissage, forme);
						toutesCartes.add(nouvelleCarte);
					}
				}
			}
		}

		cartesRestantes = new ArrayList<Carte>();
		for (Carte carte : toutesCartes) {
			cartesRestantes.add(carte);
		}
	}

	private void genererJoueurs(int nombreDeJoueursHumains, int nombreDeJoueuersBots) {
		joueurs = new ArrayList<Joueur>();

		int nombreBotsAjoutes    = 0;
		int nombreHumainsAjoutes = 0;

		while (nombreBotsAjoutes + nombreHumainsAjoutes < nombreDeJoueuersBots + nombreDeJoueursHumains) {
			if (nombreHumainsAjoutes < nombreDeJoueursHumains) {
				joueurs.add(new Joueur("Humain_" + nombreHumainsAjoutes, new StrategyJoueurConsole(), true));
				nombreHumainsAjoutes++;
			}

			if (nombreBotsAjoutes < nombreDeJoueuersBots) {
				joueurs.add(new Joueur("Bot_" + nombreBotsAjoutes, new StrategyTest(), false));
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

	private Thread threadStrategyJoueurActuel;

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
	 * @param regles    Les regles de jeu a utiliser
	 * @param typeTapis Le type de tapis a utiliser
	 * @param largeur   TODO
	 * @param hauteur   TODO
	 */
	public void commencerNouvellePartie(int nbHumains, int nbBots, Regles regles, TypeTapis typeTapis, int largeur,
			int hauteur){

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
		initialiserCartes();

		// Commener l'interface graphique du jeu.
		InterfaceJeu.begin();

		// Au debut de la partie, il faut jeter une carte.
		popCarteAleatoire();

		switch (regles) {
			case Standard:
				distribuerCartesVictoires();
				break;
			case Advanced:
				distribuerCartesDansMain();
				break;
			default:
				throw new UnsupportedOperationException("regles pas implémenté");

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
		} else {
			debutPartie = false;
		}

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
				String stringCarte = GenerateurString.getStringCarte(carteVictoire);

				System.out.println(String.format("Carte victoire: |%s|", stringCarte));
				break;

			case Advanced:
				afficherMainJoueurActuel();
				break;

			default:
				break;
		}

//		if(threadStrategyJoueurActuel != null)
//			threadStrategyJoueurActuel.

		setChanged();
		notifyObservers();

		threadStrategyJoueurActuel = joueurActuel.beginStrategyThread();

		return true;
	}

	/**
	 * Obtenir le {@link Joueur} actuel, c'est a dire le joueur en train de joueur.
	 * 
	 * @return {@link Joueur}.
	 */
	public Joueur getJoueurActuel() {
		return joueurActuel;
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

		Carte nouvelleCarte = popCarteAleatoire();

		String carteString = GenerateurString.getStringCarte(nouvelleCarte);

		System.out.println();
		System.out
				.println(String.format("%s a pioché un %s |%s|", joueurActuel, nouvelleCarte.toString(), carteString));

		joueurActuelAPiocheCarteCeTour = true;

		switch (regles) {
			case Advanced:
				joueurActuel.ajouterCarteDansMain(nouvelleCarte);
				afficherMainJoueurActuel();
				return true;
			case Standard:
				joueurActuel.setCartePiochee(nouvelleCarte);
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

	/**
	 * Est-ce-que le tapis du jeu actuel est vide ?
	 * 
	 * @return {@code true} si le tapis ne contient pas de cartes, {@code false}
	 *         sinon.
	 */
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
		if (!joueurActuelAPoseCarteCeTour)
			return false;
		if (ilResteDesCartes() && !joueurActuelAPiocheCarteCeTour)
			return false;
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
	public Tapis getCloneTapis() {
		return tapis.getClone();
	}

	/**
	 * Obtenir le tapis du jeu en cours.
	 * 
	 * @return {@link Tapis}
	 */
	public Tapis getTapis() {
		return tapis;
	}

	/**
	 * Obtenir une liste de toutes les cartes possibles du jeu.
	 * 
	 * @return Un {@code ArrayList} de cartes.
	 */
	public ArrayList<Carte> getToutesCartes() {
		return toutesCartes;
	}

	private void afficherTapis() {
		System.out.print(GenerateurString.getStringTapis(tapis));
	}

	private void afficherPointsJoueurActuel() {

		if (regles == Regles.Standard) {
			String stringCarteVictoire = GenerateurString.getStringCarte(joueurActuel.getCarteVictoire());
			System.out.println(String.format("Score pour %s avec |%s|: %d", joueurActuel, stringCarteVictoire,
					getScorePourCarte(joueurActuel.getCarteVictoire())));
		}

	}

	private void afficherMainJoueurActuel() {
		System.out.println("Main:");
		System.out.println(GenerateurString.getStringCartesDansMainJoueur(joueurActuel));
		System.out.println();
	}

	/**
	 * Afficher le score de tout les jouers a la console.
	 */
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
				default:
					break;
			}

			int score = getScorePourCarte(carteVictoire);
			joueur.setScore(score);
		}
	}

	/**
	 * @return {@code true} si le joueur actuel a deja posé une carte ce tour,
	 *         {@code false} sinon.
	 */
	public boolean hasJoueurActuelPoseCarteCeTour() {
		return joueurActuelAPoseCarteCeTour;
	}

	/**
	 * @return {@code true} si le joueur actuel a deja pioché une carte ce tour,
	 *         {@code false} sinon.
	 */
	public boolean hasJoueurActuelPiocheCarteCeTour() {
		return joueurActuelAPiocheCarteCeTour;
	}

	/**
	 * @return {@code true} si le joueur a deja déplacé une carte ce tour,
	 *         {@code false} sinon.
	 */
	public boolean hasJoueurActuelDeplaceCarteCeTour() {
		return joueurActuelADeplaceCarteCeTour;
	}

	/**
	 * Determiner si il reste des cartes non-piochées / dans le tas.
	 * 
	 * @return {@code true} si il reste des cartes, {@code false} sinon.
	 */
	public boolean ilResteDesCartes() {
		return cartesRestantes.size() != 0;
	}

	/**
	 * Determiner si le jeu en cours est terminé, en regardant si le tapis est
	 * rempli, si il reste des cartes non piochés, si le joueur actuel a fini son
	 * tour ...
	 * 
	 * @return {@code true} si le jeu est terminé, {@code false} sinon.
	 */
	public boolean jeuTermine() {
		return _jeuTermine && !threadStrategyJoueurActuel.isAlive(); // Attendre que le thread de la strategy ait
																		// terminé.
	}

	private boolean jeuPeutTerminer() {
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
			default:
				break;
		}
		return false;
	}

	/**
	 * Obtenir le type {@link Regles} du jeu en cours.
	 * 
	 * @return Les regles.
	 */
	public Regles getRegles() {
		return regles;
	}

	/**
	 * Faire terminer le tour du joueur actuel, tout en verifiant si ce joueur a le
	 * droit de terminer.
	 * 
	 * @return {@code true} si le tour du joueur a pu etre terminé, {@code false}
	 *         sinon.
	 */
	public boolean terminerTourJoueurActuel() {
		if (!joueurActuelPeutFinir())
			return false;
		
		joueurActuel.arreterStrategy();
		

		if (jeuPeutTerminer()) {
			calculerScoresDesJoueurs();
			_jeuTermine = true;
			return true;
		}
		passerAuJoueurSuivant();
		return true;
	}

}
