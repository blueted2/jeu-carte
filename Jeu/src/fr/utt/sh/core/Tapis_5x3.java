package fr.utt.sh.core;

import fr.utt.sh.console_ui.VisitorAffichage;

/**
 * Le tapis de jeu standard. Un rectangle de taille 5x3, mais acceptant des positions 0-6 et 0-4 inclus, afin de permttre au jeu de se decaler. 
 * @author grego
 *
 */
public class Tapis_5x3 extends Tapis {

	private Carte[][] cartes = new Carte[7][5];
	boolean premiereCartePosee = false;

	// La position est elle valide, c'est-a-dire dans les bornes du tapis ?
	boolean positionLegale(int x, int y) {
		if (x < 0 || x > 6)
			return false;
		if (y < 0 || y > 3)
			return false;

		return true;
	}

	// L'emplacement donné a-t-il une carte voisine ?.
	boolean positionAVoisins(int x, int y) {

		int[][] decalages = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } }; // Positions voisines a la carte

		for (int[] decalage : decalages) {
			int xVoisin = decalage[0] + x;
			int yVoisin = decalage[1] + y;

			if (positionLegale(xVoisin, yVoisin)) {
				if (getCarteAt(xVoisin, yVoisin) != null) {
					return true;
				}
			}
		}

		return false;

	}

	@Override
	public Carte getCarteAt(int x, int y) {
		return cartes[x][y];
	}

	@Override
	public boolean echangerCartes(int x1, int y1, int x2, int y2) {

		Carte buff = cartes[x1][y1];
		cartes[x1][y1] = cartes[x2][y2];
		cartes[x2][y2] = buff;

		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @param carte {@inheritDoc}
	 * @param x     de 0 a 6
	 * @param y     de 0 a 4
	 */
	@Override
	public boolean poserCarte(Carte carte, int x, int y) {

		if (!positionLegale(x, y))
			return false;

		if (getCarteAt(x, y) != null)
			return false;

		// Cas particulier pour la premiere carte, car elle ne peut jamais avoir de
		// voisins
		if (premiereCartePosee && !positionAVoisins(x, y))
			return false;

		cartes[x][y] = carte;

		premiereCartePosee = true;
		return true;

	}


	public boolean estRempli() {
		for (int y = 1; y < 4; y++) {
			for (int x = 1; x < 6; x++) {
				if (getCarteAt(x, y) == null)
					return false;
			}
		}
		return true;
	}

	public void accept(VisitorAffichage v) {
		v.visit(this);
	}

}
