package fr.utt.sh.console_ui;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Tapis_5x3;
import fr.utt.sh.core.Carte.Remplissage;

/**
 * @author grego
 *
 */
public class VisitorAffichageString implements VisitorAffichage {

	private String representationString;

	public void visit(Tapis_5x3 tapis) {
		representationString = "";
		representationString += "----------------------\n";
		for (int y = 0; y < 5; y++) {
			representationString += "|";
			for (int x = 0; x < 7; x++) {
				Carte carte = tapis.getCarteAt(x, y);

				String strCarte;
				if (carte == null) {
					strCarte = "  ";
				} else {
					strCarte = getRepresentationString(carte);
				}

				representationString += strCarte + "|";
			}
			representationString += "\n" + "----------------------\n";
		}
	}

	public void visit(Carte carte) {
		char charCouleur = carte.getCouleur().name().charAt(0);
//		char charRemlissage = (carte.getRemplissage() == Carte.Remplissage.Vide) ? ' ' : 'X';

		String charForme = "?";
		if (carte.getRemplissage() == Remplissage.Rempli) {
			switch (carte.getForme()) {
				case Carre:
					charForme = "■";
					break;
				case Cercle:
					charForme = "▲";
					break;
				case Triangle:
					charForme = "o";
					break;
				default:
					break;

			}
		} else {
			switch (carte.getForme()) {
				case Carre:
					charForme = "□";
					break;
				case Cercle:
					charForme = "△";
					break;
				case Triangle:
					charForme = "○";
					break;
				default:
					break;
			}
		}

		representationString = String.format("%s%s", charCouleur, charForme);
	}

	/**
	 * @param visitable
	 * @return Une representaion {@code String} du {@code VisitableAffichage} donné. 
	 */
	public static String getRepresentationString(VisitableAffichage visitable) {
		VisitorAffichageString vis = new VisitorAffichageString();
		visitable.accept(vis);
		return vis.representationString;
	}

}
