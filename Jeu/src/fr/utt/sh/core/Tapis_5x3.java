package fr.utt.sh.core;

import fr.utt.sh.console_ui.VisitorAffichage;

/**
 * Le tapis de jeu standard. Un rectangle de taille 5x3, mais acceptant des
 * positions 0-6 et 0-4 inclus, afin de permttre au jeu de se decaler.
 * 
 * @author grego
 *
 */
public class Tapis_5x3 extends Tapis {

	private Carte[][] cartes = new Carte[5][3];
	boolean premiereCartePosee = false;

	// La position est elle valide, c'est-a-dire dans les bornes du tapis ? Peu
	// inclure les bords pour permettre au tapis de se decaller.
	boolean positionLegale(int x, int y) {
		if (x < -1 || x > 5)
			return false;
		if (y < -1 || y > 3)
			return false;

		return true;
	}

	// La position est elle valide, mais cette fois sans les bords.
	boolean positionJouable(int x, int y) {
		if (x < 0 || x > 4)
			return false;
		if (y < 0 || y > 2)
			return false;

		return true;
	}

	// L'emplacement donné a-t-il une carte voisine ?.
	boolean positionAVoisins(int x, int y) {

		int[][] decalages = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } }; // Positions relatives des cartes voisines

		for (int[] decalage : decalages) {
			int xVoisin = decalage[0] + x;
			int yVoisin = decalage[1] + y;

			if (positionJouable(xVoisin, yVoisin)) {
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

	// Pas de vérification
	void setCarteAt(Carte carte, int x, int y) {
		cartes[x][y] = carte;
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
	 * @param x     de 0 a 4
	 * @param y     de 0 a 2
	 */
	@Override
	public boolean poserCarte(Carte carte, int x, int y) {

		if (!positionLegale(x, y))
			return false;

		// Cas particulier pour la première carte, car elle ne peut jamais avoir de
		// voisins
		if (premiereCartePosee && !positionAVoisins(x, y))
			return false;

		// Si la position n'est pas sur les bords, est ce qu'il y a une carte ?
		if (positionJouable(x, y)) {
			if (getCarteAt(x, y) != null)
				return false;
			setCarteAt(carte, x, y);
			premiereCartePosee = true;
			return true;
		}

		if (x == -1) {
			if (!decalerADroite()) // Si le tapis a pu etre decalé a droite
				return false;

			setCarteAt(carte, 0, y);
			premiereCartePosee = true;
			return true;
		}
		return false;
	}

	boolean decalerAGauche() {
		for (int y = 0; y < 3; y++) {
			// Si il y a une carte sur la colonne de gauche, les cartes ne peuvent pas être
			// décalés.
			if (getCarteAt(0, y) != null) {
				return false;
			}
		}

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 4; x++) {
				Carte c = getCarteAt(x + 1, y);
				setCarteAt(c, x, y);
			}
			setCarteAt(null, 4, y);
		}
		return true;
	}

	boolean decalerADroite() {
		for (int y = 0; y < 3; y++) {
			// Si il y a une carte sur la colonne de gauche, les cartes ne peuvent pas être
			// décalés.
			if (getCarteAt(4, y) != null) {
				return false;
			}
		}

		for (int y = 0; y < 3; y++) {
			for (int x = 4; x > 0; x--) {
				Carte c = getCarteAt(x - 1, y);
				setCarteAt(c, x, y);
			}
			setCarteAt(null, 0, y);
		}
		return true;
	}

	public boolean estRempli() {
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 5; x++) {
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
