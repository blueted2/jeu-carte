/**
 * 
 */
package fr.utt.sh.core;

/**
 * Une classe pour représenter toutes les cartes du jeu. Une {@code Carte} est
 * immuable, son état pouvant seulement être défini lors de sa création.
 * 
 * @author grego
 *
 */
public class Carte {

	private Couleur     couleur;
	private Remplissage remplissage;
	private Forme       forme;

	/**
	 * Constructeur {@link Carte}.
	 * 
	 * @param couleurCarte     La {@link Couleur} de la forme sur la carte.
	 * @param remplissageCarte Le {@link Remplissage} de la forme de la carte.
	 * @param formeCarte       La {@link Forme} sur la carte.
	 */
	public Carte(Couleur couleurCarte, Remplissage remplissageCarte, Forme formeCarte) {
		couleur     = couleurCarte;
		remplissage = remplissageCarte;
		forme       = formeCarte;
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
	 * @return La {@link Forme} de la carte
	 */
	public Forme getForme() {
		return forme;
	}

	/**
	 * Les couleurs possibles qu'une {@code Carte} peut avoir.
	 * 
	 * @author grego
	 *
	 */
	public enum Couleur {
		/**
		 * Couleur rouge.
		 */
		Rouge,
		/**
		 * Couleur bleu.
		 */
		Bleu,
		/**
		 * Couleur vert.
		 */
		Vert
	}

	/**
	 * Les remplissages possibles de la forme sur une carte.
	 * 
	 * @author grego
	 *
	 */
	public enum Remplissage {
		/**
		 * Une forme remplie.
		 */
		Rempli,
		/**
		 * Une forme vide.
		 */
		Vide
	}

	/**
	 * Les formes possibles sur une carte.
	 * 
	 * @author grego
	 *
	 */
	public enum Forme {
		/**
		 * Forme triangle.
		 */
		Triangle,
		/**
		 * Forme carré.
		 */
		Carre,
		/**
		 * Forme cercle.
		 */
		Cercle
	}

	/**
	 * @return Les caractéristiques des cartes sous forme de String.
	 */
	public String toString() {
		return String.format("%s %s %s", getForme().name(), getCouleur().name(), getRemplissage().name());
	}

}

