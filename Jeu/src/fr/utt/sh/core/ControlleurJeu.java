/**
 * 
 */
package fr.utt.sh.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Cette classe singleton se charge de controller le flux general du jeu, ainsi
 * de la logique des regles.
 * 
 * @author grego
 *
 */
public class ControlleurJeu {

	private static ControlleurJeu instance;
	ArrayList<Carte> cartesRestantes;

	ArrayList<Joueur> joueurs;
	Iterator<Joueur> iteratorJoueurs;

	Tapis tapis;

	Joueur joueurActuel;
	boolean joueurAPoseCarteCeTour = false;
	boolean joueurAPiocheCarteCeTour = false;

	ControlleurJeu() {
		cartesRestantes = new ArrayList<Carte>();
		joueurs = new ArrayList<Joueur>();
	}

	/**
	 * @return Donne l'instance de {@code ControlleurJeu}.
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
				for(Carte.Forme forme : Carte.Forme.values()) {
					cartesRestantes.add(new Carte(couleur, remplissage, forme));
				}
			}
		}
	}

	void genererJoueurs(int nombreDeJoueurs) {
		joueurs = new ArrayList<Joueur>();
		for (int i = 0; i < nombreDeJoueurs; i++) {
			joueurs.add(new Joueur(Integer.toString(i)));
		}

	}

	/**
	 * Permet de commencer une nouvelle partie, et supprime celle deja en cours.
	 * 
	 * @param nombreDeJoueurs le nombre de joueurs dans la partie
	 */
	public void commencerNouvellePartie(int nombreDeJoueurs) {
		genererCartes();
		genererJoueurs(nombreDeJoueurs);

		iteratorJoueurs = joueurs.iterator();
		joueurActuel = iteratorJoueurs.next();

		tapis = new Tapis_5x3();
	}

	/**
	 * Verifie si le joueur actuel a fini son tour, puis passe au joueur suivant.
	 * 
	 * @return {@code true} si on a pu passer au joueur suivant, {@code false}
	 *         sinon.
	 */
	public boolean passerAuJoueurSuivant() {
		if (!iteratorJoueurs.hasNext()) {
			iteratorJoueurs = joueurs.iterator();
		}
		joueurActuel = iteratorJoueurs.next();
		joueurAPiocheCarteCeTour = false;
		joueurAPoseCarteCeTour = false;
		return true;
	}

	/**
	 * @return le {@code Joueur} actuel.
	 */
	public Joueur getJoueurActuel() {
		return joueurActuel;
	}

	/**
	 * Appelle a son tour {@code poserCarte} dans son tapis, mais prend comme
	 * parametre supplementaire le joueur qui veux poser une carte, afin de verifier
	 * si le joueur a le droit de poser une carte.
	 * 
	 * @see Tapis#poserCarte
	 * @param joueur Le {@code Joueur} qui veux poser une carte.
	 * @param carte  La carte a poser.
	 * @param x      Abscisse de la carte.
	 * @param y      Ordonnee de la carte.
	 * @return {@code true} si la carte a pu etre pos�e, {@code false} sinon
	 */
	public boolean poserCarte(Joueur joueur, Carte carte, int x, int y) {
		if (joueur != joueurActuel)
			return false;
		if (joueurAPoseCarteCeTour)
			return false;

		if (!tapis.poserCarte(carte, x, y))
			return false;
		
//		joueurAPoseCarteCeTour = true;
		return true;
	}
	
	
	
	/**
	 * Permet a un joueur de piocher une carte. Donne une nouvelle, en l'elevant le la list des cartes restantes/non pioch�es. 
	 * @param joueur Le joueur voulant piocher une carte.
	 * @return {@code null} si le joueur n'a pas le droit de piocher une carte, une {@code Carte} sinon.
	 */
	public Carte piocherCarte(Joueur joueur) {
		if(tapis.estRempli()) return null;
		if(joueurActuel != joueur) return null;
		if(joueurAPiocheCarteCeTour) return null;
		
		
		int i = new Random().nextInt(cartesRestantes.size());
		Carte c = cartesRestantes.get(i);
		cartesRestantes.remove(i);
		
		joueurAPiocheCarteCeTour = true;
		return c;
	}
	
	/**
	 * @return {@code true} si le terrain ne peut plus accepter de cartes,
	 *         {@code false} sinon
	 */
	public boolean tapisEstRempli() {
		return tapis.estRempli();
	}

	public Tapis getTapis() {
		return tapis;
	}
}
