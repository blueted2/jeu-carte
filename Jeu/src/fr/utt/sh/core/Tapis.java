package fr.utt.sh.core;

import fr.utt.sh.console_ui.VisitableAffichage;

/**
 * {@link Tapis} est une classe abstraite de base pour toutes les variantes de
 * tapis possibles. <br>
 * 
 * La position d'une carte est donnée par deux parametres integers, pouvant
 * representer soit des coordonnées cartésiens comme dans , soit polaire pour
 * une implémentation de tapis circulaire. <br>
 * {@link Tapis} doit permet un deplacement dynamique du jeu. Cela se fait en
 * ayant les emplacements du bord toujours vides. Si une carte est posé sur ces
 * emplacements, le {@link Tapis} tente de decaler toutes les cartes, en
 * assurant que la taille totales du tapis est toujours respecté.
 * 
 * 
 * 
 * @author grego
 *
 */
public abstract class Tapis implements VisitableAffichage {

	/**
	 * Echanger la position de deux cartes posées sur le tapis. Cette méthode prend
	 * en compte si
	 * 
	 * @param x1 Abscisse de la premiere carte.
	 * @param y1 Ordonnee de la premiere carte.
	 * @param x2 Abscisse de la deuxieme carte.
	 * @param y2 Ordonnee de la deuxieme carte.
	 * @return {@code true} si l'échange a pu etre effectuer, {@code false} sinon.
	 */
	public abstract boolean echangerCartes(int x1, int y1, int x2, int y2);

	/**
	 * Obtenir la {@link Carte} a une position donnée.
	 * 
	 * @param x Abscisse de la carte.
	 * @param y Ordonnee de la carte.
	 * @return {@code null} si la position donnee est invalide, ou il n'u a pas de
	 *         carte a la position spécifiée , sinon une {@link Carte}.
	 */
	public abstract Carte getCarteAt(int x, int y);

	/**
	 * Poser une {@link Carte} a une certaine position.
	 * 
	 * @param carte La carte a poser.
	 * @param x     Abscisse de la carte.
	 * @param y     Ordonnee de la carte.
	 * @return {@link Carte} si il y a bien une carte a la position specifiée,
	 *         {@code null} si il n'y a pas de carte, ou la position n'est pas
	 *         valide.
	 */
	public abstract boolean poserCarte(Carte carte, int x, int y);

	/**
	 * @return {@code true} si le terrain ne peut plus accepter de cartes,
	 *         {@code false} sinon
	 */
	public abstract boolean estRempli();

}
