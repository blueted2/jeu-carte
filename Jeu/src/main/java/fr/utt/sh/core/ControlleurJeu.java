/**
 * 
 */
package fr.utt.sh.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;

import fr.utt.sh.console_ui.VueConsole;
import fr.utt.sh.core.actions.DeplacerCarte;
import fr.utt.sh.core.actions.FinJeu;
import fr.utt.sh.core.actions.FinPartie;
import fr.utt.sh.core.actions.NouveauJoueur;
import fr.utt.sh.core.actions.PiocherCarte;
import fr.utt.sh.core.actions.PoserCarte;
import fr.utt.sh.core.score.VisitorComptageScore;
import fr.utt.sh.core.score.VisitorComptageScoreStandard;
import fr.utt.sh.core.strategy.StrategyJoueurConsole;
import fr.utt.sh.core.strategy.StrategyBot;
import fr.utt.sh.gui.InterfaceJeu;
import fr.utt.sh.core.tapis.TapisTri;
import fr.utt.sh.core.tapis.TypeTapis;
import fr.utt.sh.core.tapis.decalable.TapisRectTrouee_6x3;
import fr.utt.sh.core.tapis.decalable.Tapis_5x3;
import fr.utt.sh.core.tapis.Tapis;

/**
 * Cette classe singleton se charge de controller le flux général du jeu, ainsi
 * que la logique des règles.
 * 
 * @author grego
 *
 */
public class ControlleurJeu extends Observable {

	private static ControlleurJeu instance;

	private ArrayList<Carte>  toutesCartes;
	private ArrayList<Carte>  cartesRestantes;
	private ArrayList<Joueur> joueurs;
	private Iterator<Joueur>  iteratorJoueurs;

	private Tapis tapis;

	private Joueur joueurActuel;

	private Regles regles;

	private boolean debutPartie                     = false;
	private boolean debutJeu                        = true;
	private boolean joueurActuelAPoseCarteCeTour    = false;
	private boolean joueurActuelAPiocheCarteCeTour  = false;
	private boolean joueurActuelADeplaceCarteCeTour = false;

	private boolean _jeuTermine = false;

	private int nombreTotalDeParties;
	private int nombreDePartiesJoues = 0;

	private Thread threadStrategyJoueurActuel;

	private ControlleurJeu() {
		cartesRestantes = new ArrayList<Carte>();
		joueurs         = new ArrayList<Joueur>();
	}

	/**
	 * Une méthode utilitaire statique permettant de déterminer si une certaine
	 * configuration du nombre de joueurs humains et ordinateur est valide.
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
				joueurs.add(new Joueur("Bot_" + nombreBotsAjoutes, new StrategyBot(), false));
				nombreBotsAjoutes++;
			}
		}

		return;
	}

	/**
	 * 
	 * Commencer un nouveau jeu, et supprime celui déjà en cours. Le nombre total de
	 * joueurs doit être soit 2 ou 3, donc les combinaisons possibles de joueurs
	 * humains et bots sont :
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
	 * @param nbHumains       Le nombre de joueurs humains.
	 * @param nbBots          Le nombre de joueurs bots.
	 * @param regles          Les règles de jeu à utiliser.
	 * @param typeTapis       Le type de tapis à utiliser.
	 * @param nombreDeParties Le nombre de parties dans un jeu.
	 * @param interfaceJeu    Si l'interface graphique doit être activée.
	 */
	public void commencerNouveauJeu(int nbHumains, int nbBots, Regles regles, TypeTapis typeTapis, int nombreDeParties,
			boolean interfaceJeu) {

		// Assurer que le nombre de joueurs soit correct.
		nbHumains = Math.max(0, nbHumains);
		nbHumains = Math.min(3, nbHumains);

		nbBots = Math.max(0, nbBots);
		nbBots = Math.min(3, nbBots);

		nombreTotalDeParties = Math.max(1, nombreDeParties);

		if (nbBots + nbHumains > 3)
			nbBots = 0;

		if (nbBots + nbHumains < 2)
			nbBots = 2 - (nbBots + nbHumains);

		this.regles = regles;

		switch (typeTapis) {
			case Rectangulaire_5x3:
				tapis = new Tapis_5x3();
				break;
			case Triangulaire_5:
				tapis = new TapisTri(5);
				break;
			case RectangulaireTrouee_6x3:
				tapis = new TapisRectTrouee_6x3();
				break;
			default:
				tapis = new Tapis_5x3();
				break;
		}
		genererJoueurs(nbHumains, nbBots);
		genererToutesCartes();

		// Commencer l'interface graphique du jeu.
		if (interfaceJeu) {
			InterfaceJeu.begin();
		}

		VueConsole.begin();

		commencerNouvellePartie();
	}

	private void genererToutesCartes() {
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

	/**
	 * Essayer de commencer une nouvelle partie, tout en verifiant si la partie
	 * actuelle peut se terminer.
	 * 
	 * @return {@code true} si une nouvelle partie a pu être commencée,
	 *         {@code false} sinon.
	 */
	public boolean commencerNouvellePartie() {

		if (!debutJeu) {
			if (!partiePeutTerminer()) {
				return false;
			}
		} else {
			debutJeu = false;
		}

		for (Joueur joueur : joueurs) {
			joueur.resetCartes();
		}

		iteratorJoueurs = joueurs.iterator();
		tapis.clear();

		debutPartie = true;

		// Au lieu d'aller chercher toutes les cartes chez les joueurs, simplement les
		// recréer.
		initialiserCartes();

		// Au debut de la partie, il faut jeter une carte.
		popCarteAleatoire();

		switch (regles) {
			case Standard:
				distribuerCartesVictoires();
				break;
			case Advanced:
				distribuerCartesDansMain();
				break;
			case Variante:
				distribuerToutesCartesDansMain();
				break;
			default:
				throw new UnsupportedOperationException("regles pas implémenté");

		}

		passerAuJoueurSuivant();
		return true;
	}

	/**
	 * Vérifie si le joueur actuel a fini son tour, puis passe au joueur suivant.
	 * 
	 * @return {@code true} si on a pu passer au joueur suivant, {@code false}
	 *         sinon.
	 */
	public boolean passerAuJoueurSuivant() {

		// Si c'est le debut de la partie, on n'a pas besoin de verifier si le joueur
		// actuel a deja pioché/posé une carte...
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

		setChanged();
		notifyObservers(new NouveauJoueur(joueurActuel));

		joueurActuel.arreterStrategy();

		threadStrategyJoueurActuel = joueurActuel.beginStrategyThread();

		return true;
	}

	/**
	 * Obtenir le {@link Joueur} actuel, c'est a dire le joueur en train de jouer.
	 * 
	 * @return {@link Joueur}.
	 */
	public Joueur getJoueurActuel() {
		return joueurActuel;
	}

	/**
	 * Faire piocher une carte au joueur actuel.
	 * 
	 * @return {@code true} si le joueur actuel a le droit de piocher une carte, 
	 *         {@code false} sinon.
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

		joueurActuelAPiocheCarteCeTour = true;

		Carte nouvelleCarte = popCarteAleatoire();

		switch (regles) {
			case Advanced:
				joueurActuel.ajouterCarteDansMain(nouvelleCarte);
				break;
			case Standard:
				joueurActuel.setCartePiochee(nouvelleCarte);
				break;
			case Variante:
				return false;
			// Le joueur ne peut pas piocher de cartes, tout a ete distribué
			default:
				throw new IllegalArgumentException("Unexpected value: " + getRegles());
		}

		setChanged();
		notifyObservers(new PiocherCarte(joueurActuel, nouvelleCarte));
		return true;

	}

	/**
	 * Le joueur actuel tente de poser une carte dans sa main. Si la carte peut être
	 * posée à l'emplacement indiqué, la carte sera enlevée de la main du joueur, et
	 * posée sur le tapis.
	 * 
	 * @param carte La {@link Carte} du joueur a poser.
	 * @param x     Abscisse de l'emplacement voulu.
	 * @param y     Ordonnée de l'emplacement voulu.
	 * @return {@code true} si la carte a pu être posée, {@code false} sinon.
	 */
	public boolean joueurActuelPoseCarteDansMain(Carte carte, int x, int y) {

		if (joueurActuel.getNombreCartesDansMain() == 1)
			return false;

		if (!joueurActuel.hasCarte(carte))
			return false;

		if (!joueurActuelPoseCarte(carte, x, y))
			return false;

		joueurActuel.retirerCarteDansMain(carte);

		setChanged();
		notifyObservers(new PoserCarte(carte, new Position(x, y)));

		return true;
	}

	/**
	 * Fait poser la carte piochée du joueur actuel (en verifiant si il a déjà
	 * pioché une carte ce tour).
	 * 
	 * @param x Abscisse de la position de la carte.
	 * @param y Ordonnée de la position de la carte.
	 * @return {@code true} si la carte a pu être posée, {@code false} sinon.
	 */
	public boolean joueurActuelPoseCartePiochee(int x, int y) {
		Carte cartePiochee = joueurActuel.getCartePiochee();

		if (!joueurActuelPoseCarte(cartePiochee, x, y))
			return false;

		joueurActuel.setCartePiochee(null);

		setChanged();
		notifyObservers(new PoserCarte(cartePiochee, new Position(x, y)));
		return true;
	}

	/*
	 * Poser une carte donnée. Cette méthode est privée car elle est plus générale.
	 * Les autres méthodes comme joueurActuelPoseCartePiochee ou
	 * joueurActuelPoseCarteDansMain appelleront cette méthode.
	 */
	private boolean joueurActuelPoseCarte(Carte carte, int x, int y) {
		if (joueurActuelAPoseCarteCeTour)
			return false;

		if (carte == null)
			return false;

		if (!tapis.poserCarte(carte, x, y))
			return false;

		joueurActuelAPoseCarteCeTour = true;
		return true;
	}

	// Choisit et enlève une carte aléatoire de la liste des cartes restantes.
	private Carte popCarteAleatoire() {
		if (!ilResteDesCartes())
			return null;
		int   i = new Random().nextInt(cartesRestantes.size());
		Carte c = cartesRestantes.get(i);
		cartesRestantes.remove(i);
		return c;
	}

	// Donner une carte victoire à chaque joueur. Seulement appelé en debut de
	// partie, et pour les règles standard.
	private boolean distribuerCartesVictoires() {
		for (Joueur joueur : joueurs) {
			joueur.setCarteVictoire(popCarteAleatoire());
		}

		return true;
	}

	private void distribuerCartesDansMain() {
		for (Joueur joueur : joueurs) {
			for (int i = 0; i < 3; i++) {
				joueur.ajouterCarteDansMain(popCarteAleatoire());
			}
		}
	}

	private void distribuerToutesCartesDansMain() {
		int i = 0;
		while (ilResteDesCartes()) {
			if (i >= joueurs.size())
				i = 0;

			joueurs.get(i).ajouterCarteDansMain(popCarteAleatoire());
			i++;
		}
	}

	/**
	 * Déplacer une carte sur le tapis, prennant en compte si le joueur actuel a
	 * déjà deplacé une carte, si la nouvelle position a des voisins...
	 * 
	 * @param x1 Abscisse de depart de la carte.
	 * @param y1 Ordonnée de depart de la carte.
	 * @param x2 Abscisse d'arrivée de la carte.
	 * @param y2 Ordonnée d'arrivée de la carte.
	 * @return {@code true} si le déplacement a pu être effectué, {@code false}
	 *         sinon.
	 */
	public boolean joueurActuelDeplaceCarte(int x1, int y1, int x2, int y2) {
		if (joueurActuelADeplaceCarteCeTour)
			return false;

		if (!tapis.deplacerCarte(x1, y1, x2, y2))
			return false;

		joueurActuelADeplaceCarteCeTour = true;

		setChanged();
		notifyObservers(new DeplacerCarte(new Position(x1, y1), new Position(x2, y2)));
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
	 * Le joueur actuel a-t-il le droit d'arrêter son tour ?
	 * 
	 * @return {@code true} si le joueur actuel peut arrêter son tour, {@code false}
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
	 * cartes stockées par les tapis, et non seuelement une copie de la reference à
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
				case Variante:
					carteVictoire = joueur.getCarteDansMain(0);
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + getRegles());
			}

			int score = getScorePourCarte(carteVictoire);
			joueur.setScore(score);
		}
	}

	/**
	 * @return {@code true} si le joueur actuel a déjà posé une carte ce tour,
	 *         {@code false} sinon.
	 */
	public boolean hasJoueurActuelPoseCarteCeTour() {
		return joueurActuelAPoseCarteCeTour;
	}

	/**
	 * @return {@code true} si le joueur actuel a déjà pioché une carte ce tour,
	 *         {@code false} sinon.
	 */
	public boolean hasJoueurActuelPiocheCarteCeTour() {
		return joueurActuelAPiocheCarteCeTour;
	}

	/**
	 * @return {@code true} si le joueur a déjà déplacé une carte ce tour,
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
	public boolean isJeuTermine() {
		return _jeuTermine && !threadStrategyJoueurActuel.isAlive(); // Attendre que le thread de la strategy ait
																		// terminé.
	}

	private boolean partiePeutTerminer() {
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
			case Variante:
				if (ilResteDesCartes())
					return false;

				for (Joueur joueur : joueurs) {
					if (joueur.getNombreCartesDansMain() > 1)
						return false;
				}

				return true;
			default:
				throw new IllegalArgumentException("Unexpected value: " + getRegles());
		}
	}

	/**
	 * Obtenir le type {@link Regles} du jeu en cours.
	 * 
	 * @return Les règles.
	 */
	public Regles getRegles() {
		return regles;
	}

	/**
	 * Faire terminer le tour du joueur actuel, tout en verifiant si ce joueur a le
	 * droit de terminer.
	 * 
	 * @return {@code true} si le tour du joueur a pu être terminé, {@code false}
	 *         sinon.
	 */
	public boolean terminerTourJoueurActuel() {
		if (!joueurActuelPeutFinir())
			return false;

		if (partiePeutTerminer()) {
			calculerScoresDesJoueurs();

			nombreDePartiesJoues++;
			if (nombreDePartiesJoues >= nombreTotalDeParties) {
				_jeuTermine = true;
				setChanged();
				notifyObservers(new FinJeu());
			} else {
				setChanged();
				notifyObservers(new FinPartie());
			}

			return true;
		}
		passerAuJoueurSuivant();
		return true;
	}

	/**
	 * @return Un {@link ArrayList} des joueurs du jeu.
	 */
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * @return Le {@link Joueur} avec le score le plus élevé.
	 */
	public Joueur getGagnant() {
		Joueur gagnant = joueurs.get(0);
		for (Joueur joueur : joueurs) {
			if (joueur.getScore() > gagnant.getScore())
				gagnant = joueur;
		}

		return gagnant;
	}
}
