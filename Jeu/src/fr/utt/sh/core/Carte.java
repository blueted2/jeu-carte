/**
 * 
 */
package fr.utt.sh.core;

import fr.utt.sh.console_ui.VisitableAffichage;
import fr.utt.sh.console_ui.VisitorAffichage;

/**
 * @author grego
 *
 */
public class Carte implements VisitableAffichage{

	Couleur couleur;
	Remplissage remplissage;
	Forme forme;

	public Carte(Couleur couleurCarte, Remplissage remplissageCarte, Forme formeCarte) {
		couleur = couleurCarte;
		remplissage = remplissageCarte;
		forme = formeCarte;
	}

	public Couleur getCouleur() {
		return couleur;
	}

	public Remplissage getRemplissage() {
		return remplissage;
	}
	
	public Forme getForme() {
		return forme;
	}

	/**
	 * Couleurs de carte valides. 
	 * @author grego
	 *
	 */
	public enum Couleur {
		Rouge, Bleu, Vert
	}
	
	/**
	 * Remplissages de carte valides. 
	 * @author grego
	 *
	 */
	public enum Remplissage {
		Rempli, Vide
	}
	
	public enum Forme {
		Triangle, Carre, Cercle
	}

	
	public void accept(VisitorAffichage v) {
		v.visit(this);
		
	}
}
