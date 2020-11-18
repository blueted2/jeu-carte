package fr.utt.sh.core.strategy;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;
import fr.utt.sh.core.score.VisitorComptageScore;
import fr.utt.sh.core.score.VisitorComptageScoreStandard;
import fr.utt.sh.core.tapis.Tapis_Rectangulaire;

/**
 * Une implementation test d'une strateg. Quand cette strategy est utilisée,
 * elle pioche, puis tante de poser une carte a la premiere position disponible.
 * 
 * @author grego
 *
 */
public class StrategyTest implements Strategy {

	ControlleurJeu c = ControlleurJeu.getInstance();

	@Override
	public boolean execute(Joueur joueur) {
		Tapis_Rectangulaire tapisTemp = (Tapis_Rectangulaire)c.getTapis();
		int lTapis = tapisTemp.getLargeur();
		int hTapis = tapisTemp.getHauteur();
		
		joueur.piocherCarte();

		int posXMax = -2; // Position invalide, pour tester si la valeur a été changée.
		int posYMax = -2;
		int scoreMax = -1;
		
		for (int y = 0; y < hTapis; y++) {
			for (int x = 0; x < lTapis; x++) {
				
				Carte cartePiochee = joueur.getCartePiochee();
				if(tapisTemp.poserCarte(cartePiochee, x, y)) {		
					
					VisitorComptageScore v = new VisitorComptageScoreStandard(cartePiochee);
					v.visit(tapisTemp);
					int points = v.getPoints();
					
					if(points > scoreMax) {
						scoreMax = points;
						posXMax = x;
						posYMax = y;
					}
					
					tapisTemp.retirerCarte(x, y);
				}
			}
		}
		
		joueur.poserCarte(posXMax, posYMax);
		return true;
	}
}
