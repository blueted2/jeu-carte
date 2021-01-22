package fr.utt.sh.core.strategy;

import java.util.Observable;
import java.util.Observer;

import fr.utt.sh.console_ui.ConsoleLineReader;
import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;
import fr.utt.sh.core.Regles;

/**
 * Une implémentation de {@link Strategy} demandant a un joueur humain les
 * actions à effectuer avec la console.
 * 
 * @author grego
 *
 */
public class StrategyJoueurConsole implements Strategy, Observer {

	private ControlleurJeu cj = ControlleurJeu.getInstance();

	private Joueur   joueurActuel;
	private String[] commande;

	@Override
	public void run() {
		try {
			execute();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute() throws InterruptedException {
		joueurActuel = cj.getJoueurActuel();
		ConsoleLineReader.getInstance().addObserver(this);
	}

	/**
	 * Fait jouer un joueur humain, selon les {@link Regles} Standard.
	 */
	private void executeStandard() {

		if (commande == null)
			return;

		switch (commande[0]) {
			case "piocher":
				if (!cj.joueurActuelPiocheCarte())
					System.out.println("Erreur: Pas pu piocher carte!");

				return;

			case "poser":
				try {
					int x = Integer.parseInt(commande[1]);
					int y = Integer.parseInt(commande[2]);

					if (!cj.joueurActuelPoseCartePiochee(x, y))
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

					if (!cj.joueurActuelDeplaceCarte(x1, y1, x2, y2))
						System.out.println("Erreur: Pas pu déplacer carte!");

				} catch (Exception e) {
					System.out.println("Erreur: Coordonées incorectes!");
				}
				return;

			case "finir":
				if (cj.terminerTourJoueurActuel()) {
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

	/**
	 * Fait jouer un joueur humain, selon les {@link Regles} Advanced.
	 */
	private void executeAdvanced() {

		if (commande == null)
			return;

		switch (commande[0]) {
			case "piocher":
				if (!cj.joueurActuelPiocheCarte())
					System.out.println("Erreur: Pas pu piocher carte!");

				return;

			case "poser":
				try {
					int i = Integer.parseInt(commande[1]);
					int x = Integer.parseInt(commande[2]);
					int y = Integer.parseInt(commande[3]);

					Carte carte = joueurActuel.getCarteDansMain(i);

					if (!cj.joueurActuelPoseCarteDansMain(carte, x, y))
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

				if (!cj.joueurActuelDeplaceCarte(x1, y1, x2, y2))
					System.out.println("Erreur: Pas pu déplacer carte!");

				return;

			case "finir":
				if (cj.terminerTourJoueurActuel()) {
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

	/**
	 * Fait jouer un joueur humain, selon les {@link Regles} Variante.
	 */
	private void executeVariante() {

		if (commande == null)
			return;

		switch (commande[0]) {
			case "poser":
				try {
					int i = Integer.parseInt(commande[1]);
					int x = Integer.parseInt(commande[2]);
					int y = Integer.parseInt(commande[3]);

					Carte carte = joueurActuel.getCarteDansMain(i);

					if (!cj.joueurActuelPoseCarteDansMain(carte, x, y))
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

				if (!cj.joueurActuelDeplaceCarte(x1, y1, x2, y2))
					System.out.println("Erreur: Pas pu déplacer carte!");

				return;

			case "finir":
				if (cj.terminerTourJoueurActuel()) {
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
		switch (cj.getRegles()) {
			case Standard:
				executeStandard();
				break;
			case Advanced:
				executeAdvanced();
				break;
			case Variante:
				executeVariante();
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + cj.getRegles());
		}

	}

}
