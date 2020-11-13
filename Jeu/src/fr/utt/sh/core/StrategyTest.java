package fr.utt.sh.core;

/**
 * Une implementation test d'une strateg. Quand cette strategy est utilisée,
 * elle pioche, puis tante de poser une carte a la premiere position disponible.
 * 
 * @author grego
 *
 */
public class StrategyTest implements Strategy {

	int pos = 1;
	ControlleurJeu c = ControlleurJeu.getInstance();

	@Override
	public boolean execute(Joueur joueur) {
		joueur.piocherCarte();

		for (int y = 1; y < 4; y++) {
			for (int x = 1; x < 6; x++) {
				if (joueur.poserCarte(x, y))
					return true;
			}
		}

		return false;

	}
}
