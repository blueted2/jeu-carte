package fr.utt.sh.core;

public class StrategyTest implements Strategy {

	@Override
	public boolean execute(Joueur joueur) {
		return ControlleurJeu.getInstance().poserCarte(joueur, new Carte(Carte.Couleur.Rouge, Carte.Remplissage.Rempli), 1, 1);
		
	}
}
