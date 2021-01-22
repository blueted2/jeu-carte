package fr.utt.sh.core.strategy;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;
import fr.utt.sh.core.Position;
import fr.utt.sh.core.Regles;
import fr.utt.sh.core.score.VisitorComptageScore;
import fr.utt.sh.core.score.VisitorComptageScoreStandard;
import fr.utt.sh.core.tapis.Tapis;

/**
 * Une implémentation test d'une strategy. Quand cette strategy est utilisée,
 * elle teste chaque position du tapis afin de trouver celle qui
 * donnera le meilleur score. <br>
 * Limites: Cette stratégie ne posera pas la carte à une position qui nécessitera un
 * déplacement.
 * 
 * @author grego
 *
 */
public class StrategyBot implements Strategy {

	ControlleurJeu cj = ControlleurJeu.getInstance();

	Tapis tapisTemp;
	int   lTapis;
	int   hTapis;

	private int delay = 500;

	Joueur joueurActuel;

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

		tapisTemp = cj.getCloneTapis();
		lTapis    = tapisTemp.getLargeur();
		hTapis    = tapisTemp.getHauteur();

		switch (cj.getRegles()) {
			case Standard:
				executeStandard();
				break;
			case Advanced:
			case Variante:
				executeAdvanced();
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + cj.getRegles());
		}
		return;
	}

	/**
	 * Fait jouer un joueur bot, selon les {@link Regles} Standard.
	 * @return {@code true} si le tour du bot a pu être terminé, {@code false}
	 *         sinon.
	 * @throws InterruptedException si le tour est interrompu. 
	 */
	private boolean executeStandard() throws InterruptedException {

		Thread.sleep(delay);
		cj.joueurActuelPiocheCarte();
		Thread.sleep(delay);

		if (cj.tapisEstVide()) {
			cj.joueurActuelPoseCartePiochee(0, 0);
			Thread.sleep(delay);
			return cj.terminerTourJoueurActuel();
		}

		joueurActuel = cj.getJoueurActuel();

		Carte carteVictoire = joueurActuel.getCarteVictoire();
		Carte cartePiochee  = joueurActuel.getCartePiochee();

		Position meilleurePosition = getMeilleurePosition(carteVictoire, cartePiochee);

		int x = meilleurePosition.getX();
		int y = meilleurePosition.getY();

		cj.joueurActuelPoseCartePiochee(x, y);
		Thread.sleep(delay);
		return cj.terminerTourJoueurActuel();
	}

	/**
	 * Fait jouer un joueur bot, selon les {@link Regles} Advanced.
	 * @return {@code true} si le tour du bot a pu être terminé, {@code false}
	 *         sinon.
	 * @throws InterruptedException si le tour est interrompu. 
	 */
	private boolean executeAdvanced() throws InterruptedException {

		joueurActuel = cj.getJoueurActuel();

		Carte carteVictoire = joueurActuel.getCarteDansMain(0);
		Carte carteAPoser   = joueurActuel.getCarteDansMain(1);

		Position meilleurePosition = getMeilleurePosition(carteVictoire, carteAPoser);

		int x = meilleurePosition.getX();
		int y = meilleurePosition.getY();

		Thread.sleep(delay);
		cj.joueurActuelPoseCarteDansMain(carteAPoser, x, y);
		Thread.sleep(delay);
		cj.joueurActuelPiocheCarte();
		Thread.sleep(delay);

		return cj.terminerTourJoueurActuel();
	}

	/**
	 * Détermine la meilleure position où poser une carte donnée, c'est-à-dire la position qui rapporterait le plus de points.
	 * @param carteVictoire La carte de victoire du joueur.
	 * @param carteAPoser La carte que le joueur veut poser. 
	 * @return Position La meilleure position possible. 
	 */
	private Position getMeilleurePosition(Carte carteVictoire, Carte carteAPoser) {
		int posXMax  = -1; // Position invalide, pour tester si la valeur a été changée.
		int posYMax  = -1;
		int scoreMax = -1;

		// Essayer toutes les coordonnées possibles pour trouver une position jouable, et
		// qui donnerait le meilleur score.
		for (int y = 0; y < hTapis; y++) {
			for (int x = 0; x < lTapis; x++) {

				if (tapisTemp.positionSurTapis(x, y)) {
					if (tapisTemp.poserCarte(carteAPoser, x, y)) {

						VisitorComptageScore v = new VisitorComptageScoreStandard(carteVictoire);
						tapisTemp.accept(v);
						int points = v.getPoints();

						if (points > scoreMax) {
							scoreMax = points;
							posXMax  = x;
							posYMax  = y;
						}
						tapisTemp.retirerCarte(x, y);
					}
				}
			}
		}
		return new Position(posXMax, posYMax);
	}

	public void arreter() {
	};

}
