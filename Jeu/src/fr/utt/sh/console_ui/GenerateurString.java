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

	public static String getStringTapis(Tapis tapis) {
		if (tapis == null)
			return "";

		VisitorAffichageString vis = new VisitorAffichageString();
		tapis.accept(vis);
		return vis.getRepresentationString();
	}

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
