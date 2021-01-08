package fr.utt.sh.core.tapis.decalable;

import fr.utt.sh.console_ui.VisitorAffichage;
import fr.utt.sh.core.Carte;
import fr.utt.sh.core.tapis.Decalable;
import fr.utt.sh.core.tapis.TapisRect;

public class TapisRectDecalable extends TapisRect implements Decalable{

	public TapisRectDecalable(int largeur, int hauteur) {
		super(largeur, hauteur);
	}

	@Override
	public boolean poserCarte(Carte carte, int x, int y) {
		if(super.poserCarte(carte, x, y))
			return true;
		
		if (x == -1) {
			if (!decalerADroite())
				return false;
			setCarteAt(carte, 0, y);
		} else if (x == largeur) {
			if (!decalerAGauche())
				return false;
			setCarteAt(carte, largeur - 1, y);

		} else if (y == -1) {
			if (!decalerEnBas())
				return false;
			setCarteAt(carte, x, 0);
		} else if (y == hauteur) {
			if (!decalerEnHaut())
				return false;
			setCarteAt(carte, x, hauteur - 1);
		}

		premiereCartePosee = true;
		this.setChanged();
		this.notifyObservers();

		return true;
		
	}
	
	boolean decalerAGauche() {
		for (int y = 0; y < hauteur; y++) {
			// Si il y a une carte sur la colonne de gauche, les cartes ne peuvent pas être
			// décalés.
			if (getCarteAt(0, y) != null) {
				return false;
			}
		}

		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < largeur - 1; x++) {
				Carte c = getCarteAt(x + 1, y);
				setCarteAt(c, x, y);
			}
			setCarteAt(null, largeur - 1, y);
		}
		return true;
	}

	boolean decalerADroite() {
		for (int y = 0; y < hauteur; y++) {
			// Si il y a une carte sur la colonne de gauche, les cartes ne peuvent pas être
			// décalés.
			if (getCarteAt(largeur - 1, y) != null) {
				return false;
			}
		}

		for (int y = 0; y < hauteur; y++) {
			for (int x = largeur - 1; x > 0; x--) {
				Carte c = getCarteAt(x - 1, y);
				setCarteAt(c, x, y);
			}
			setCarteAt(null, 0, y);
		}
		return true;
	}

	boolean decalerEnHaut() {
		for (int x = 0; x < largeur; x++) {
			if (getCarteAt(x, 0) != null)
				return false;
		}

		for (int x = 0; x < largeur; x++) {
			for (int y = 0; y < hauteur - 1; y++) {
				Carte c = getCarteAt(x, y + 1);
				setCarteAt(c, x, y);
			}
			setCarteAt(null, x, hauteur - 1);
		}
		return true;

	}

	boolean decalerEnBas() {
		for (int x = 0; x < largeur; x++) {
			if (getCarteAt(x, hauteur - 1) != null)
				return false;
		}

		for (int x = 0; x < largeur; x++) {
			for (int y = hauteur - 1; y > 0; y--) {
				Carte c = getCarteAt(x, y - 1);
				setCarteAt(c, x, y);
			}
			setCarteAt(null, x, 0);
		}
		return true;

	}
	
	@Override
	public void accept(VisitorAffichage v) {
		v.visit(this);
	}

}
