/**
 * 
 */
package fr.utt.sh.core;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.utt.sh.core.strategy.Strategy;
import fr.utt.sh.core.strategy.StrategyJoueurConsole;

/**
 * <pre>
 * Cette classe représente un joueur. Elle implémente une version du patron de conception
 * {@code Strategy}, modifié pour fonctionner en multi thread. Au debut de chaque tour, ControlleurJeu appelle la méthode
 * {@link Joueur#beginStrategyThread()}, ce qui à son tour commence un thread exécutant une 
 * {@link Strategy}.
 * 
 * Les attributs du joueur comme cartePiochee, carteVictoire ... sont tous
 * accessibles par des getters et setters, afin d'etre controllés de
 * l'exterieur, c'est-a-dire que la classe {@code Joueur} n'est pas responsable pour
 * ses actions, c'est simplement un rassemblement de cartes. 
 * Par exemple, pour piocher une carte, la {@link Strategy} du joueur appelle la méthode
 * {@link ControlleurJeu#joueurActuelPiocheCarte()}, qui, après avoir fait
 * certaines vérifications, va enlever une carte du tas de cartes restantes, et
 * la mettre dans {@code cartePioche} si les règles sont standard, ou à la fin de
 * {@code cartesDansMain} si les règles sont Advanced.
 * 
 * </pre>
 * 
 * @author grego
 *
 */
public class Joueur {

	private CopyOnWriteArrayList<Carte> cartesMain = new CopyOnWriteArrayList<>();

	private Carte cartePiochee;
	private Carte carteVictoire;

	private boolean humain;

	/**
	 * Le joueur est-il controllé par un humain ? Utilisé pour l'interface graphique
	 * afin de determiner si l'interface doit être activée ou non.
	 * 
	 * @return {@code true} si le joueur est controllé par un humain, {@code false}
	 *         sinon.
	 */
	public boolean isHumain() {
		return humain;
	}

	private String id;

	private Strategy strategy = new StrategyJoueurConsole();

	private int score = 0;

	/**
	 * Constructeur pour un {@code Joueur}.
	 * 
	 * @param id       Un {@code String} pour donner un nom / identifiant au joueur.
	 * @param strategy La {@link Strategy} que ce joueur va implémenter.
	 * @param humain   Le joueur est-il humain ?
	 */
	public Joueur(String id, Strategy strategy, boolean humain) {
		this.id       = id;
		this.strategy = strategy;
		this.humain   = humain;
	}

	/**
	 * Constructeur clonage.
	 * 
	 * @param joueur Le {@link Joueur} à cloner.
	 */
	public Joueur(Joueur joueur) {
		this.id            = joueur.id;
		this.strategy      = joueur.strategy;
		this.cartePiochee  = joueur.cartePiochee;
		this.carteVictoire = joueur.carteVictoire;
		this.cartesMain    = new CopyOnWriteArrayList<Carte>(joueur.cartesMain);
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
	 * Attribuer une carte à cartePiochee.
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
	 * Attribuer une carte à carteVictoire.
	 * 
	 * @param carte {@link Carte}.
	 */
	public void setCarteVictoire(Carte carte) {
		carteVictoire = carte;
	}

	/**
	 * Donne la carte à l'index donné.
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
	 * @param carte La {@link Carte} à ajouter.
	 */
	public void ajouterCarteDansMain(Carte carte) {
		cartesMain.add(carte);
	}

	/**
	 * Enlever la carte du set {@code cartesDansMain}.
	 * 
	 * @param carte La {@link Carte} à retirer.
	 * @return {@code true} si la carte était dans le set et a pu être retirée,
	 *         {@code false} sinon.
	 */
	public boolean retirerCarteDansMain(Carte carte) {
		if (!cartesMain.contains(carte))
			return false;

		cartesMain.remove(carte);

		return true;
	}

	/**
	 * @return {@code int} le nombre de cartes dans la main du joueur.
	 */
	public int getNombreCartesDansMain() {
		return cartesMain.size();
	}

	/**
	 * 
	 * @return Une {@link ArrayList} des cartes dans la main du joueur.
	 */
	public CopyOnWriteArrayList<Carte> getCartesDansMain() {
		return cartesMain;
	}

	/**
	 * Commencer le thread de la strategy du joueur.
	 * 
	 * @return Le {@code Thread} de la strategy.
	 */
	public Thread beginStrategyThread() {
		Thread threadStrategy = new Thread(strategy, this.toString());
		threadStrategy.start();
		return threadStrategy;
	}

	public String toString() {
		return "Joueur_" + id;
	}

	/**
	 * Donne un clone du joueur.
	 * 
	 * @return {@link Joueur}
	 */
	public Joueur getClone() {
		return new Joueur(this);
	}

	/**
	 * Obtenir le score du joueur.
	 * 
	 * @return Le score du joueur.
	 * 
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Affecter le score du joueur.
	 * 
	 * 
	 * @param score Le nouveau score du joueur.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Demander à la strategy du joueur d'arrêter.
	 */
	public void arreterStrategy() {
		strategy.arreter();
	}

	/**
	 * Supprimer toutes les cartes du joueur.
	 */
	public void resetCartes() {
		cartePiochee  = null;
		carteVictoire = null;
	}

}
