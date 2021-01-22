package fr.utt.sh.core;

/**
 * Une énumeration des différentes règles du jeu possibles.
 * 
 * @author grego
 *
 */
public enum Regles {
	/**
	 * Règles standard.
	 */
	Standard,
	/**
	 * Règles advanced.
	 */
	Advanced,

	/**
	 * Une variante des règles Advanced, où toutes les cartes sont distribuées au
	 * debut du jeu, et le score du joueur déterminé avec la dernière carte du
	 * joueur.
	 */
	Variante
}
