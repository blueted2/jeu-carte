package fr.utt.sh.core.actions;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Joueur;

public class PiocherCarte implements ActionJeu, ActionMainJoueur {
	
	private Joueur joueur;
	

	private Carte carte;
	
	public PiocherCarte(Joueur joueur, Carte carte) {
		this.joueur = joueur;
		this.carte = carte;
	}
	
	public Joueur getJoueur() {
		return joueur;
	}

	public Carte getCarte() {
		return carte;
	}
	
}
