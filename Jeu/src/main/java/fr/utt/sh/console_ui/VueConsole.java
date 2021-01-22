package fr.utt.sh.console_ui;

import java.util.Observable;
import java.util.Observer;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;
import fr.utt.sh.core.Position;
import fr.utt.sh.core.actions.ActionMainJoueur;
import fr.utt.sh.core.actions.ActionTapis;
import fr.utt.sh.core.actions.DeplacerCarte;
import fr.utt.sh.core.actions.FinPartie;
import fr.utt.sh.core.actions.NouveauJoueur;
import fr.utt.sh.core.actions.PiocherCarte;
import fr.utt.sh.core.actions.PoserCarte;

/**
 * Une classe singleton permettant d'afficher la vue du jeu actuel à la console.
 * @author grego
 *
 */
public class VueConsole implements Observer {

	private static VueConsole instance;
	private ControlleurJeu    cj;

	/**
	 * Demarrer la vue.
	 * <br>
	 * Nécessite que controlleurJeu soit déjà initialisé.
	 */
	public static void begin() {
		if (instance == null)
			instance = new VueConsole();
	}

	private VueConsole() {
		cj = ControlleurJeu.getInstance();
		cj.addObserver(this);

	}

	@Override
	public void update(Observable arg0, Object arg1) {

		if (arg1 instanceof ActionMainJoueur) {
			if (arg1 instanceof PoserCarte) {
				afficherPoserCarte((PoserCarte) arg1);

				switch (cj.getRegles()) {
					case Standard:
						break;
					case Advanced:
					case Variante:
						afficherMainJoueurActuel();
						break;
					default:
						throw new IllegalArgumentException("Unexpected value: " + cj.getRegles());
				}
			}

			else if (arg1 instanceof PiocherCarte) {
				afficherJoueurPiocheCarte((PiocherCarte) arg1);
			}

		}

		if (arg1 instanceof DeplacerCarte)
			afficherDeplacerCarte((DeplacerCarte) arg1);

		if (arg1 instanceof ActionTapis)
			afficherTapis();
		
		if(arg1 instanceof NouveauJoueur)
			afficherNouveauJoueur((NouveauJoueur) arg1);
		
		if(arg1 instanceof FinPartie)
			afficherScoresDesJoueurs();

	}

	private void afficherNouveauJoueur(NouveauJoueur nouveauJoueur) {
		Joueur joueur = nouveauJoueur.getJoueur();
		
		System.out.println();
		System.out.println("--------------------------------------------");
		
		System.out.println(String.format("A %s de jouer", joueur));
		switch (cj.getRegles()) {
		case Standard:
			Carte carteVictoire = joueur.getCarteVictoire();
			System.out.println(String.format("Carte victoire: %s (%s)", GenerateurString.getStringCarte(carteVictoire), carteVictoire));
			break;
		case Variante:
		case Advanced:
			break;	

		default:
			throw new IllegalArgumentException("Unexpected value: " + cj.getRegles());
		}
		
	}

	private void afficherDeplacerCarte(DeplacerCarte deplacerCarte) {
		Position source      = deplacerCarte.getSource();
		Position destination = deplacerCarte.getDestination();

		Carte carte = cj.getTapis().getCarteAt(destination);

		String stringCarte = GenerateurString.getStringCarte(carte);

		System.out.println(
				String.format("%s a deplacé %s de %s vers %s", cj.getJoueurActuel(), stringCarte, source, destination));

	}

	private void afficherJoueurPiocheCarte(PiocherCarte piocherCarte) {
		Carte  carte            = piocherCarte.getCarte();
		String stringJoueur     = cj.getJoueurActuel().toString();
		String stringCarte      = carte.toString();
		String stringCourtCarte = GenerateurString.getStringCarte(carte);

		System.out.println(String.format("%s pioché: %s (%s)", stringJoueur, stringCourtCarte, stringCarte));
	}

	private void afficherPoserCarte(PoserCarte poserCarte) {
		String stringJoueur   = cj.getJoueurActuel().toString();
		String stringCarte    = GenerateurString.getStringCarte(poserCarte.getCarte());
		String stringPosition = poserCarte.getPosition().toString();

		System.out.println(String.format("%s a posé %s a %s", stringJoueur, stringCarte, stringPosition));
	}

	private void afficherTapis() {
		System.out.print(GenerateurString.getStringTapis(cj.getTapis()));
	}

	private void afficherMainJoueurActuel() {
		System.out.println("Main:");
		System.out.println(GenerateurString.getStringCartesDansMainJoueur(cj.getJoueurActuel()));
		System.out.println();
	}

	private void afficherScoresDesJoueurs() {
		cj.getJoueurs().forEach(j -> {
			System.out.println(String.format("Score de %s: %d", j, j.getScore()));
		});
	}

}
