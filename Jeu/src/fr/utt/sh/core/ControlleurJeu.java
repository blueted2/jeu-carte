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
import fr.utt.sh.core.tapis.Tapis_5x3;
import fr.utt.sh.core.tapis.Tapis_Triangulaire;

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

	ArrayList<Carte>  cartesRestantes;
	ArrayList<Joueur> joueurs;
	Iterator<Joueur>  iteratorJoueurs;

	Tapis tapis;

	Joueur joueurActuel;

	boolean debutPartie;

	boolean joueurAPoseCarteCeTour    = false;
	boolean joueurAPiocheCarteCeTour  = false;
	boolean joueurADeplaceCarteCeTour = false;

	HashMap<Joueur, Boolean> joueursAyantPiocheeCarteVictoire;

	ControlleurJeu() {
		cartesRestantes = new ArrayList<Carte>();
		joueurs         = new ArrayList<Joueur>();
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

	void genererCartes() {
		cartesRestantes = new ArrayList<Carte>();
		for (Carte.Remplissage remplissage : Carte.Remplissage.values()) {
			for (Carte.Couleur couleur : Carte.Couleur.values()) {
				for (Carte.Forme forme : Carte.Forme.values()) {
					cartesRestantes.add(new Carte(couleur, remplissage, forme));
				}
			}
		}
	}

	void genererJoueurs(int nombreDeJoueursHumains, int nombreDeJoueuersBots) {
		joueurs = new ArrayList<Joueur>();

		int nombreBotsAjoutes    = 0;
		int nombreHumainsAjoutes = 0;

		while (nombreBotsAjoutes + nombreHumainsAjoutes < nombreDeJoueuersBots + nombreDeJoueursHumains) {
			if (nombreBotsAjoutes < nombreDeJoueuersBots) {
				joueurs.add(new Joueur("Bot", new StrategyTest()));
				nombreBotsAjoutes++;
			}

			if (nombreHumainsAjoutes < nombreDeJoueursHumains) {
				joueurs.add(new Joueur("Humain", new StrategyJoueurConsole()));
				nombreHumainsAjoutes++;
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
	 * Permet de commencer une nouvelle partie, et supprime celle deja en cours.
	 * 
	 * @param nombreDeJoueurs le nombre de joueurs dans la partie
	 */
	public void commencerNouvellePartie(int nbHumains, int nbBots) {
		genererCartes();
		genererJoueurs(nbHumains, nbBots);

		joueursAyantPiocheeCarteVictoire = new HashMap<Joueur, Boolean>();
		for (Joueur joueur : joueurs)
			joueursAyantPiocheeCarteVictoire.put(joueur, false);

		iteratorJoueurs = joueurs.iterator();

//		tapis       = new Tapis_5x3();
		tapis       = new Tapis_Triangulaire(5);
		debutPartie = true;

		for (Joueur joueur : joueurs)
			joueur.piocherCarteVicoire();
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
		if (!debutPartie) {
			if (!joueurAPiocheCarteCeTour)
				return false;

			if (!joueurAPoseCarteCeTour)
				return false;
		} else
			debutPartie = false;

		// Si on est a la fin de l'iterator, en créer un nouveau.
		if (!iteratorJoueurs.hasNext())
			iteratorJoueurs = joueurs.iterator();

		joueurActuel = iteratorJoueurs.next();

		joueurAPiocheCarteCeTour  = false;
		joueurAPoseCarteCeTour    = false;
		joueurADeplaceCarteCeTour = false;

		String stringCarte = VisitorAffichageString.getRepresentationStringStatic((joueurActuel.getCarteVictoire()));

		System.out.println("-------------------------------------");
		System.out.println(String.format("A %s de jouer", joueurActuel));
		System.out.println(String.format("Carte victoire: |%s|", stringCarte));

		afficherTapis();

		return true;
	}

	/**
	 * Obtenir le {@link Joueur} actuel.
	 * 
	 * @return Le {@link Joueur} actuel.
	 */
	public Joueur getJoueurActuel() {
		return joueurActuel;
	}

	/**
	 * Permet a un joueur de piocher une carte. Donne une nouvelle, en l'elevant le
	 * la list des cartes restantes/non piochées.
	 * 
	 * @param joueur Le joueur voulant piocher une carte.
	 * @return Si le joueur a le droit de piocher une carte, une {@link Carte},
	 *         sinon {@code null}.
	 */
	public Carte piocherCarte(Joueur joueur) {
		if (tapis.estRempli())
			return null;
		if (joueurActuel != joueur)
			return null;
		if (joueurAPiocheCarteCeTour)
			return null;
		if (!toutJoueurAPiocheCarteVictoire())
			return null;

		Carte c = popCarteAleatoire();

		String carte       = VisitorAffichageString.getRepresentationStringStatic(c);
		String forme       = c.getForme().name();
		String couleur     = c.getCouleur().name();
		String remplissage = c.getRemplissage().name();

		System.out.println();
		System.out.println(String.format("%s a pioché un %s %s %s |%s|", joueur, forme, couleur, remplissage, carte));

		afficherTapis();

		joueurAPiocheCarteCeTour = true;
		return c;
	}

	// Choisi et enleve une carte aleatoire de la liste des cartes restantes.
	Carte popCarteAleatoire() {
		int   i = new Random().nextInt(cartesRestantes.size());
		Carte c = cartesRestantes.get(i);
		cartesRestantes.remove(i);
		return c;
	}

	boolean toutJoueurAPiocheCarteVictoire() {
		for (Joueur joueur : joueurs)
			if (!joueursAyantPiocheeCarteVictoire.get(joueur))
				return false;

		return true;
	}

	/**
	 * Essayer de piocher une carte victoire. Donnera null si le joueur a deja
	 * pioché une carte victoire.
	 * 
	 * @param joueur
	 * @return Une carte.
	 */
	public Carte piocherCarteVictoire(Joueur joueur) {
		if (joueursAyantPiocheeCarteVictoire.get(joueur))
			return null;

		joueursAyantPiocheeCarteVictoire.put(joueur, true);
		return popCarteAleatoire();
	}

	/**
	 * Appelle a son tour {@link Tapis#poserCarte} dans le tapis du jeu, mais prend
	 * comme paramètre supplémentaire le joueur qui veux poser une carte, afin de
	 * verifier si le joueur a le droit de poser une carte.
	 * 
	 * @see Tapis#poserCarte
	 * @param joueur Le {@link Joueur} qui veux poser une carte.
	 * @param carte  La carte a poser.
	 * @param x      Abscisse de la carte.
	 * @param y      Ordonnée de la carte.
	 * @return {@code true} si la carte a pu être posée, {@code false} sinon
	 */
	public boolean poserCarte(Joueur joueur, Carte carte, int x, int y) {
		if (joueur != joueurActuel)
			return false;
		if (joueurAPoseCarteCeTour)
			return false;
		if (!joueurAPiocheCarteCeTour)
			return false;

		if (!tapis.poserCarte(carte, x, y))
			return false;

		System.out.println();
		System.out.println(String.format("%s a posé une carte a (%d, %d)", joueur, x, y));
		afficherPointsJoueurActuel();
		afficherTapis();

		joueurAPoseCarteCeTour = true;
		return true;
	}

	/**
	 * @param joueur Le joueur essayant de deplacer une carte.
	 * @param x1     Abscisse de depart de la carte.
	 * @param y1     Ordonnée de depart de la carte.
	 * @param x2     Abscisse d'arrivée de la carte.
	 * @param y2     Ordonnée d'arrivée de la carte.
	 * @return {@code true} si le deplacement a pu etre effectué, {@code false}
	 *         sinon.
	 */
	public boolean deplacerCarte(Joueur joueur, int x1, int y1, int x2, int y2) {
		if (joueurADeplaceCarteCeTour)
			return false;

		if (!tapis.deplacerCarte(x1, y1, x2, y2))
			return false;

		System.out.println();
		System.out.println(String.format("%s a deplacé une carte de (%d, %d) a (%d, %d)", joueur, x1, y1, x2, y2));
		afficherPointsJoueurActuel();
		afficherTapis();

		joueurADeplaceCarteCeTour = true;
		return true;

	}

	/**
	 * @return {@code true} si le terrain ne peut plus accepter de cartes,
	 *         {@code false} sinon
	 */
	public boolean tapisEstRempli() {
		return tapis.estRempli();
	}

	/**
	 * Le joueur actuel a-t-il le droit d'arreter son tour.
	 * 
	 * @return {@code true} si le joueur actuel peut arreter son tour, {@code false}
	 *         sinon.
	 */
	public boolean joueurActuelPeutFinir() {
		return (joueurAPiocheCarteCeTour && joueurAPoseCarteCeTour);
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
			passerAuJoueurSuivant();
			return true;
		}
		return false;
	}

	/**
	 * @return Le {@link Tapis} du jeu actuel.
	 */
	public Tapis getTapis() {
		return tapis.getClone();
	}

	void afficherTapis() {
		System.out.print(VisitorAffichageString.getRepresentationStringStatic(tapis));
	}

	void afficherPointsJoueurActuel() {
		String stringCarteVictoire = VisitorAffichageString
				.getRepresentationStringStatic(joueurActuel.getCarteVictoire());
		System.out.println(String.format("Score pour %s avec |%s|: %d", joueurActuel, stringCarteVictoire,
				getScorePourCarte(joueurActuel.getCarteVictoire())));
	}

	/**
	 * Permet d'obtenir le score d'une carte pour l'etat actuel du tapis.
	 * 
	 * @param carte La {@link Carte} pour laquelle on veut calculer le score.
	 * @return Le score pour la carte.
	 */
	public int getScorePourCarte(Carte carte) {
		VisitorComptageScore v = new VisitorComptageScoreStandard(carte);
		tapis.accept(v);

		return v.getPoints();
	}

}
