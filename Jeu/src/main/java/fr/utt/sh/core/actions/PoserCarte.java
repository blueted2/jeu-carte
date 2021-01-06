package fr.utt.sh.core.actions;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Joueur;
import fr.utt.sh.core.Position;

public class PoserCarte implements ActionJeu, ActionMainJoueur, ActionTapis {

	private Carte    carte;
	private Position position;

	public PoserCarte(Carte carte, Position position) {
		this.carte    = carte;
		this.position = position;
	}

	public Carte getCarte() {
		return carte;
	}

	public Position getPosition() {
		return position;
	}

}
