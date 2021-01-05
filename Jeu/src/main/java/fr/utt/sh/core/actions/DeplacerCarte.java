package fr.utt.sh.core.actions;

import fr.utt.sh.core.Joueur;
import fr.utt.sh.core.Position;

public class DeplacerCarte implements ActionJeu , ActionTapis {
	
	private Position source;
	

	private Position destination;
	
	public DeplacerCarte(Position source, Position destination) {

		this.source = source;
		this.destination = destination;
	}
	public Position getSource() {
		return source;
	}

	public Position getDestination() {
		return destination;
	}
}
