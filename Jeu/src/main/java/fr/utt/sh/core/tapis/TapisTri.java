package fr.utt.sh.core.tapis;

import fr.utt.sh.console_ui.VisitorAffichage;
import fr.utt.sh.core.Carte;
import fr.utt.sh.core.score.VisitorComptageScore;


/**
 * Classe de base pour créer des tapis triangulaires. La hauteur et la largeur
 * seront égales.
 * 
 * @author grego
 *
 */
public class TapisTri extends Tapis {

	int taille;

	boolean premiereCartePosee = false;

	Carte cartes[][];

	/**
	 * Créer un tapis triangulaire de taille {@code taille}.
	 * 
	 * @param taille La taille du tapis triangulaire à créer.
	 */
	public TapisTri(int taille) {
		this.taille = taille;

		cartes = new Carte[taille][];
		// Créer une matrice / liste 2d triangulaire.
		for (int i = 0; i < taille; i++) {
			cartes[i] = new Carte[i + 1];
		}
	}

	/**
	 * Construteur pour cloner un tapis. Prend comme paramètre le tapis à cloner.
	 * 
	 * @param tapis Le tapis à cloner.
	 * 
	 */
	public TapisTri(TapisTri tapis) {

		this.cartes = new Carte[tapis.cartes.length][];
		for (int i = 0; i < tapis.cartes.length; i++) {
			this.cartes[i] = new Carte[tapis.cartes[i].length];

			for (int j = 0; j < tapis.cartes[i].length; j++) {
				this.cartes[i][j] = tapis.cartes[i][j];
			}
		}

		this.taille        = tapis.cartes.length;
		premiereCartePosee = tapis.premiereCartePosee;
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

		if (!positionAVoisins(x, y))
			if (!estVide())
				return false;


		if (positionSurTapis(x, y)) {
			if (getCarteAt(x, y) != null)
				return false;
			setCarteAt(carte, x, y);
			this.setChanged();
			this.notifyObservers();
			return true;
		}

		if (x == -1) {
			if (decalerADroite()) {
				setCarteAt(carte, 0, y);
				this.setChanged();
				this.notifyObservers();
				return true;
			}
			return false;
		} else if (y == -1) {
			if (decalerEnBas()) {
				setCarteAt(carte, x, 0);
				this.setChanged();
				this.notifyObservers();
				return true;
			}
			return false;

		} else if (y >= taille) { // Carte en-dessous du tapis
			if (decalerEnHaut()) {
				setCarteAt(carte, x, taille - 1);
				this.setChanged();
				this.notifyObservers();
				return true;
			}
			return false;
		}

		// Carte a droite du tapis
		else if (x > y) {
			if (decalerAGauche()) {
				setCarteAt(carte, y, y);
				this.setChanged();
				this.notifyObservers();
				return true;
			}

			if (decalerEnBas()) {
				setCarteAt(carte, x, x);
				this.setChanged();
				this.notifyObservers();
				return true;
			}
			return false;

		}

		return false;
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
	 * Décale le tapis vers la gauche.
	 * 
	 * @return {@code true} si le décalage a pu se faire,
	 *         {@code false} sinon (colonne de gauche non-vide).
	 */
	boolean decalerAGauche() {
		// Verifier que la colonne gauche est vide.
		for (int y = 0; y < taille; y++) {
			if (getCarteAt(0, y) != null)
				return false;
		}

		// On commence à y=1, car s'il n'y a pas de carte dans la première colonne, il n'y en aura
		// pas une tout en haut.
		for (int y = 1; y < taille; y++) {
			for (int x = 0; x < y; x++) {
				setCarteAt(getCarteAt(x + 1, y), x, y);
			}
			setCarteAt(null, y - 1, y - 1);
		}
		return true;
	}

	/**
	 * Décale le tapis vers la droite.
	 * 
	 * @return {@code true} si le décalage a pu se faire,
	 *         {@code false} sinon (colonne de droite non-vide).
	 */
	boolean decalerADroite() {

		// Verifier sur le côté droit qu'il n'y a pas de cartes.
		for (int y = 0; y < taille; y++) {
			if (getCarteAt(y, y) != null)
				return false;
		}

		// On commence a y=1, car s'il n'y a pas de carte à droite, il n'y en aura pas une en
		// haut.
		for (int y = 1; y < taille; y++) {
			for (int x = y; x > 0; x--) {
				setCarteAt(getCarteAt(x - 1, y), x, y);
			}
			setCarteAt(null, 0, y);
		}
		return true;
	}

	/**
	 * Décale le tapis vers le haut.
	 * 
	 * @return {@code true} si le décalage a pu se faire,
	 *         {@code false} sinon (ligne du haut non-vide).
	 */
	boolean decalerEnHaut() {
		// Verifier sur le coté droit qu'il n'y a pas de cartes.
		for (int y = 0; y < taille; y++) {
			if (getCarteAt(y, y) != null)
				return false;
		}

		for (int x = 0; x < taille; x++) {
			for (int y = x; y < taille - 1; y++) {
				setCarteAt(getCarteAt(x, y + 1), x, y);
			}
			setCarteAt(null, x, taille - 1);
		}
		return true;
	}

	/**
	 * Décale le tapis vers le bas.
	 * 
	 * @return {@code true} si le décalage a pu se faire,
	 *         {@code false} sinon (ligne du bas non-vide).
	 */
	boolean decalerEnBas() {
		for (int x = 0; x < taille; x++) {
			if (getCarteAt(x, taille - 1) != null)
				return false;
		}

		for (int x = 0; x < taille; x++) {
			for (int y = taille - 1; y > x; y--) {
				setCarteAt(getCarteAt(x, y - 1), x, y);
			}
			setCarteAt(null, x, x);
		}
		return true;
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
	boolean setCarteAt(Carte carte, int x, int y) {
		if (!positionSurTapis(x, y))
			return false;

		cartes[y][x] = carte;
		return true;
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

		return cartes[y][x];
	}

	/**
	 * Vérifie si le tapis est rempli.
	 * 
	 * @return {@code true} si le tapis est rempli,
	 *         {@code false} sinon.
	 */
	@Override
	public boolean estRempli() {
		for (int y = 0; y < taille; y++) {
			for (int x = 0; x <= y; x++) {
				if (getCarteAt(x, y) == null) {
					return false;
				}
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
		for (int y = 0; y < taille; y++) {
			for (int x = 0; x <= y; x++) {

				if (getCarteAt(x, y) != null) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public Tapis getClone() {
		return new TapisTri(this);
	}

	/**
	 * Retire du tapis la carte présente à des coordonnées données.
	 * 
	 * @param x     Abscisse de la carte à retirer.
	 * @param y     Ordonnée de la carte à retirer.
	 */
	@Override
	public void retirerCarte(int x, int y) {
		setCarteAt(null, x, y);
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
	@Override
	public boolean positionLegale(int x, int y) {
		if (x < -1)
			return false;
		if (y < -1)
			return false;
		if (x > y + 1)
			return false;

		if (y >= taille + 1)
			return false;

		if (x == -1 || x == taille)
			if (y == -1 || y == taille)
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
	@Override
	public boolean positionSurTapis(int x, int y) {
		if (x < 0)
			return false;
		if (y < 0)
			return false;
		if (x > y)
			return false;
		if (y >= taille)
			return false;

		return true;
	}

	/**
	 * Getter largeur du tapis.
	 * 
	 * @return int
	 */
	@Override
	public int getLargeur() {
		return taille;
	}

	/**
	 * Getter hauteur du tapis.
	 * 
	 * @return int
	 */
	@Override
	public int getHauteur() {
		return taille;
	}

	public void accept(VisitorAffichage v) {
		v.visit(this);
	}

	public void accept(VisitorComptageScore v) {
		v.visit(this);

	}

	@Override
	public void clear() {
		cartes = new Carte[taille][];
		// Créer une matrice / liste 2d triangulaire.
		for (int i = 0; i < taille; i++) {
			cartes[i] = new Carte[i + 1];
		}
		this.setChanged();
		this.notifyObservers();
	}

}
