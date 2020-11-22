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
		c.commencerNouvellePartie(2, 0, Regles.Advanced);		
		
		
		
//		Carte carte = c.getJoueurActuel().getCarteDansMain(0);
//		
//		System.out.print(carte);
//		
//		c.joueurActuelPoseCarteDansMain(carte, 0, 0);
//		c.joueurActuelPiocheCarte();
//		c.passerAuJoueurSuivant();
		
		
		
		while (!c.tapisEstRempli()) {
			c.jouer();
		}
	}
}
