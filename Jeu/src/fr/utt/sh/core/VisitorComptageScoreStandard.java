package fr.utt.sh.core;

import fr.utt.sh.core.tapis.Tapis_Rectangulaire;

public class VisitorComptageScoreStandard implements VisitorComptageScore {

	Carte             carte;
	Carte.Couleur     couleur;
	Carte.Forme       forme;
	Carte.Remplissage remplissage;

	int points = 0;

	/**
	 * @param carte
	 */
	public VisitorComptageScoreStandard(Carte carte) {
		setCarte(carte);
	}

	@Override
	public void setCarte(Carte carte) {
		this.carte  = carte;
		couleur     = carte.getCouleur();
		forme       = carte.getForme();
		remplissage = carte.getRemplissage();
	}

	@Override
	public void visit(Tapis_Rectangulaire tapis) {
		int largeurTapis = tapis.getLargeur();
		int hauteurTapis = tapis.getHauteur();

		// Calcule des lignes
		for (int y = 0; y < hauteurTapis; y++) {
			Carte[] ligneCartes = new Carte[largeurTapis];
			for (int x = 0; x < largeurTapis; x++) {
				ligneCartes[x] = tapis.getCarteAt(x, y);
			}

			points += getPointsDansListe(ligneCartes);
		}

		// Calcule des colonnes
		for (int x = 0; x < largeurTapis; x++) {
			Carte[] colonneCartes = new Carte[hauteurTapis];
			for (int y = 0; y < hauteurTapis; y++) {
				colonneCartes[y] = tapis.getCarteAt(x, y);
			}

			points += getPointsDansListe(colonneCartes);
		}
	}

	/**
	 * Permet d'obtenir le nombre de points APRES avoir visiter le tapis.
	 * 
	 * @return Le nombre de points pour la carte.
	 */
	public int getPoints() {
		return points;
	}

	// Calculer les points pour une ligne / colonne donnée.
	int getPointsDansListe(Carte[] cartes) {

		int formeALaSuite       = 0;
		int couleurALaSuite     = 0;
		int remplissageALaSuite = 0;

		int maxCouleurALaSuite     = 0;
		int maxFormeALaSuite       = 0;
		int maxRemplissageALaSuite = 0;

		for (Carte carteTapis : cartes) {

			if (carteTapis == null) {
				maxCouleurALaSuite = Math.max(couleurALaSuite, maxCouleurALaSuite);
				couleurALaSuite    = 0;

				maxFormeALaSuite = Math.max(formeALaSuite, maxFormeALaSuite);
				formeALaSuite    = 0;

				maxRemplissageALaSuite = Math.max(remplissageALaSuite, maxRemplissageALaSuite);
				remplissageALaSuite    = 0;

			} else {

				if (carteTapis.getCouleur() == couleur)
					couleurALaSuite++;
				else {
					maxCouleurALaSuite = Math.max(couleurALaSuite, maxCouleurALaSuite);
					couleurALaSuite    = 0;
				}

				if (carteTapis.getForme() == forme)
					formeALaSuite++;
				else {
					maxFormeALaSuite = Math.max(formeALaSuite, maxFormeALaSuite);
					formeALaSuite    = 0;
				}

				if (carteTapis.getRemplissage() == remplissage)
					remplissageALaSuite++;
				else {
					maxRemplissageALaSuite = Math.max(remplissageALaSuite, maxRemplissageALaSuite);
					remplissageALaSuite    = 0;
				}
			}
		}
		
		maxCouleurALaSuite = Math.max(couleurALaSuite, maxCouleurALaSuite);
		maxFormeALaSuite = Math.max(formeALaSuite, maxFormeALaSuite);
		maxRemplissageALaSuite = Math.max(remplissageALaSuite, maxRemplissageALaSuite);


		int score = 0;
		if (maxFormeALaSuite > 1)
			score += maxFormeALaSuite - 1;

		if (maxRemplissageALaSuite > 2)
			score += maxRemplissageALaSuite;

		if (maxCouleurALaSuite > 3)
			score += maxCouleurALaSuite + 1;

		return score;
	}

}
