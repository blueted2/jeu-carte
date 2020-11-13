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
public class Carte implements VisitableAffichage {

	Couleur couleur;
	Remplissage remplissage;
	Forme forme;

	public Carte(Couleur couleurCarte, Remplissage remplissageCarte, Forme formeCarte) {
		couleur = couleurCarte;
		remplissage = remplissageCarte;
		forme = formeCarte;
	}

	/**
	 * @return La {@code Couleur} de la carte
	 */
	public Couleur getCouleur() {
		return couleur;
	}

	/**
	 * @return Le {@code Remplissage} de la carte
	 */
	public Remplissage getRemplissage() {
		return remplissage;
	}

	/**
	 * @return @return La {@code forme} de la carte
	 */
	public Forme getForme() {
		return forme;
	}

	/**
	 * Couleurs possible d'une carte.
	 * 
	 * @author grego
	 *
	 */
	public enum Couleur {
		Rouge, Bleu, Vert
	}

	/**
	 * Remplissages possibles de la forme sur une carte.
	 * 
	 * @author grego
	 *
	 */
	public enum Remplissage {
		Rempli, Vide
	}

	/**
	 * Formes possibles sur une carte.
	 * @author grego
	 *
	 */
	public enum Forme {
		Triangle, Carre, Cercle
	}

	public void accept(VisitorAffichage v) {
		v.visit(this);

	}
}
