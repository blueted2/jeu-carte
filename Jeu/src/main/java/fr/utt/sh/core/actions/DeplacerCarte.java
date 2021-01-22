package fr.utt.sh.core.actions;

import fr.utt.sh.core.Position;

/**
 * {@link ActionJeu} indiquant qu'une carte a été deplacée.
 * 
 * @author grego
 *
 */
public class DeplacerCarte implements ActionJeu, ActionTapis {

	private Position source;

	private Position destination;

	/**
	 * @param source      La {@link Position} d'origine de la carte déplacée.
	 * @param destination La {@link Position} d'arrivée de la carte déplacée.
	 */
	public DeplacerCarte(Position source, Position destination) {

		this.source      = source;
		this.destination = destination;
	}

	/**
	 * @return La {@link Position} d'origine de la carte déplacée.
	 */
	public Position getSource() {
		return source;
	}

	/**
	 * @return La {@link Position} d'arrivée de la carte déplacée.
	 */
	public Position getDestination() {
		return destination;
	}
}
