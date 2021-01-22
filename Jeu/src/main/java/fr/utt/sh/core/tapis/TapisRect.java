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
	 * Constructeur pour cloner un tapis. Prend comme paramètre le tapis à cloner.
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

	/**
	 * Vérifie si une position donnée est valide, c'est-à-dire sur le tapis. 
	 * Cette vérification inclut les bords du tapis, afin de lui permettre de se déplacer.
	 * 
	 * @param x     Abscisse de la position à vérifier.
	 * @param y     Ordonnée de la position à vérifier.
	 * 
	 * @return {@code true} si la position est valide,
	 *         {@code false} sinon.
	 */
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

	/**
	 * Vérifie si une position donnée est valide, c'est-à-dire sur le tapis. 
	 * Contrairement à la précédente, cette vérification n'inclut pas les bords du tapis.
	 * 
	 * @param x     Abscisse de la position à vérifier.
	 * @param y     Ordonnée de la position à vérifier.
	 * 
	 * @return {@code true} si la position est valide,
	 *         {@code false} sinon.
	 */
	public boolean positionSurTapis(int x, int y) {
		if (x < 0 || x > largeur - 1)
			return false;
		if (y < 0 || y > hauteur - 1)
			return false;

		return true;
	}

	/**
	 * Vérifie si une position donnée a des cartes voisines.
	 * 
	 * @param x     Abscisse de la position à vérifier.
	 * @param y     Ordonnée de la position à vérifier.
	 * 
	 * @return {@code true} si la position a des cartes voisines,
	 *         {@code false} sinon.
	 */
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

	/**
	 * Getter qui récupère la carte posée à un emplacement donné.
	 * 
	 * @param x     Abscisse de la carte à récupérer.
	 * @param y     Ordonnée de la carte à récupérer.
	 * 
	 * @return une carte, ou {@code null} si la position donnée n'est pas valide.
	 */
	@Override
	public Carte getCarteAt(int x, int y) {
		if (!positionSurTapis(x, y))
			return null;
		return cartes[x][y];
	}

	/**
	 * Setter qui pose une carte donnée à un emplacement donné.
	 * 
	 * @param x     Abscisse de la position souhaitée.
	 * @param y     Ordonnée de la position souhaitée.
	 * @param carte Carte à poser.
	 * 
	 * @return {@code true} si la carte a pu être posée (position légale),
	 *         {@code false} sinon.
	 */
	protected boolean setCarteAt(Carte carte, int x, int y) {
		if (!positionLegale(x, y))
			return false;
		cartes[x][y] = carte;

		return true;
	}

	/**
	 * Vérifie si un emplacement donné est valide pour y poser une carte donnée, puis le cas échéant la pose en appelant {@code setCarteAt}. 
	 * 
	 * @param x     Abscisse de la position souhaitée.
	 * @param y     Ordonnée de la position souhaitée.
	 * @param carte Carte à poser.
	 * 
	 * @return {@code true} si la carte a pu être posée,
	 *         {@code false} sinon.
	 */
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

	/**
	 * Vérifie si le tapis est rempli.
	 * 
	 * @return {@code true} si le tapis est rempli,
	 *         {@code false} sinon.
	 */
	public boolean estRempli() {
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < largeur; x++) {
				if (getCarteAt(x, y) == null)
					return false;
			}
		}
		return true;
	}

	/**
	 * Vérifie si le tapis est vide.
	 * 
	 * @return {@code true} si le tapis est vide,
	 *         {@code false} sinon.
	 */
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
