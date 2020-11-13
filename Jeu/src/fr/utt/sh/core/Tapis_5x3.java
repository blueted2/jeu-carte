package fr.utt.sh.core;

public class Tapis_5x3 extends Tapis {

	private Carte[][] cartes = new Carte[7][5];
	
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
	 * @param carte {@inheritDoc}
	 * @param x de 0 a 6
	 * @param y de 0 a 4
	 */
	@Override
	public boolean poserCarte(Carte carte, int x, int y) {
		if (cartes[x][y] != null)
			return false;

		cartes[x][y] = carte;
		
		return true;

	}

	public void accept(VisitorAffichage v) {
		v.visit(this);
	}

}
