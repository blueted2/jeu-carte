package fr.utt.sh.core;

/**
 * 
 * 
 * @author grego
 *
 */
public class Main {

	public static void main(String[] args) {
		ControlleurJeu c = ControlleurJeu.getInstance();
		c.commencerNouvellePartie(2, 0);		
		while (!c.tapisEstRempli()) {
			c.jouer();
		}
	}
}
