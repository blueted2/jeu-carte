package fr.utt.sh.core.tapis.decalable;

import fr.utt.sh.core.Position;
import fr.utt.sh.core.tapis.TapisRect;

/**
 * Une variante de {@link TapisRect}, avec une taille fixe, et définissant des
 * positions du tapis sur lesquelles des cartes ne peuvent pas être posées.
 * 
 * @author grego
 *
 */
public class TapisRectTrouee_6x3 extends TapisRect {

	private Position[] trous = { new Position(1, 1), new Position(4, 0), new Position(4, 2) };

	/**
	 * Constructeur par défaut.
	 */
	public TapisRectTrouee_6x3() {
		super(6, 3);
	}

	/**
	 * Constructeur clonage.
	 * 
	 * @param tapis Le {@link TapisRectTrouee_6x3} que l'on veut cloner.
	 */
	public TapisRectTrouee_6x3(TapisRectTrouee_6x3 tapis) {
		super(tapis);
	}

	/**
	 * Vérifie si une position donnée est valide, c'est-à-dire sur le tapis. 
	 * Cette vérification doit prendre en compte les "trous" sur lesquels on ne peut pas poser de carte. 
	 * 
	 * @param x     Abscisse de la position à vérifier.
	 * @param y     Ordonnée de la position à vérifier.
	 * 
	 * @return {@code true} si la position est valide,
	 *         {@code false} sinon.
	 */
	@Override
	public boolean positionLegale(int x, int y) {
		if (!super.positionLegale(x, y))
			return false;

		for (Position trou : trous) {
			if (trou.getX() == x && trou.getY() == y)
				return false;
		}
		return true;
	}

	@Override
	public TapisRectTrouee_6x3 getClone() {
		return new TapisRectTrouee_6x3(this);
	}

}
