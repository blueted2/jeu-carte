/**
 * 
 */
package fr.utt.sh.core;

import fr.utt.sh.console_ui.VisitableAffichage;
import fr.utt.sh.console_ui.VisitorAffichage;

/**
 * Une implementation de {@link VisitableAffichage}. 
 * @author grego
 *
 */
public class Carte implements VisitableAffichage {

	Couleur couleur;
	Remplissage remplissage;
	Forme forme;

	/**
	 * Constructeur {@link Carte}.
	 * @param couleurCarte La {@link Couleur} de la forme sur la carte.
	 * @param remplissageCarte Le {@link Remplissage} de la forme de la carte.
	 * @param formeCarte La {@link forme} sur la carte.
	 */
	public Carte(Couleur couleurCarte, Remplissage remplissageCarte, Forme formeCarte) {
		couleur = couleurCarte;
		remplissage = remplissageCarte;
		forme = formeCarte;
	}

	/**
	 * @return La {@link Couleur} de la carte
	 */
	public Couleur getCouleur() {
		return couleur;
	}

	/**
	 * @return Le {@link Remplissage} de la carte
	 */
	public Remplissage getRemplissage() {
		return remplissage;
	}

	/**
	 * @return La {@link forme} de la carte
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
	
	public String toString() {
		return String.format("%s %s %s", getForme().name(), getCouleur().name(), getRemplissage().name());
	}
}
