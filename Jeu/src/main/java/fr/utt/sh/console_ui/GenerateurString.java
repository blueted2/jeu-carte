package fr.utt.sh.console_ui;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Carte.Remplissage;
import fr.utt.sh.core.Joueur;
import fr.utt.sh.core.tapis.Tapis;

/**
 * Cette classe contient des methodes pour créer des representaion string to
 * differents objets afin d'etre affichés a la console.
 * 
 * @author grego G
 */
public class GenerateurString {

	/**
	 * Obtenir une représentation string d'un tapis.
	 * 
	 * @param tapis {@link Tapis}
	 * @return String
	 */
	public static String getStringTapis(Tapis tapis) {
		if (tapis == null)
			return "";

		VisitorAffichageString vis = new VisitorAffichageString();
		tapis.accept(vis);
		return vis.getRepresentationString();
	}

	/**
	 * Obtenir une représentation string d'une carte.
	 * 
	 * @param carte {@link Carte}
	 * @return String
	 */
	public static String getStringCarte(Carte carte) {
		if (carte == null)
			return "";

		char   charCouleur = carte.getCouleur().name().charAt(0);
		String charForme   = getCharForme(carte);
		return String.format("%s%s", charCouleur, charForme);
	}

	private static String getCharForme(Carte carte) {
		if (carte.getRemplissage() == Remplissage.Rempli)
			switch (carte.getForme()) {
				case Carre:
					return "■";
				case Cercle:
					return "●";
				case Triangle:
					return "▲";
				default:
					return "?";
			}

		else
			switch (carte.getForme()) {
				case Carre:
					return "□";
				case Cercle:
					return "○";
				case Triangle:
					return "△";
				default:
					return "?";
			}

	}

	/**
	 * Obtenir une représentation string des cartes dans la main d'un Joueur. Cette
	 * représentaion tient sur deux lignes, la premiere pour l'indice de la carte,
	 * la deuxieme pour la representaion de la carte.
	 * 
	 * @param joueur {@link Joueur}
	 * @return String de deux lignes.
	 */
	public static String getStringCartesDansMainJoueur(Joueur joueur) {
		String ligneHaut = " ";
		String ligneBas  = "|";

		int i = 0;
		for (Carte carte : joueur.getCartesDansMain()) {
			ligneHaut += i + "  ";
			ligneBas  += GenerateurString.getStringCarte(carte) + "|";
			i++;
		}

		return ligneHaut + "\n" + ligneBas;
	}
}
