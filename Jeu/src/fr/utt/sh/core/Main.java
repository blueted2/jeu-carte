package fr.utt.sh.core;

import fr.utt.sh.console_ui.VisitorAffichageString;

/**
 * 
 * 
 * @author grego
 *
 */
public class Main {

	public static void main(String[] args) {
		ControlleurJeu c = ControlleurJeu.getInstance();
		
		c.commencerNouvellePartie(4);
		for(int i=0; i<20; i++) {
			c.getJoueurActuel().jouer();
			System.out.println(VisitorAffichageString.getRepresentationString(c.getTapis()));
			c.passerAuJoueurSuivant();
		}
	}

}
