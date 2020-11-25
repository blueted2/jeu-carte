package fr.utt.sh.core;

import java.util.ArrayList;

/**
 * 
 * 
 * @author grego
 *
 */
public class Main {

	public static void main(String[] args) {
		ControlleurJeu c = ControlleurJeu.getInstance();
		c.commencerNouvellePartie(0, 3, Regles.Standard);

		while (!c.jeuTermine()) {
			c.jouer();
		}

		ArrayList<Joueur> joueurs = c.getJoueurs();

		joueurs.forEach(j -> {
			System.out.println(j.getScore());
		});

	}
}
