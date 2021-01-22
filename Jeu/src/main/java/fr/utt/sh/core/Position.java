package fr.utt.sh.core;

/**
 * Cette classe sert à représenter une position sur un tapis avec deux
 * coordonnées. Elle est immuable, ses valeurs pouvant seulement être attribuées
 * lors de sa création.
 * 
 * @author grego
 *
 */
public class Position {

	private int x, y;

	/**
	 * Constructeur de la position.
	 * 
	 * @param x L'abscisse de la position.
	 * @param y L'ordonnée de la position.
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Obtenir l'abscisse de la position.
	 * 
	 * @return int.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Obtenir l'ordonnée de la position.
	 * 
	 * @return int.
	 */
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return String.format("( %d, %d )", x, y);
	}
}
