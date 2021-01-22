package fr.utt.sh.core;

import fr.utt.sh.console_ui.Utils;
import fr.utt.sh.core.tapis.TypeTapis;
import fr.utt.sh.gui.InterfaceConfiguration;;

/**
 * 
 * 
 * @author grego
 *
 */
@SuppressWarnings("unused")
public class Main {

	/**
	 * Méthode main, exécutant le jeu.
	 * 
	 * @param args Les arguments de ligne de commande.
	 */
	public static void main(String[] args) {

		ControlleurJeu c = ControlleurJeu.getInstance();

		switch (args.length) {
			case 1:
				if (args[0].equals("console")) {
					c.commencerNouveauJeu(1, 1, Regles.Standard, TypeTapis.Rectangulaire_5x3, 2, false);
				} else {
					InterfaceConfiguration.begin();
				}
				break;

			case 5:
				boolean console = args[0].equals("console");
				int nbHumains = Integer.parseInt(args[1]);
				int nbBots = Integer.parseInt(args[2]);
				int indexRegles = Integer.parseInt(args[3]);
				int indexTapis = Integer.parseInt(args[4]);
				Regles regles = Regles.values()[indexRegles];
				TypeTapis tapis = TypeTapis.values()[indexTapis];

				c.commencerNouveauJeu(nbHumains, nbBots, regles, tapis, 2, !console);
				break;

			default:
				InterfaceConfiguration.begin();
				break;
		}

		while (!c.isJeuTermine()) {
			try {
				Thread.sleep(10);// Sans le delai, le programme devient instable.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
