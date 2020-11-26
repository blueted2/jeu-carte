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
		
		//Sélection du nombre de joueurs (humains et bots)
		
		System.out.println("Combien de joueurs humains et de bots ? (min 0, max 3)");
		String[] input = Utils.getLigneSeparee();
		
		int nbHumains = Integer.parseInt(input[0]);
		int nbBots = Integer.parseInt(input[1]);
		
		while(nbHumains > 3 || nbHumains < 0 || nbBots > 3 || nbBots < 0) {
			System.out.println("Arrête de raconter des conneries, utilisateur. Combien de joueurs humains et de bots ? (min 0, max 3)");
			input = Utils.getLigneSeparee();
			nbHumains = Integer.parseInt(input[0]);
			nbBots = Integer.parseInt(input[1]);
		}
		
		if(nbBots + nbHumains > 3) {
			nbBots = 0;
			System.out.println("Le nombre total de joueurs ne peut pas excéder 3. Paramètres modifiées à 3 humains et 0 bot.");
		}

		if (nbBots + nbHumains < 2) {
			nbBots = 2 - (nbBots + nbHumains);
			System.out.println("Le nombre total de joueurs ne peut pas être inférieur à 2. Paramètres modifiées à "+nbHumains+" humain(s) et "+nbBots+" bot(s).");
		}
		
		ControlleurJeu c = ControlleurJeu.getInstance();
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
