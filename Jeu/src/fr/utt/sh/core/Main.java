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
		
//		c.getJoueurActuel().jouer();
//		
//		Tapis_5x3 tapis = (Tapis_5x3)c.getTapis();
//		tapis.decalerADroite();
//		System.out.println(VisitorAffichageString.getRepresentationString(c.getTapis()));
////
//		
		while (!c.tapisEstRempli()) {
			c.getJoueurActuel().jouer();
			System.out.println(VisitorAffichageString.getRepresentationString(c.getTapis()));
		}
	}
}
