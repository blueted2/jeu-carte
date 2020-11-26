/**
 * 
 */
package fr.utt.sh.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import fr.utt.sh.console_ui.VisitableAffichage;
import fr.utt.sh.console_ui.VisitorAffichage;
import fr.utt.sh.core.strategy.Strategy;
import fr.utt.sh.core.strategy.StrategyJoueurConsole;

/**
 * Cette classe représente un joueur. Elle implémente le patron de conception
 * {@code Strategy}. Chaque tour, ControlleurJeu appelle la méthode
 * {@link Joueur#jouer()}, ce qui en tour appelle la méthode exécute d'une
 * {@link Strategy}.
 * 
 * @author grego
 *
 */
public class Joueur {

	private ArrayList<Carte> cartesMain = new ArrayList<>();

	private Carte cartePiochee;
	private Carte carteVictoire;

	private ControlleurJeu cj = ControlleurJeu.getInstance();

	private String id;

	private Strategy strategy = new StrategyJoueurConsole();

	private int score = 0;

	/**
	 * Constructeur par défaut.
	 */
	public Joueur() {
		this.id = "defaut";
	}

	public Joueur(String id, Strategy strategy) {
		this.id       = id;
		this.strategy = strategy;
	}

	/**
	 * Constructeur clonage.
	 * 
	 * @param joueur Le {@link Joueur} a cloner.
	 */
	public Joueur(Joueur joueur) {
		this.id            = joueur.id;
		this.strategy      = joueur.strategy;
		this.cartePiochee  = joueur.cartePiochee;
		this.carteVictoire = joueur.carteVictoire;
		this.cartesMain    = new ArrayList<Carte>(joueur.cartesMain);
		this.score         = joueur.score;
	}

	/**
	 * Seulement utilisé pour les règles standards.
	 * 
	 * @return La {@link Carte} précédemment piochée.
	 */
	public Carte getCartePiochee() {
		return cartePiochee;
	}

	public void setCartePiochee(Carte carte) {
		cartePiochee = carte;
	}

	/**
	 * Seulement utilisé pour les règles standards.
	 * 
	 * @return La {@link Carte} victoire.
	 */
	public Carte getCarteVictoire() {
		return carteVictoire;
	}

	public void setCarteVictoire(Carte carte) {
		carteVictoire = carte;
	}

	/**
	 * Donne la carte a l'index donné.
	 * 
	 * @param index L'indice de la carte demandée.
	 * @return {@link Carte}
	 */
	public Carte getCarteDansMain(int index) {
		if (cartesMain.size() <= index) {
			return null;
		}

		return cartesMain.get(index);
	}

	public boolean hasCarte(Carte carte) {
		return cartesMain.contains(carte);
	}

	public void ajouterCarteDansMain(Carte carte) {
		cartesMain.add(carte);
	}

	public boolean retirerCarteDansMain(Carte carte) {
		if (!cartesMain.contains(carte))
			return false;

		cartesMain.remove(carte);
		return true;
	}

	/**
	 * @return {@code int} le nombre de cartes.
	 */
	public int getNombreCartesDansMain() {
		return cartesMain.size();
	}

	/**
	 * 
	 * @return Une {@link ArrayList} des cartes dans le main du joueur.
	 */
	public ArrayList<Carte> getCartesDansMain() {
		return cartesMain;
	}

	/**
	 * Exécute la strategy donné lors de la construction du joueur.
	 * 
	 * @return {@code true} si le joueur a fini son tour, {@code false} sinon.
	 */
	public boolean jouer() {
		if (strategy.execute())
			return true;
		return false;
	}

	public String toString() {
		return "Joueur_" + id;
	}

	public Joueur getClone() {
		return new Joueur(this);
	}

	public String getStringCartesDansMain() {
		String ligneHaut = " ";
		String ligneBas  = "|";

		int i = 0;
		for (Carte carte : cartesMain) {
			ligneHaut += i + "  ";
			ligneBas  += carte.getStringCarte() + "|";
			i++;
		}

		return ligneHaut + "\n" + ligneBas;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
