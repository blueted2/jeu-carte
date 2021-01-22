package fr.utt.sh.core.tapis;

import fr.utt.sh.console_ui.VisitableAffichage;
import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Position;
import fr.utt.sh.core.score.VisitableComptageScore;
import java.util.Observable;

/**
 * {@link Tapis} est une classe abstraite de base pour toutes les variantes de
 * tapis possibles. <br>
 * 
 * La position d'une carte est donnée par deux paramètres integer, pouvant
 * représenter soit des coordonnées cartésiennes comme pour {@link TapisRect},
 * soit polaires pour une implémentation de tapis circulaire (actuellement il n'y
 * a pas d'implémentations polaires de tapis). <br>
 * {@link Tapis} doit permettre un déplacement dynamique du jeu. Cela se fait en
 * ayant les emplacements du bord toujours vides. Si une carte est posée sur ces
 * emplacements, le {@link Tapis} tente de décaler toutes les cartes, en
 * s'assurant que la taille totale du tapis est toujours respectée.
 * 
 * 
 * 
 * @author grego
 *
 */
public abstract class Tapis extends Observable implements VisitableAffichage, VisitableComptageScore {

	/**
	 * Échanger la position de deux cartes posées sur le tapis. Cette méthode prend
	 * en compte : <br>
	 * 
	 * @param x1 Abscisse de départ de la carte.
	 * @param y1 Ordonnée de départ de la carte.
	 * @param x2 Abscisse d'arrivée de la carte.
	 * @param y2 Ordonnée d'arrivée de la carte.
	 * @return {@code true} si l'échange a pu être effectué, {@code false} sinon.
	 */
	public boolean deplacerCarte(int x1, int y1, int x2, int y2) {
		if (!positionLegale(x1, y1))
			return false;
		if (!positionLegale(x2, y2))
			return false;

		if (getCarteAt(x1, y1) == null)
			return false;

		if (positionSurTapis(x2, y2)) {
			if (getCarteAt(x2, y2) != null) {
				// Si la position d'arrivée est jouable et contient une carte, retourner false;
				return false;
			}

			Carte carteDeplacee = getCarteAt(x1, y1); // Obtenir la carte à déplacer.
			setCarteAt(null, x1, y1); // Supprimer temporairement la carte.

			if (poserCarte(carteDeplacee, x2, y2)) {
				this.setChanged();
				this.notifyObservers();
				return true;
			}

			setCarteAt(carteDeplacee, x1, y1); // Si pas pu poser, la remettre d'où elle vient.
			return false;
		}

		Carte carteDeplacee = getCarteAt(x1, y1); // Obtenir la carte à déplacer.
		setCarteAt(null, x1, y1); // Supprimer temporairement la carte.

		if (poserCarte(carteDeplacee, x2, y2)) { // Essayer de la poser
			this.setChanged();
			this.notifyObservers();
			return true;
		}

		setCarteAt(carteDeplacee, x1, y1); // Si pas pu poser, la remettre d'où elle vient.
		return false;
	}

	/**
	 * Obtenir la {@link Carte} à une position donnée.
	 * 
	 * @param x Abscisse de la carte.
	 * @param y Ordonnée de la carte.
	 * @return {@code null} si la position donnée est invalide, ou s'il n'y a pas de
	 *         carte à la position spécifiée, sinon une {@link Carte}.
	 */
	public abstract Carte getCarteAt(int x, int y);

	abstract boolean setCarteAt(Carte carte, int x, int y);

	/**
	 * Poser une {@link Carte} à une certaine position.
	 * 
	 * @param carte La carte à poser.
	 * @param x     Abscisse de la carte.
	 * @param y     Ordonnée de la carte.
	 * @return {@link Carte} si il y a bien une carte à la position specifiée,
	 *         {@code null} si il n'y a pas de carte, ou si la position n'est pas
	 *         valide.
	 */
	public abstract boolean poserCarte(Carte carte, int x, int y);

	/**
	 * @return {@code true} si le terrain ne peut plus accepter de cartes,
	 *         {@code false} sinon
	 */
	public abstract boolean estRempli();

	/**
	 * Donne un clone du tapis, afin d'avoir un accès direct au tapis, tout en
	 * protégeant celui utilisé pour le jeu.
	 * 
	 * @return Un {@code Tapis} clone.
	 */
	public abstract Tapis getClone();

	/**
	 * Déterminer si à la position donnée, une carte peut être posée, incluant les
	 * bords, c'est-à-dire les positions qui peuvent aussi faire déplacer le tapis.
	 * 
	 * @param x Abscisse de la position.
	 * @param y Ordonnée de la position.
	 * @return {@code true} si la position est légale, {@code false} sinon.
	 */
	public abstract boolean positionLegale(int x, int y);

	/**
	 * Determiner si à la position donnée, une carte peut être posée, n'incluant pas
	 * les bords. <br>
	 * Different de {@link #positionLegale(int, int)}.
	 * 
	 * @param x Abscisse de la position.
	 * @param y Ordonnée de la position.
	 * @return {@code true} si la position est jouable, {@code false} sinon.
	 */
	public abstract boolean positionSurTapis(int x, int y);

	/**
	 * Retire une carte du tapis à la position specifiée.
	 * 
	 * @param x Abscisse de la carte.
	 * @param y Ordonnée de la carte.
	 */
	public void retirerCarte(int x, int y) {
		setCarteAt(null, x, y);
	}

	/**
	 * La position donnée a-t-elle un (des) voisin(s) ?
	 * 
	 * @param x Abscisse position.
	 * @param y Ordonnée position.
	 * @return {@code true} si la position a au moins un voisin, {@code false} sinon.
	 */
	public abstract boolean positionAVoisins(int x, int y);

	/**
	 * Obtenir la largeur du tapis.
	 * 
	 * @return {@code int}.
	 */
	public abstract int getLargeur();

	/**
	 * Obtenir la hauteur du tapis.
	 * 
	 * @return {@code int}.
	 */
	public abstract int getHauteur();

	/**
	 * Indique si le tapis est complètement vide.
	 * 
	 * @return Boolean
	 */
	public abstract boolean estVide();

	/**
	 * Raccourcis pour {@link #getCarteAt(int, int)}.
	 * 
	 * @param posCarte La position de la carte à obtenir.
	 * @return La carte à la position donnée.
	 */
	public Carte getCarteAt(Position posCarte) {
		return getCarteAt(posCarte.getX(), posCarte.getY());
	}

	/**
	 * Vider le tapis.
	 */
	public abstract void clear();
}
