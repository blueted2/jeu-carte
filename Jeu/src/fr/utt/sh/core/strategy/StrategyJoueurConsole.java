package fr.utt.sh.core.strategy;

import fr.utt.sh.console_ui.Utils;
import fr.utt.sh.console_ui.VisitorAffichageString;
import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;

public class StrategyJoueurConsole implements Strategy {

	private ControlleurJeu c = ControlleurJeu.getInstance();

	private Joueur joueurActuel;

	@Override
	public boolean execute() {

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

		String message = "Actions possibles: ";

		message += !c.joueurActuelAPiocheCarteCeTour() ? "piocher | " : "p̶i̶o̶c̶h̶e̶r̶ | ";
		message += !c.joueurActuelAPoseCarteCeTour() && c.joueurActuelAPiocheCarteCeTour() ? "poser {x} {y} | "
				: "p̶o̶s̶e̶r̶ ̶{̶x̶}̶ ̶{̶y̶}̶ | ";
		message += !c.joueurActuelADeplaceCarteCeTour() ? "deplacer {x1} {y1} {x2} {y2} | "
				: "d̶e̶p̶l̶a̶c̶e̶r̶ ̶{̶x̶1̶}̶ ̶{̶y̶1̶}̶ ̶{̶x̶2̶}̶ ̶{̶y̶2̶}̶ | ";
		message += c.joueurActuelAPoseCarteCeTour() ? "finir" : "f̶i̶n̶i̶r̶";

//		System.out.println("Actions possibles: piocher | poser {x} {y} | deplacer {x1} {y1} {x2} {y2} | finir");
		System.out.println(message);
		System.out.print("Action: ");

		String[] mots = Utils.getLigneSeparee();
		if (mots == null)
			return false;

		switch (mots[0]) {
			case "piocher":
				if (!c.joueurActuelPiocheCarte())
					System.out.println("Erreur: Pas pu piocher carte!");

				return false;

			case "poser":
				try {
					int x = Integer.parseInt(mots[1]);
					int y = Integer.parseInt(mots[2]);

					if (!c.joueurActuelPoseCartePiochee(x, y))
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

					if (!c.joueurActuelDeplaceCarte(x1, y1, x2, y2))
						System.out.println("Erreur: Pas pu déplacer carte!");

				} catch (Exception e) {
					System.out.println("Erreur: Coordonées incorectes!");
				}
				return false;

			case "finir":
				if (c.joueurActuelPeutFinir()) {
					return true;
				}

				System.out.println("Erreur: Joueur ne peut pas finir a ce moment!");
				return false;

			default:
				System.out.println("Erreur: Commande incorrecte!");
				return false;
		}
	}

	private boolean executeAdvanced() {
		System.out.println("Actions possibles: piocher | poser {i} {x} {y} | deplacer {x} {y} | finir");
		System.out.print("Action: ");
		String[] mots = Utils.getLigneSeparee();
		if (mots == null)
			return false;

		switch (mots[0]) {
			case "piocher":
				if (!c.joueurActuelPiocheCarte())
					System.out.println("Erreur: Pas pu piocher carte!");

				return false;

			case "poser":
				try {
					int i = Integer.parseInt(mots[1]);
					int x = Integer.parseInt(mots[2]);
					int y = Integer.parseInt(mots[3]);

					Carte carte = joueurActuel.getCarteDansMain(i);

					if (!c.joueurActuelPoseCarteDansMain(carte, x, y))
						System.out.println("Erreur: Pas pu poser carte!");

				} catch (Exception e) {
					System.out.println("Erreur: Index ou Coordonées incorectes!");
				}

				return false;

			case "deplacer":

				int x1, x2, y1, y2;
				try {
					x1 = Integer.parseInt(mots[1]);
					y1 = Integer.parseInt(mots[2]);
					x2 = Integer.parseInt(mots[3]);
					y2 = Integer.parseInt(mots[4]);

				} catch (Exception e) {
					System.out.println(e);
					System.out.println("Erreur: Coordonées incorectes!");
					return false;
				}

				if (!c.joueurActuelDeplaceCarte(x1, y1, x2, y2))
					System.out.println("Erreur: Pas pu déplacer carte!");

				return false;

			case "finir":
				if (c.joueurActuelPeutFinir()) {
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
