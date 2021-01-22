package fr.utt.sh.console_ui;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.tapis.TapisTri;
import fr.utt.sh.core.tapis.decalable.TapisRectDecalable;
import fr.utt.sh.core.tapis.TapisRect;

/**
 * Cette classe s'occupe de créer une représentation string de tous les élements
 * du jeu.
 * 
 * @author grego
 *
 */
public class VisitorAffichageString implements VisitorAffichage {

	private String representationString;

	/**
	 * Crée la représentation string d'un tapis rectangulaire.
	 */
	public void visit(TapisRect tapis) {

		representationString = "";

		int largeurTapis = tapis.getLargeur();
		int hauteurTapis = tapis.getHauteur();

		String ligneNombres;
		String ligneHaut;
		String ligneSeparateur;
		String ligneBas;

		ligneNombres = "   0  ";
		ligneHaut = "  ┌──";
		ligneSeparateur = "  ├──";
		ligneBas = "  └──";

		for (int x = 1; x < largeurTapis; x++) {
			ligneNombres += Integer.toString(x) + "  ";
			ligneHaut += "┬──";
			ligneSeparateur += "┼──";
			ligneBas += "┴──";
		}

		ligneNombres += "\n";
		ligneHaut += "┐ \n";
		ligneSeparateur += "┤ \n";
		ligneBas += "┘ \n";

		representationString += ligneNombres;
		representationString += ligneHaut;

		String ligne;
		for (int y = 0; y < hauteurTapis; y++) {
			ligne = Integer.toString(y) + " │";

			for (int x = 0; x < largeurTapis; x++) {
				String strCarte;

				if (!tapis.positionLegale(x, y))
					strCarte = "><";
				else {
					Carte carte = tapis.getCarteAt(x, y);
					if (carte == null) {
						strCarte = "  ";
					} else {
						strCarte = GenerateurString.getStringCarte(carte);
					}
				}

				ligne += strCarte + "│";
			}
			ligne += "\n";

			representationString += ligne;

			if (y != hauteurTapis - 1) // Si on n'est pas a la derniere ligne
				representationString += ligneSeparateur;
			else
				representationString += ligneBas;

		}
	}

	/**
	 * Crée la représentation string d'un tapis triangulaire.
	 */
	public void visit(TapisTri tapis) {
		representationString = "";

		int largeurTapis = tapis.getLargeur();
		int hauteurTapis = tapis.getHauteur();

		String ligneNombres = "   0  ";
		String ligneHaut = "  ┌──┐ \n";
		String ligneSeparateur = "  ├──┼──";
		String ligneBas = "  └──";

		for (int x = 1; x < largeurTapis; x++) {
			ligneNombres += Integer.toString(x) + "  ";
			ligneBas += "┴──";
		}
		ligneNombres += "\n";
		ligneBas += "┘ \n";

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
				ligneSeparateur += "┼──";

			} else {
				representationString += ligneBas;
			}
		}

	}

	@Override
	public void visit(TapisRectDecalable tapis) {
		visit((TapisRect) tapis);
	}

	/**
	 * @return La représentation string d'un {@link VisitableAffichage}.
	 */
	public String getRepresentationString() {
		return representationString;
	}

}
