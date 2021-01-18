package fr.utt.sh.core.actions;

import fr.utt.sh.core.Joueur;

/**
 * {@link ActionJeu} indiquant qu'un nouveau {@link Joueur} commence son tour.
 * 
 * @author grego
 *
 */
public class NouveauJoueur implements ActionJeu {

	private Joueur joueur;

	/**
	 * @param joueur Le {@link Joueur} commenceant son tour.
	 */
	public NouveauJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	/**
	 * @return Le {@link Joueur} commenceant son tour.
	 */
	public Joueur getJoueur() {
		return joueur;
	}
}
