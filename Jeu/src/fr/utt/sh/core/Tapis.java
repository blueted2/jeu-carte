package fr.utt.sh.core;

import fr.utt.sh.console_ui.VisitableAffichage;

/**
 * {@code Tapis} est une classe abstraite de base pour toutes les variantes de
 * tapis possibles. <br>
 * 
 * La position d'une carte est donn�e par deux parametres integers, pouvant
 * representer soit des coordonn�es cart�siens comme dans , soit polaire pour
 * une impl�mentation de tapis circulaire. <br>
 * {@code Tapis} doit permet un deplacement dynamique du jeu. Cela se fait en
 * ayant les emplacements du bord toujours vides. Si une carte est pos� sur ces
 * emplacements, le {@code Tapis} tente de decaler toutes les cartes, en
 * assurant que la taille totales du tapis est toujours respect�.
 * 
 * 
 * 
 * @author grego
 *
 */
public abstract class Tapis implements VisitableAffichage {

	/**
	 * Echanger la position de deux cartes pos�es sur le tapis. Cette m�thode prend
	 * en compte si
	 * 
	 * @param x1 Abscisse de la premiere carte.
	 * @param y1 Ordonnee de la premiere carte.
	 * @param x2 Abscisse de la deuxieme carte.
	 * @param y2 Ordonnee de la deuxieme carte.
	 * @return {@code true} si l'�change a pu etre effectuer,
	 *         {@code false} sinon.
	 */
	public abstract boolean echangerCartes(int x1, int y1, int x2, int y2);

	/**
	 * Obtenir la {@code Carte} a une position donn�e.
	 * 
	 * @param x Abscisse de la carte.
	 * @param y Ordonnee de la carte.
	 * @return {@code null} si la position donnee est invalide, ou il n'u a pas de
	 *         carte a la position sp�cifi�e , sinon une {@code Carte}.
	 */
	public abstract Carte getCarteAt(int x, int y);

	/**
	 * Poser une {@code Carte} a une certaine position. 
	 * 
	 * @param carte La carte a poser.
	 * @param x Abscisse de la carte.
	 * @param y Ordonnee de la carte.
	 * @return 
	 */
	public abstract boolean poserCarte(Carte carte, int x, int y);

}
