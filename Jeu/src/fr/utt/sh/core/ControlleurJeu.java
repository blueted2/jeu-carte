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

	boolean cartesVictoiresDistribues = false;

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

		// Au lieur d'aller chercher toutes les cartes chez les joueurs, simplement les
		// recreers.
		genererCartes();
		genererJoueurs(nbHumains, nbBots);

		cartesVictoiresDistribues = false;

		iteratorJoueurs = joueurs.iterator();

//		tapis       = new Tapis_5x3();
		tapis       = new Tapis_Triangulaire(5);
		debutPartie = true;

		distribuerCartesVictoires();

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
		return joueurActuel.getClone();
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

		if (joueurAPiocheCarteCeTour)
			return false;
		if (!cartesVictoiresDistribues)
			return false;

		Carte c = popCarteAleatoire();

		String carteString = VisitorAffichageString.getRepresentationStringStatic(c);

		System.out.println();
		System.out.println(String.format("%s a pioché un %s |%s|", joueurActuel, c.toString(), carteString));

		joueurActuel.setCartePiochee(c);
		joueurAPiocheCarteCeTour = true;

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
		return joueurActuelPoseCarte(joueurActuel.getCartePiochee(), x, y);
	}

	boolean joueurActuelPoseCarte(Carte carte, int x, int y) {
		if (joueurAPoseCarteCeTour)
			return false;
		if (!joueurAPiocheCarteCeTour)
			return false;

		if (!tapis.poserCarte(carte, x, y))
			return false;

		System.out.println();
		System.out.println(String.format("%s a posé une carte a (%d, %d)", joueurActuel, x, y));
		afficherPointsJoueurActuel();
		afficherTapis();

		joueurAPoseCarteCeTour = true;
		return true;
	}

	// Choisi et enleve une carte aleatoire de la liste des cartes restantes.
	Carte popCarteAleatoire() {
		int   i = new Random().nextInt(cartesRestantes.size());
		Carte c = cartesRestantes.get(i);
		cartesRestantes.remove(i);
		return c;
	}

	boolean distribuerCartesVictoires() {
		if (cartesVictoiresDistribues)
			return false;

		for (Joueur joueur : joueurs) {
			joueur.setCarteVictoire(popCarteAleatoire());
		}
		cartesVictoiresDistribues = true;
		return true;
	}

	/**
	 * @param x1 Abscisse de depart de la carte.
	 * @param y1 Ordonnée de depart de la carte.
	 * @param x2 Abscisse d'arrivée de la carte.
	 * @param y2 Ordonnée d'arrivée de la carte.
	 * @return {@code true} si le deplacement a pu etre effectué, {@code false}
	 *         sinon.
	 */
	public boolean joueurActuelDeplaceCarte(int x1, int y1, int x2, int y2) {
		if (joueurADeplaceCarteCeTour)
			return false;

		if (!tapis.deplacerCarte(x1, y1, x2, y2))
			return false;

		System.out.println();
		System.out
				.println(String.format("%s a deplacé une carte de (%d, %d) a (%d, %d)", joueurActuel, x1, y1, x2, y2));
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
