package fr.utt.sh.core.strategy;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;
import fr.utt.sh.core.Position;
import fr.utt.sh.core.Regles;
import fr.utt.sh.core.score.VisitorComptageScore;
import fr.utt.sh.core.score.VisitorComptageScoreStandard;
import fr.utt.sh.core.tapis.Tapis;
import fr.utt.sh.core.tapis.Tapis_Rectangulaire;

/**
 * Une implementation test d'une strategy. Quand cette strategy est utilisée,
 * elle pioche, puis teste chaque position du tapis afin de trouver celle qui
 * donnera le meilleur score. <br>
 * Cette strategie ne posera pas la carte a une position qui necessitera un
 * deplacement.
 * 
 * @author grego
 *
 */
public class StrategyTest implements Strategy {

	ControlleurJeu c = ControlleurJeu.getInstance();

	Tapis tapisTemp;
	int   lTapis;
	int   hTapis;

	Joueur joueurActuel;

	@Override
	public boolean execute() {

		tapisTemp = c.getTapis();
		lTapis    = tapisTemp.getLargeur();
		hTapis    = tapisTemp.getHauteur();

		joueurActuel = c.getJoueurActuel();

		switch (c.getRegles()) {
			case Standard:
				return executeStandard();
			case Advanced:
				return executeAdvanced();
			case Autre:
				break;
			default:
				break;
		}
		return false;
	}

	private boolean executeStandard() {
		c.joueurActuelPiocheCarte();

		Carte carteVictoire = joueurActuel.getCarteVictoire();

		Carte cartePiochee = joueurActuel.getCartePiochee();

		Position meilleurePosition = getMeilleurePosition(carteVictoire, cartePiochee);

		int x = meilleurePosition.getX();
		int y = meilleurePosition.getY();

		c.joueurActuelPoseCartePiochee(x, y);
		return true;
	}

	private boolean executeAdvanced() {
		Carte carteVictoire = joueurActuel.getCarteDansMain(0);
		Carte carteAPoser   = joueurActuel.getCarteDansMain(1);

		Position meilleurePosition = getMeilleurePosition(carteVictoire, carteAPoser);

		int x = meilleurePosition.getX();
		int y = meilleurePosition.getY();

		c.joueurActuelPoseCarteDansMain(carteAPoser, x, y);
		c.joueurActuelPiocheCarte();
		
		return true;
	}

	private Position getMeilleurePosition(Carte carteVictoire, Carte carteAPoser) {
		int posXMax  = -2; // Position invalide, pour tester si la valeur a été changée.
		int posYMax  = -2;
		int scoreMax = -1;

		// Essayer tous les coordonnées possibles pour trouver une position jouable, et
		// qui donnerait le meilleure score.
		for (int y = 0; y < hTapis; y++) {
			for (int x = 0; x < lTapis; x++) {

				if (tapisTemp.positionJouable(x, y)) {
					if (tapisTemp.poserCarte(carteAPoser, x, y)) {

						VisitorComptageScore v = new VisitorComptageScoreStandard(carteVictoire);
						tapisTemp.accept(v);
						int points = v.getPoints();

						if (points > scoreMax) {
							scoreMax = points;
							posXMax  = x;
							posYMax  = y;
						}
						tapisTemp.retirerCarte(x, y);
					}
				}
			}
		}
		return new Position(posXMax, posYMax);
	}

}
