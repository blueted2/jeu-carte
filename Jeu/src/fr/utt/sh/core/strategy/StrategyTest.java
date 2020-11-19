package fr.utt.sh.core.strategy;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;
import fr.utt.sh.core.score.VisitorComptageScore;
import fr.utt.sh.core.score.VisitorComptageScoreStandard;
import fr.utt.sh.core.tapis.Tapis;
import fr.utt.sh.core.tapis.Tapis_Rectangulaire;

/**
 * Une implementation test d'une strategy. Quand cette strategy est utilisée,
 * elle pioche, puis teste chaque position du tapis afin de trouver celle qui donnera le meilleur score. 
 * <br>
 * Cette strategie ne posera pas la carte a une position qui necessitera un deplacement. 
 * 
 * @author grego
 *
 */
public class StrategyTest implements Strategy {

	ControlleurJeu c = ControlleurJeu.getInstance();

	@Override
	public boolean execute(Joueur joueur) {
		Tapis tapisTemp = c.getTapis();
		int   lTapis    = tapisTemp.getLargeur();
		int   hTapis    = tapisTemp.getHauteur();

		joueur.piocherCarte();

		int posXMax  = -2; // Position invalide, pour tester si la valeur a été changée.
		int posYMax  = -2;
		int scoreMax = -1;

		for (int y = 0; y < hTapis; y++) {
			for (int x = 0; x < lTapis; x++) {

				if (tapisTemp.positionJouable(x, y)) {
					Carte cartePiochee = joueur.getCartePiochee();
					if (tapisTemp.poserCarte(cartePiochee, x, y)) {

						VisitorComptageScore v = new VisitorComptageScoreStandard(cartePiochee);
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

		joueur.poserCarte(posXMax, posYMax);
		return true;
	}
}
