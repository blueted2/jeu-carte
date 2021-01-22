package fr.utt.sh.core.actions;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Position;

/**
 * {@link ActionJeu} indiquant qu'un joueur vient de poser une carte.
 * 
 * @author grego
 *
 */
public class PoserCarte implements ActionJeu, ActionMainJoueur, ActionTapis {

	private Carte    carte;
	private Position position;

	/**
	 * @param carte    La {@link Carte} posée.
	 * @param position La {@link Position} de la carte posée.
	 */
	public PoserCarte(Carte carte, Position position) {
		this.carte    = carte;
		this.position = position;
	}

	/**
	 * @return La {@link Carte} posée.
	 */
	public Carte getCarte() {
		return carte;
	}

	/**
	 * @return La {@link Position} de la carte posée.
	 */
	public Position getPosition() {
		return position;
	}

}
