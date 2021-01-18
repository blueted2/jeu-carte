package fr.utt.sh.core;

/**
 * Une enumerations des differentes regles du jeu possible.
 * 
 * @author grego
 *
 */
public enum Regles {
	/**
	 * Regles standard.
	 */
	Standard,
	/**
	 * Regles advanved.
	 */
	Advanced,

	/**
	 * Une variante des regles Advanced, ou toutes les cartes sont distribués au
	 * debut du jeu, et le score du joueur déterminé avec la derniere carte du
	 * joueur.
	 */
	Variante
}
