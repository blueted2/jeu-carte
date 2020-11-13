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
		ControlleurJeu controlleurJeu = ControlleurJeu.getInstance();
		
		controlleurJeu.commencerNouvellePartie(4);
		controlleurJeu.getJoueurActuel().jouer();
		controlleurJeu.getJoueurActuel().jouer();
		
		System.out.println(VisitorAffichageString.getRepresentationString(controlleurJeu.getTapis()));
	}

}
