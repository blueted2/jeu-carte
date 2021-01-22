package fr.utt.sh.core.tapis;

import fr.utt.sh.console_ui.VisitorAffichage;
import fr.utt.sh.core.Carte;
import fr.utt.sh.core.score.VisitorComptageScore;

/**
 * Le tapis de jeu standard. Un rectangle de taille largeur x hauteur, mais
 * acceptant des positions -1 à largeur et -1 à hauteur inclus, afin de permettre
 * au jeu de se décaler.
 * 
 * @author grego
 *
 */
public class TapisRect extends Tapis {

	private Carte[][] cartes;
	protected boolean premiereCartePosee = false;

	protected int largeur;
	protected int hauteur;

	/**
	 * Constructeur pour un tapis rectangulaire général.
	 * 
	 * @param largeur Largeur du tapis.
	 * @param hauteur Hauteur du tapis.
	 */
	public TapisRect(int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;

		cartes = new Carte[largeur][hauteur];
	}

	/**
	 * Constructeur pour cloner un tapis. Prendre comme paramètre le tapis à cloner.
	 * 
	 * @param tapis Le {@code Tapis} à cloner.
	 */
	public TapisRect(TapisRect tapis) {
		this.cartes = new Carte[tapis.cartes.length][];

		for (int i = 0; i < tapis.cartes.length; i++) {
			this.cartes[i] = new Carte[tapis.cartes[i].length];

			for (int j = 0; j < tapis.cartes[i].length; j++) {
				this.cartes[i][j] = tapis.cartes[i][j];
			}
		}

		largeur = tapis.cartes.length;
		hauteur = tapis.cartes[0].length;
		premiereCartePosee = tapis.premiereCartePosee;
	}

	/**
	 * Getter largeur du tapis.
	 * 
	 * @return int
	 */
	public int getLargeur() {
		return largeur;
	}

	/**
	 * Getter hauteur du tapis.
	 * 
	 * @return int
	 */
	public int getHauteur() {
		return hauteur;
	}

	// La position est-elle valide, c'est-à-dire dans les bornes du tapis ? Peut
	// inclure les bords pour permettre au tapis de se décaler.
	public boolean positionLegale(int x, int y) {
		if (x < -1 || x > largeur)
			return false;
		if (y < -1 || y > hauteur)
			return false;

		if (x == -1 || x == largeur)
			if (y == -1 || y == hauteur)
				return false;

		return true;
	}

	// La position est-elle valide, mais cette fois sans les bords.
	public boolean positionSurTapis(int x, int y) {
		if (x < 0 || x > largeur - 1)
			return false;
		if (y < 0 || y > hauteur - 1)
			return false;

		return true;
	}

	// L'emplacement donné a-t-il une carte voisine ?
	public boolean positionAVoisins(int x, int y) {

		int[][] decalages = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } }; // Positions relatives des cartes voisines

		for (int[] decalage : decalages) {
			int xVoisin = decalage[0] + x;
			int yVoisin = decalage[1] + y;

			if (positionSurTapis(xVoisin, yVoisin)) {
				if (getCarteAt(xVoisin, yVoisin) != null) {
					return true;
				}
			}
		}

		return false;

	}

	@Override
	public Carte getCarteAt(int x, int y) {
		if (!positionSurTapis(x, y))
			return null;
		return cartes[x][y];
	}

	protected boolean setCarteAt(Carte carte, int x, int y) {
		if (!positionLegale(x, y))
			return false;
		cartes[x][y] = carte;

		return true;
	}

	@Override
	public boolean poserCarte(Carte carte, int x, int y) {

		if (!positionLegale(x, y))
			return false;

		// Cas particulier pour la première carte, car elle ne peut jamais avoir de
		// voisins
		if (!estVide() && !positionAVoisins(x, y))
			return false;

		// Si la position n'est pas sur les bords, est ce qu'il y a une carte ?
		if (positionSurTapis(x, y)) {
			if (getCarteAt(x, y) != null)
				return false;
			setCarteAt(carte, x, y);

			this.setChanged();
			this.notifyObservers();
			return true;
		}

		return false;
	}

	public boolean estRempli() {
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < largeur; x++) {
				if (getCarteAt(x, y) == null)
					return false;
			}
		}
		return true;
	}

	public boolean estVide() {
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < largeur; x++) {
				if (getCarteAt(x, y) != null)
					return false;
			}
		}
		return true;
	}

	@Override
	public Tapis getClone() {
		return new TapisRect(this);
	}

	@Override
	public void accept(VisitorAffichage v) {
		v.visit(this);
	}

	@Override
	public void accept(VisitorComptageScore v) {
		v.visit(this);
	}

	@Override
	public void clear() {
		cartes = new Carte[largeur][hauteur];
		this.setChanged();
		this.notifyObservers();

	}

}
