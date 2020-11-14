package fr.utt.sh.console_ui;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Carte.Remplissage;
import fr.utt.sh.core.tapis.Tapis_5x3;

/**
 * @author grego
 *
 */
public class VisitorAffichageString implements VisitorAffichage {

	private String representationString;

	public void visit(Tapis_5x3 tapis) {
		representationString =  "   0  1  2  3  4 \n";
		representationString += "  ┌──┬──┬──┬──┬──┐\n";
		
		for (int y = 0; y < 3; y++) {
			
			String yStr = Integer.toString(y);
			representationString += yStr + " │";
			for (int x = 0; x < 5; x++) {
				Carte carte = tapis.getCarteAt(x, y);

				String strCarte;
				if (carte == null) {
					strCarte = "  ";
				} else {
					strCarte = getRepresentationString(carte);
				}

				representationString += strCarte + "│";
			}
			if(y==2) {
				representationString += "\n";
				representationString += "  └──┴──┴──┴──┴──┘\n";
			}
			else {
				representationString += "\n";
				representationString += "  ├──┼──┼──┼──┼──┤\n";
			}
			
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
					charForme = "●";
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
	 * @param visitable Un object implementant {@link VisitableAffichage}.
	 * @return Une representaion {@code String} du {@link VisitableAffichage} donné.
	 * @see VisitableAffichage
	 */
	public static String getRepresentationString(VisitableAffichage visitable) {
		VisitorAffichageString vis = new VisitorAffichageString();
		visitable.accept(vis);
		return vis.representationString;
	}

}
