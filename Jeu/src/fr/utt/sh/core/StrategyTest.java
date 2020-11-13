package fr.utt.sh.core;

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
