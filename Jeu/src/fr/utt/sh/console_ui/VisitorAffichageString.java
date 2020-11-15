package fr.utt.sh.console_ui;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Carte.Remplissage;
import fr.utt.sh.core.tapis.Tapis_Rectangulaire;

/**
 * @author grego
 *
 */
public class VisitorAffichageString implements VisitorAffichage {

	private String representationString;

	public void visit(Tapis_Rectangulaire tapis) {
		
		representationString = "";
		
		int l = tapis.getLargeur();
		int h = tapis.getHauteur();
				
		String ligneNombres;
		String ligneHaut;
		String ligneSeparateur;
		String ligneBas;
		
		ligneNombres    = "   0  "  ;
		ligneHaut       = "  ┌──";
		ligneSeparateur = "  ├──";
		ligneBas        = "  └──";
		
		for (int x=1; x<l; x++) {
			ligneNombres += Integer.toString(x) + "  ";
			ligneHaut       += "┬──";
			ligneSeparateur += "┼──";
			ligneBas        += "┴──";
			
		}
		ligneNombres    += "\n";
		ligneHaut       += "┐ \n";
		ligneSeparateur += "┤ \n";
		ligneBas        += "┘ \n";
		
		
		representationString += ligneNombres;
		representationString += ligneHaut;
		
		
		
		String ligne;
		for (int y = 0; y < h; y++) {
			
			String yStr = Integer.toString(y);
			ligne = yStr + " │";
			
			for (int x = 0; x < l; x++) {
				Carte carte = tapis.getCarteAt(x, y);

				String strCarte;
				if (carte == null) {
					strCarte = "  ";
				} else {
					strCarte = getRepresentationString(carte);
				}

				ligne += strCarte + "│";
			}
			ligne += "\n";
			representationString += ligne;
			
			
			if(y != h-1)
				representationString += ligneSeparateur;
			else
				representationString += ligneBas;
			
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
