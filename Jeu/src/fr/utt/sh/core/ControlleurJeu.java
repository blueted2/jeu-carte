/**
 * 
 */
package fr.utt.sh.core;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author grego
 *
 */
public class ControlleurJeu {
	
	// Test permissions
	// Test merge

	private static ControlleurJeu instance;
	ArrayList<Carte> cartesRestantes;
	ArrayList<Joueur> joueurs;

	Iterator<Joueur> iteratorJoueurs;

	Joueur joueurActuel;

	public ControlleurJeu() {
		cartesRestantes = new ArrayList<Carte>();
		joueurs = new ArrayList<Joueur>();
	}

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
				cartesRestantes.add(new Carte(couleur, remplissage));
			}
		}
	}

	private void genererJoueurs(int nombreDeJoueurs) {
		joueurs = new ArrayList<Joueur>();
		for (int i = 0; i < nombreDeJoueurs; i++) {
			joueurs.add(new Joueur(Integer.toString(i)));
		}
		iteratorJoueurs = joueurs.iterator();

	}

	public void commencerNouvellePartie(int nombreDeJoueurs) {
		genererCartes();
		genererJoueurs(nombreDeJoueurs);
	}

	public Joueur passerAuJoueurSuivant() {
		if (!iteratorJoueurs.hasNext()) {
			iteratorJoueurs = joueurs.iterator();
		}
		joueurActuel = iteratorJoueurs.next();
		return joueurActuel;
	}

	public Joueur getJoueurActuel() {
		return joueurActuel;
	}
}
