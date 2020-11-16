package fr.utt.sh.core.strategy;

import fr.utt.sh.console_ui.Utils;
import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;

public class StrategyJoueurConsole implements Strategy {

	ControlleurJeu cj = ControlleurJeu.getInstance();

	@Override
	public boolean execute(Joueur joueur) {

		Carte carteTest = new Carte(Carte.Couleur.Rouge, Carte.Remplissage.Rempli, Carte.Forme.Carre);
		System.out.println(String.format("Score pour (%s): %d", carteTest, cj.getScorePourCarte(carteTest)));

		System.out.println("Actions possibles: piocher | poser {x} {y} | deplacer {x} {y} | finir");
		System.out.print("Action: ");
		String[] mots = Utils.getLigneSeparee();
		if (mots == null)
			return false;

		switch (mots[0]) {
			case "piocher":
				if (!joueur.piocherCarte())
					System.out.println("Erreur: Pas pu piocher carte!");

				return false;

			case "poser":
				try {
					int x = Integer.parseInt(mots[1]);
					int y = Integer.parseInt(mots[2]);

					if (!joueur.poserCarte(x, y))
						System.out.println("Erreur: Pas pu poser carte!");
				} catch (Exception e) {
					System.out.println("Erreur: Coordonées incorectes!");
				}

				return false;

			case "deplacer":
				try {
					int x1 = Integer.parseInt(mots[1]);
					int y1 = Integer.parseInt(mots[2]);
					int x2 = Integer.parseInt(mots[3]);
					int y2 = Integer.parseInt(mots[4]);

					if (!joueur.deplacerCarte(x1, y1, x2, y2))
						System.out.println("Erreur: Pas pu déplacer carte!");

				} catch (Exception e) {
					System.out.println("Erreur: Coordonées incorectes!");
				}
				return false;

			case "finir":
				if (cj.joueurActuelPeutFinir()) {
					return true;
				}

				System.out.println("Erreur: Joueur ne peut pas finir a ce moment!");
				return false;

			default:
				System.out.println("Erreur: Commande incorrecte!");
				return false;
		}
	}
}
