package fr.utt.sh.core;

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
		joueur.piocherCarte();

		for (int y = 0; y < 3; y++) {
			for (int x = -1; x < 5; x++) {
				if (joueur.poserCarte(x, y))
					return true;
			}
		}

		return false;

	}
}
