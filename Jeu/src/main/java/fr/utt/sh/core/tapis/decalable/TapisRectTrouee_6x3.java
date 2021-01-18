package fr.utt.sh.core.tapis.decalable;

import fr.utt.sh.core.Position;
import fr.utt.sh.core.tapis.TapisRect;

/**
 * Une variante de {@link TapisRect}, avec une taille fixe, et definissant des
 * positions du tapis sur lesquelles des cartes ne peuvent pas etres posés.
 * 
 * @author grego
 *
 */
public class TapisRectTrouee_6x3 extends TapisRect {

	private Position[] trous = { new Position(1, 1), new Position(4, 0), new Position(4, 2) };

	/**
	 * Constructeur par defaut.
	 */
	public TapisRectTrouee_6x3() {
		super(6, 3);
	}

	/**
	 * Constructeur clonage.
	 * 
	 * @param tapis Le {@link TapisRectTrouee_6x3} voulant etre cloné.
	 */
	public TapisRectTrouee_6x3(TapisRectTrouee_6x3 tapis) {
		super(tapis);
	}

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
