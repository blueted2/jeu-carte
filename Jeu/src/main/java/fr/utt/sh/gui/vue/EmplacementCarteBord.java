package fr.utt.sh.gui.vue;

import java.awt.Color;

import fr.utt.sh.core.Position;

/**
 * Une variante de {@link EmplacementCarte}, utilisé pour representer les
 * positions du tapis hors du jeu, afin de permetre au jeu de se deplacer.
 * L'apparence de cet emplacement est gris, et il est caché par defaut.
 * 
 * @author grego
 *
 */
@SuppressWarnings("serial")
public class EmplacementCarteBord extends EmplacementCarte {

	/**
	 * @param position La postion qu'aurait cet emplacement sur le tapis.
	 * 
	 */
	public EmplacementCarteBord(Position position) {
		super(position);
		setVisible(false);
		setBackground(Color.LIGHT_GRAY);
	}
}
