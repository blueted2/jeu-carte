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
		c.commencerNouvellePartie(1, 1);		
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
