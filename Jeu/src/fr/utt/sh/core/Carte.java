/**
 * 
 */
package fr.utt.sh.core;

import fr.utt.sh.console_ui.VisitableAffichage;
import fr.utt.sh.console_ui.VisitorAffichage;
import fr.utt.sh.core.Carte.Remplissage;

/**
 * Une implementation de {@link VisitableAffichage}.
 * 
 * @author grego
 *
 */
public class Carte {

	private Couleur     couleur;
	private Remplissage remplissage;
	private Forme       forme;

	private String stringCarte;

	/**
	 * Constructeur {@link Carte}.
	 * 
	 * @param couleurCarte     La {@link Couleur} de la forme sur la carte.
	 * @param remplissageCarte Le {@link Remplissage} de la forme de la carte.
	 * @param formeCarte       La {@link forme} sur la carte.
	 */
	public Carte(Couleur couleurCarte, Remplissage remplissageCarte, Forme formeCarte) {
		couleur     = couleurCarte;
		remplissage = remplissageCarte;
		forme       = formeCarte;

		char   charCouleur = getCouleur().name().charAt(0);
		String charForme   = getCharForme();
		stringCarte = String.format("%s%s", charCouleur, charForme);
	}
	
	private String getCharForme() {
		if (getRemplissage() == Remplissage.Rempli)
			switch (getForme()) {
				case Carre:
					return "■";
				case Cercle:
					return "▲";
				case Triangle:
					return "●";
				default:
					return "?";
			}

		else
			switch (getForme()) {
				case Carre:
					return "□";
				case Cercle:
					return "○";
				case Triangle:
					return "△";
				default:
					return "?";
			}

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
	 * 
	 * @author grego
	 *
	 */
	public enum Forme {
		Triangle, Carre, Cercle
	}

	public String toString() {
		return String.format("%s %s %s", getForme().name(), getCouleur().name(), getRemplissage().name());
	}
	
	public String getStringCarte() {
		return stringCarte;
	}

}
