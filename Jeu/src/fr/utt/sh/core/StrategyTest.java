package fr.utt.sh.core;

public class StrategyTest implements Strategy {

	int pos = 1;
	ControlleurJeu c = ControlleurJeu.getInstance();

	@Override
	public boolean execute(Joueur joueur) {
		joueur.piocherCarte();

		if (!joueur.poserCarte(1, 1)) {
			joueur.poserCarte(1, 2);
		}
		return true;

	}
}
