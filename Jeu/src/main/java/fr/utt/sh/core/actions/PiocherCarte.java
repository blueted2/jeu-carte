package fr.utt.sh.core.actions;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Joueur;

/**
 * {@link ActionJeu} indiquant qu'un joueur vient de piocher une carte.
 * 
 * @author grego
 *
 */
public class PiocherCarte implements ActionJeu, ActionMainJoueur {

	private Joueur joueur;

	private Carte carte;

	/**
	 * @param joueur Le {@link Joueur} piochant la carte.
	 * @param carte  La {@link Carte} piochée.
	 */
	public PiocherCarte(Joueur joueur, Carte carte) {
		this.joueur = joueur;
		this.carte  = carte;
	}

	/**
	 * @return Le {@link Joueur} piochant la carte.
	 */
	public Joueur getJoueur() {
		return joueur;
	}

	/**
	 * @return La {@link Carte} piochée.
	 */
	public Carte getCarte() {
		return carte;
	}

}
