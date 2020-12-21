package fr.utt.sh.core.strategy;

import java.util.Observable;
import java.util.Observer;

import fr.utt.sh.console_ui.ConsoleLineReader;
import fr.utt.sh.console_ui.Utils;
import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;

/**
 * Une implementation de {@link Strategy} demandant a un joueur humain les
 * actions a efectuer avec la console.
 * 
 * @author grego
 *
 */
public class StrategyJoueurConsole implements Strategy, Observer {

	private ControlleurJeu c = ControlleurJeu.getInstance();

	private Joueur   joueurActuel;
	private String[] commande;

	@Override
	public void run() {
		try {
			execute();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void execute() throws InterruptedException {
		joueurActuel = c.getJoueurActuel();
		ConsoleLineReader.getInstance().addObserver(this);
	}

	private void executeStandard() {

//		String message = "Actions possibles: ";

//		message += !c.hasJoueurActuelPiocheCarteCeTour() ? "piocher | " : "p̶i̶o̶c̶h̶e̶r̶ | ";
//		message += !c.hasJoueurActuelPoseCarteCeTour() && c.hasJoueurActuelPiocheCarteCeTour() ? "poser {x} {y} | "
//				: "p̶o̶s̶e̶r̶ ̶{̶x̶}̶ ̶{̶y̶}̶ | ";
//		message += !c.hasJoueurActuelDeplaceCarteCeTour() ? "deplacer {x1} {y1} {x2} {y2} | "
//				: "d̶e̶p̶l̶a̶c̶e̶r̶ ̶{̶x̶1̶}̶ ̶{̶y̶1̶}̶ ̶{̶x̶2̶}̶ ̶{̶y̶2̶}̶ | ";
//		message += c.hasJoueurActuelPoseCarteCeTour() ? "finir" : "f̶i̶n̶i̶r̶";
//
//		System.out.println("Actions possibles: piocher | poser {x} {y} | deplacer {x1} {y1} {x2} {y2} | finir");
//
//		System.out.print("Action: ");
//
//		
//		
//		

		if (commande == null)
			return;

		switch (commande[0]) {
			case "piocher":
				if (!c.joueurActuelPiocheCarte())
					System.out.println("Erreur: Pas pu piocher carte!");

				return;

			case "poser":
				try {
					int x = Integer.parseInt(commande[1]);
					int y = Integer.parseInt(commande[2]);

					if (!c.joueurActuelPoseCartePiochee(x, y))
						System.out.println("Erreur: Pas pu poser carte!");
				} catch (Exception e) {
					System.out.println("Erreur: Coordonées incorectes!");
				}

				return;

			case "deplacer":
				try {
					int x1 = Integer.parseInt(commande[1]);
					int y1 = Integer.parseInt(commande[2]);
					int x2 = Integer.parseInt(commande[3]);
					int y2 = Integer.parseInt(commande[4]);

					if (!c.joueurActuelDeplaceCarte(x1, y1, x2, y2))
						System.out.println("Erreur: Pas pu déplacer carte!");

				} catch (Exception e) {
					System.out.println("Erreur: Coordonées incorectes!");
				}
				return;

			case "finir":
				if (c.terminerTourJoueurActuel()) {
					arreter();
					return;
				}

				System.out.println("Erreur: Joueur ne peut pas finir a ce moment!");
				return;

			default:
				System.out.println("Erreur: Commande incorrecte!");
				return;
		}
	}

	private void executeAdvanced() {
//		System.out.println("Actions possibles: piocher | poser {i} {x} {y} | deplacer {x} {y} | finir");
//		System.out.print("Action: ");

		if (commande == null)
			return;

		switch (commande[0]) {
			case "piocher":
				if (!c.joueurActuelPiocheCarte())
					System.out.println("Erreur: Pas pu piocher carte!");

				return;

			case "poser":
				try {
					int i = Integer.parseInt(commande[1]);
					int x = Integer.parseInt(commande[2]);
					int y = Integer.parseInt(commande[3]);

					Carte carte = joueurActuel.getCarteDansMain(i);

					if (!c.joueurActuelPoseCarteDansMain(carte, x, y))
						System.out.println("Erreur: Pas pu poser carte!");

				} catch (Exception e) {
					System.out.println("Erreur: Index ou Coordonées incorectes!");
				}

				return;

			case "deplacer":

				int x1, x2, y1, y2;
				try {
					x1 = Integer.parseInt(commande[1]);
					y1 = Integer.parseInt(commande[2]);
					x2 = Integer.parseInt(commande[3]);
					y2 = Integer.parseInt(commande[4]);

				} catch (Exception e) {
					System.out.println(e);
					System.out.println("Erreur: Coordonées incorectes!");
					return;
				}

				if (!c.joueurActuelDeplaceCarte(x1, y1, x2, y2))
					System.out.println("Erreur: Pas pu déplacer carte!");

				return;

			case "finir":
				if (c.terminerTourJoueurActuel()) {
					arreter();
					return;
				}

				System.out.println("Erreur: Joueur ne peut pas finir a ce moment!");
				return;

			default:
				System.out.println("Erreur: Commande incorrecte!");
				return;
		}
	}

	
	public void arreter() {
		ConsoleLineReader.getInstance().deleteObserver(this);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if (!(arg0 instanceof ConsoleLineReader))
			return;

		commande = ConsoleLineReader.getLigneConsole();
		switch (c.getRegles()) {
			case Standard:
				executeStandard();
			case Advanced:
				executeAdvanced();
			default:
				break;
		}

	}

}
