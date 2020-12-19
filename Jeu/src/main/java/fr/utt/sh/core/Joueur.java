/**
 * 
 */
package fr.utt.sh.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import fr.utt.sh.console_ui.GenerateurString;
import fr.utt.sh.console_ui.VisitableAffichage;
import fr.utt.sh.console_ui.VisitorAffichage;
import fr.utt.sh.core.strategy.Strategy;
import fr.utt.sh.core.strategy.StrategyJoueurConsole;

/**
 * <pre>
 * Cette classe représente un joueur. Elle implémente le patron de conception
 * {@code Strategy}. Chaque tour, ControlleurJeu appelle la méthode
 * {@link Joueur#jouer()}, ce qui en tour appelle la méthode exécute d'une
 * {@link Strategy}.
 * 
 * Les attributs du joueur comme cartePiochee, carteVictoire ... sont tous
 * accessibles par des getters et setters, afin d'etre controllés de
 * l'exterieur, c'est-a-dire la classe {@code Joueur} n'est pas responsable pour
 * ses actions, c'est simplement un rassemblement de cartes. 
 * Par exemple, pour piocher une carte, la {@link Strategy} du joueur appelle la methode
 * {@link ControlleurJeu#joueurActuelPiocheCarte()}, qui, apres avoir fait
 * certains verifications, va enlever une carte du tas de cartes restantes, et
 * la mettre dans {@code cartePioche} si les regles sont standard, ou a la fin de
 * {@code cartesDansMain} si les regles sont Advanced.
 * 
 * </pre>
 * 
 * @author grego
 *
 */
public class Joueur {

	private ArrayList<Carte> cartesMain = new ArrayList<>();

	private Carte cartePiochee;
	private Carte carteVictoire;

	private String id;

	private Strategy strategy = new StrategyJoueurConsole();

	private int score = 0;

	/**
	 * Constructeur pour un {@code Joueur}.
	 * 
	 * @param id       Un {@code String} pour donner un nom / identifiant au joueur.
	 * @param strategy La {@link Strategy} que ce joueur va implémenter.
	 */
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

	/**
	 * Attribuer une carte a cartePiochee.
	 * 
	 * @param carte {@link Carte}.
	 */
	public void setCartePiochee(Carte carte) {
		cartePiochee = carte;
	}

	/**
	 * Seulement utilisé pour les règles standards.
	 * 
	 * @return La {@link Carte} victoire du joueur.
	 */
	public Carte getCarteVictoire() {
		return carteVictoire;
	}

	/**
	 * Attribuer une carte a carteVictoire.
	 * 
	 * @param carte {@link Carte}.
	 */
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

	/**
	 * Indiquer si le joueur a la carte indiquée.
	 * 
	 * @param carte {@link Carte}.
	 * @return {@code true} si le joueur a la carte, {@code false} sinon.
	 */
	public boolean hasCarte(Carte carte) {
		return cartesMain.contains(carte);
	}

	/**
	 * Ajouter une nouvelle carte dans le set {@code cartesDansMain}.
	 * 
	 * @param carte La {@link Carte} a ajouter.
	 */
	public void ajouterCarteDansMain(Carte carte) {
		cartesMain.add(carte);
	}

	/**
	 * Enlever la carte du set {@code cartesDansMain}.
	 * 
	 * @param carte La {@link Carte} a retirer.
	 * @return {@code true} si la carte etait dans le set et a pu etre retirée,
	 *         {@code false}.
	 */
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

	/**
	 * Donne un clone du joueur.
	 * @return {@link Joueur}
	 */
	public Joueur getClone() {
		return new Joueur(this);
	}

	/**
	 * TODO : Retirer
	 * @return int
	 * 
	 */
	public int getScore() {
		return score;
	}

	/**
	 * TODO : Retirer
	 * @param score int
	 */
	public void setScore(int score) {
		this.score = score;
	}

}
