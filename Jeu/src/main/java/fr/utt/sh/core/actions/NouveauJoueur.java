package fr.utt.sh.core.actions;

import fr.utt.sh.core.Joueur;

public class NouveauJoueur {
	
	private Joueur joueur;
	
	public NouveauJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public Joueur getJoueur() {
		return joueur;
	}
}
