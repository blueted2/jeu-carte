package fr.utt.sh.console_ui;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Carte.Remplissage;
import fr.utt.sh.core.Joueur;
import fr.utt.sh.core.tapis.Tapis_Rectangulaire;
import fr.utt.sh.core.tapis.Tapis_Triangulaire;

/**
 * Cette classe s'occupe de céer une représentaion string de tout les elements
 * du jeu.
 * 
 * @author grego
 *
 */
public class VisitorAffichageString implements VisitorAffichage {

	private String representationString;

	public void visit(Tapis_Rectangulaire tapis) {

		representationString = "";

		int largeurTapis = tapis.getLargeur();
		int hauteurTapis = tapis.getHauteur();

		String ligneNombres;
		String ligneHaut;
		String ligneSeparateur;
		String ligneBas;

		ligneNombres    = "   0  ";
		ligneHaut       = "  ┌──";
		ligneSeparateur = "  ├──";
		ligneBas        = "  └──";

		for (int x = 1; x < largeurTapis; x++) {
			ligneNombres    += Integer.toString(x) + "  ";
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
		for (int y = 0; y < hauteurTapis; y++) {
			ligne = Integer.toString(y) + " │";

			for (int x = 0; x < largeurTapis; x++) {
				Carte carte = tapis.getCarteAt(x, y);

				String strCarte;
				if (carte == null) {
					strCarte = "  ";
				} else {
					strCarte = GenerateurString.getStringCarte(carte);
				}

				ligne += strCarte + "│";
			}
			ligne += "\n";

			representationString += ligne;

			if (y != hauteurTapis - 1) // Sinon on est pas a la derniere ligne
				representationString += ligneSeparateur;
			else
				representationString += ligneBas;

		}
	}

	public void visit(Tapis_Triangulaire tapis) {
		representationString = "";

		int largeurTapis = tapis.getLargeur();
		int hauteurTapis = tapis.getHauteur();

		String ligneNombres    = "   0  ";
		String ligneHaut       = "  ┌──┐ \n";
		String ligneSeparateur = "  ├──┼──";
		String ligneBas        = "  └──";

		for (int x = 1; x < largeurTapis; x++) {
			ligneNombres += Integer.toString(x) + "  ";
			ligneBas     += "┴──";
		}
		ligneNombres += "\n";
		ligneBas     += "┘ \n";

		representationString += ligneNombres;
		representationString += ligneHaut;

		String ligne;
		for (int y = 0; y < hauteurTapis; y++) {
			ligne = Integer.toString(y) + " │";

			for (int x = 0; x < y + 1; x++) {
				Carte carte = tapis.getCarteAt(x, y);

				String strCarte;
				if (carte == null) {
					strCarte = "  ";
				} else {
					strCarte = GenerateurString.getStringCarte(carte);
				}

				ligne += strCarte + "│";
			}
			ligne += "\n";

			representationString += ligne;

			// Si on on est pas a la derniere ligne
			if (y != hauteurTapis - 1) {
				representationString += ligneSeparateur + "┐ \n";
				ligneSeparateur      += "┼──";

			} else {
				representationString += ligneBas;
			}
		}

	}


	
	/**
	 * Raccourcis pour:
	 * 
	 * <pre>
	 * {
	 * 	&#64;code
	 * 	VisitorAffichageString visitor = new VisitorAffichageString();
	 * 	visitable.accept(visitor);
	 * 	return visitor.getRepresentationString();
	 * }
	 * </pre>
	 * 
	 * @param visitable Un object implementant {@link VisitableAffichage}.
	 * @return Une representaion {@code String} du {@link VisitableAffichage} donné.
	 */
//	public static String getRepresentationStringStatic(VisitableAffichage visitable) {
//		if (visitable == null)
//			return "";
//
//		VisitorAffichageString vis = new VisitorAffichageString();
//		visitable.accept(vis);
//		return vis.representationString;
//	}

	/**
	 * @return La representation string d'un {@link VisitableAffichage}.
	 */
	public String getRepresentationString() {
		return representationString;
	}

	
}
