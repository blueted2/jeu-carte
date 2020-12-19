package fr.utt.sh.core;

import fr.utt.sh.console_ui.Utils;
import fr.utt.sh.core.tapis.Tapis.TypeTapis;
import fr.utt.sh.gui.InterfaceJeu;

/**
 * 
 * 
 * @author grego
 *
 */
public class Main {

	/**
	 * Methode main, executant le jeu.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		
		ControlleurJeu c = ControlleurJeu.getInstance();

		// Sélection du nombre de joueurs (humains et bots)
//
//		System.out.println("Combien de joueurs humains et de bots ? (total entre 2 et 3)");
//		String[] input = Utils.getLigneSeparee();
//
//		int       nbHumains = Integer.parseInt(input[0]);
//		int       nbBots    = Integer.parseInt(input[1]);
//		Regles    regles;
//		TypeTapis tapis;
//		int       largeur;
//		int       hauteur;

//		while (!ControlleurJeu.nombreDeJoueursValide(nbHumains, nbBots)) {
//			System.out.println(
//					"Arrête de raconter des conneries, utilisateur. Combien de joueurs humains et de bots ? (min 0, max 3)");
//			input = Utils.getLigneSeparee();
//
//			nbHumains = Integer.parseInt(input[0]);
//			nbBots    = Integer.parseInt(input[1]);
//		}
//
//		System.out.println("Quelles règles ? (s. Standard, a. Advanced)");
//		input = Utils.getLigneSeparee();
//		switch (input[0]) {
//			case "s":
//				regles = Regles.Standard;
//				break;
//			case "a":
//				regles = Regles.Advanced;
//				break;
//			default:
//				regles = Regles.Standard;
//				break;
//		}
//
//		System.out.println("Quel tapis ? (t. Triangle - précisez la taille, r. Rectangle - précisez les dimensions)");
//		input = Utils.getLigneSeparee();
//		switch (input[0]) {
//			case "t":
//				tapis = TypeTapis.Triangulaire;
//				largeur = Integer.parseInt(input[1]);
//				hauteur = Integer.parseInt(input[1]);
//				break;
//			case "r":
//				tapis = TypeTapis.Rectangulaire;
//				largeur = Integer.parseInt(input[1]);
//				hauteur = Integer.parseInt(input[2]);
//				break;
//			default:
//				tapis = TypeTapis.Rectangulaire;
//				largeur = 5;
//				hauteur = 3;
//				break;
//		}
//
//		c.commencerNouvellePartie(nbHumains, nbBots, regles, tapis, largeur, hauteur);
		
		c.commencerNouvellePartie(0, 2, Regles.Standard, TypeTapis.Rectangulaire, 5, 3);

		while (!c.jeuTermine()) {
			c.jouer();
		}

		c.afficherScoresDesJoueurs();
	}
}
