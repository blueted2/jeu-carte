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
		Regles regles;

		while (!ControlleurJeu.nombreDeJoueursValide(nbHumains, nbBots)) {
			System.out.println(
					"Arrête de raconter des conneries, utilisateur. Combien de joueurs humains et de bots ? (min 0, max 3)");
			input = Utils.getLigneSeparee();

			nbHumains = Integer.parseInt(input[0]);
			nbBots    = Integer.parseInt(input[1]);
		}
		
		System.out.println("Quelles regles ? (s. Standard, a. Advanced)");
		input = Utils.getLigneSeparee();
		switch(input[0]) {
			case "s":
				regles = Regles.Standard;
				break;
			case "a":
				regles = Regles.Advanced;
				break;
			default:
				regles = Regles.Standard;
				break;
		}

		c.commencerNouvellePartie(nbHumains, nbBots, regles);

		while (!c.jeuTermine()) {
			c.jouer();
		}
		
		c.afficherScoresDesJoueurs();
	}
}
