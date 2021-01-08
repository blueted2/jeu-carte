package fr.utt.sh.core.score;

import java.util.LinkedList;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.tapis.TapisTri;
import fr.utt.sh.core.tapis.TapisRect;

/**
 * <pre>
 * Cette implementation de {@link VisitorComptageScore} compte les points pour
 * une certaine carte dans la facon decrite par le projet.
 * 
 * La carte pour laquelle le score sera calculé doit etre donné dans le
 * constructeur, et pour effectuer le comptage du score, la methode
 * {@link VisitorComptageScore#visit} doit etre appelé avec un tapis comme
 * parametre.
 * </pre>
 * 
 * @author grego
 *
 */
public class VisitorComptageScoreStandard implements VisitorComptageScore {

	Carte             carte;
	Carte.Couleur     couleur;
	Carte.Forme       forme;
	Carte.Remplissage remplissage;

	int points = 0;

	/**
	 * Ce constructeur demande de spécifier initialement la carte pour laquelle le
	 * score sera compté.
	 * 
	 * @param carte La carte
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
	public void visit(TapisRect tapis) {
		int largeurTapis = tapis.getLargeur();
		int hauteurTapis = tapis.getHauteur();

		// Calcule des lignes
		for (int y = 0; y < hauteurTapis; y++) {
			LinkedList<Carte> ligneCartes = new LinkedList<Carte>();
			for (int x = 0; x < largeurTapis; x++) {
				ligneCartes.add(tapis.getCarteAt(x, y));
			}

			points += getPointsDansListe(ligneCartes);
		}

		// Calcule des colonnes
		for (int x = 0; x < largeurTapis; x++) {
			LinkedList<Carte> colonneCartes = new LinkedList<Carte>();
			for (int y = 0; y < hauteurTapis; y++) {
				colonneCartes.add(tapis.getCarteAt(x, y));
			}

			points += getPointsDansListe(colonneCartes);
		}
	}

	@Override
	public void visit(TapisTri tapis) {
		int tailleTapis = tapis.getHauteur(); // Hauteur et largeur sont les meme pour un tapis triangulaire.

		// Calcule des lingnes
		for (int y = 0; y < tailleTapis; y++) {

			LinkedList<Carte> ligneCartes = new LinkedList<Carte>();
			for (int x = 0; x < y + 1; x++) {
				ligneCartes.add(tapis.getCarteAt(x, y));
			}
			points += getPointsDansListe(ligneCartes);

		}

		// Calcule des colonnes
		for (int x = 0; x < tailleTapis; x++) {
			LinkedList<Carte> colonneCartes = new LinkedList<Carte>();
			for (int y = x; y < tailleTapis; y++) {
				colonneCartes.add(tapis.getCarteAt(x, y));
			}

			points += getPointsDansListe(colonneCartes);
		}
	}

	public int getPoints() {
		return points;
	}

	// Calculer les points pour une ligne / colonne donnée.
	int getPointsDansListe(LinkedList<Carte> listeCartes) {

		int formeALaSuite       = 0;
		int couleurALaSuite     = 0;
		int remplissageALaSuite = 0;

		int maxCouleurALaSuite     = 0;
		int maxFormeALaSuite       = 0;
		int maxRemplissageALaSuite = 0;

		for (Carte carte : listeCartes) {

			if (carte == null) {
				maxCouleurALaSuite = Math.max(couleurALaSuite, maxCouleurALaSuite);
				couleurALaSuite    = 0;

				maxFormeALaSuite = Math.max(formeALaSuite, maxFormeALaSuite);
				formeALaSuite    = 0;

				maxRemplissageALaSuite = Math.max(remplissageALaSuite, maxRemplissageALaSuite);
				remplissageALaSuite    = 0;

			} else {

				if (carte.getCouleur() == couleur)
					couleurALaSuite++;
				else {
					maxCouleurALaSuite = Math.max(couleurALaSuite, maxCouleurALaSuite);
					couleurALaSuite    = 0;
				}

				if (carte.getForme() == forme)
					formeALaSuite++;
				else {
					maxFormeALaSuite = Math.max(formeALaSuite, maxFormeALaSuite);
					formeALaSuite    = 0;
				}

				if (carte.getRemplissage() == remplissage)
					remplissageALaSuite++;
				else {
					maxRemplissageALaSuite = Math.max(remplissageALaSuite, maxRemplissageALaSuite);
					remplissageALaSuite    = 0;
				}
			}
		}

		maxCouleurALaSuite     = Math.max(couleurALaSuite, maxCouleurALaSuite);
		maxFormeALaSuite       = Math.max(formeALaSuite, maxFormeALaSuite);
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
