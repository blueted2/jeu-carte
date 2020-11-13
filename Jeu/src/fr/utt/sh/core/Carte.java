/**
 * 
 */
package fr.utt.sh.core;

/**
 * @author grego
 *
 */
public class Carte implements VisitableAffichage{

	Couleur couleur;
	Remplissage remplissage;

	public Carte(Couleur couleurCarte, Remplissage remplissageCarte) {
		couleur = couleurCarte;
		remplissage = remplissageCarte;
	}

	public Couleur getCouleur() {
		return couleur;
	}

	public Remplissage getRemplissage() {
		return remplissage;
	}
	

	public enum Couleur {
		Rouge, Bleu, Vert
	}

	public enum Remplissage {
		Rempli, Vide
	}

	
	public void accept(VisitorAffichage v) {
		v.visit(this);
		
	}
	// Test
}
