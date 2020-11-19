package fr.utt.sh.core;

import fr.utt.sh.console_ui.VisitorAffichageString;
import fr.utt.sh.core.tapis.Tapis;
import fr.utt.sh.core.tapis.Tapis_Triangulaire;

/**
 * 
 * 
 * @author grego
 *
 */
public class Main {

	public static void main(String[] args) {
		ControlleurJeu c = ControlleurJeu.getInstance();
		c.commencerNouvellePartie(0, 2);
		
		while (!c.tapisEstRempli()) {
			c.jouer();
		}
		
//		Tapis tapis = new Tapis_Triangulaire(5);
//		
//		System.out.println(VisitorAffichageString.getRepresentationStringStatic(tapis));
//		
//		Carte c = new Carte(Carte.Couleur.Rouge, Carte.Remplissage.Rempli, Carte.Forme.Carre);
//		
//		tapis.poserCarte(c, 0, 3);
//		System.out.println(VisitorAffichageString.getRepresentationStringStatic(tapis));
//		
//		tapis.poserCarte(c, 1, 0);
//		System.out.println(VisitorAffichageString.getRepresentationStringStatic(tapis));
	}
}
