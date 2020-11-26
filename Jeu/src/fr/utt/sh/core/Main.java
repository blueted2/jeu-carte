package fr.utt.sh.core;

import java.util.ArrayList;

import fr.utt.sh.console_ui.Utils;

/**
 * 
 * 
 * @author grego
 *
 */
public class Main {

	public static void main(String[] args) {
		ControlleurJeu c = ControlleurJeu.getInstance();

		// Sélection du nombre de joueurs (humains et bots)

		System.out.println("Combien de joueurs humains et de bots ? (total entre 2 et 3)");
		String[] input = Utils.getLigneSeparee();

		int nbHumains = Integer.parseInt(input[0]);
		int nbBots    = Integer.parseInt(input[1]);

		while (!ControlleurJeu.nombreDeJoueursValide(nbHumains, nbBots)) {
			System.out.println(
					"Arrête de raconter des conneries, utilisateur. Combien de joueurs humains et de bots ? (min 0, max 3)");
			input = Utils.getLigneSeparee();

			nbHumains = Integer.parseInt(input[0]);
			nbBots    = Integer.parseInt(input[1]);
		}

		c.commencerNouvellePartie(nbHumains, nbBots, Regles.Standard);

		while (!c.jeuTermine()) {
			c.jouer();
		}

		ArrayList<Joueur> joueurs = c.getJoueurs();

		joueurs.forEach(j -> {
			System.out.println(j.getScore());
		});

	}
}
