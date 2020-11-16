package fr.utt.sh.core.strategy;

import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;
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
		Tapis_Rectangulaire tapis = (Tapis_Rectangulaire)c.getTapis();
		int lTapis = tapis.getLargeur();
		int hTapis = tapis.getHauteur();
		
		joueur.piocherCarte();

		for (int y = 0; y < hTapis; y++) {
			for (int x = -1; x < lTapis; x++) {
				if (joueur.poserCarte(x, y))
					return true;
			}
		}

		return false;

	}
}
