/**
 * 
 */
package fr.utt.sh.core;

import java.util.ArrayList;

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

	ArrayList<Carte> cartes = new ArrayList<Carte>();
	Carte cartePiochee;
	Carte victoire;

	ControlleurJeu cj = ControlleurJeu.getInstance();

	String id;

	Strategy strategy = new StrategyTest();

	/**
	 * Constructeur par défaut.
	 */
	public Joueur() {
		this.id = "defaut";
	}

	/**
	 * Constructeur avec un id, pour l'affichaige dans la console.
	 * 
	 * @param id Un {@code String} pour l'affichage dans la console.
	 */
	public Joueur(String id) {
		this.id = id;
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
	 * Seulement utilisé pour les règles standards.
	 * 
	 * @return La {@link Carte} victoire.
	 */
	public Carte getCarteVicotoire() {
		return null;
	}

	/**
	 * Donne la carte a l'index donné.
	 * 
	 * @param index L'indice de la carte demandée.
	 * @return {@link Carte}
	 */
	public Carte getCarte(int index) {
		if (cartes.size() <= index) {
			return null;
		}

		return cartes.get(index);
	}

	/**
	 * @return {@code int} le nombre de cartes.
	 */
	public int getNombreCartes() {
		return cartes.size();
	}

	/**
	 * Seulement utiliser avec les règles standards.
	 * 
	 * @return Une {@link ArrayList} des cartes dans le main du joueur.
	 */
	public ArrayList<Carte> getCartes() {
		return cartes;
	}

	/**
	 * Pose la carte qui vient d'être piochée. Doit seulement être utilisé pour les
	 * règles normales.
	 * 
	 * @param x Abscisse de la carte.
	 * @param y Ordonnée de la carte.
	 * @return {@code true} si la carte a pu etre posée, {@code false} sinon
	 */
	public boolean poserCarte(int x, int y) {
		if (cartePiochee == null)
			return false;

		if (!cj.poserCarte(this, cartePiochee, x, y))
			return false;

		cartePiochee = null;
		return true;
	}

	/**
	 * Poser une carte dans la main du joueur. .
	 * 
	 * @param i L'index de la carte a poser.
	 * @param x Abscisse de la carte.
	 * @param y Ordonnée de la carte.
	 * @return {@code true} si la carte a pu être posée, {@code false} sinon.
	 */
	public boolean poserCarte(int i, int x, int y) {
		return false;
	}

	/**
	 * @return {@code true} si une carte a pu être piochée, {@code false} sinon.
	 */
	public boolean piocherCarte() {
		Carte nouvelleCarte = cj.piocherCarte(this);
		if (cj == null)
			return false;

		cartePiochee = nouvelleCarte;
		return true;
	}

	/**
	 * Exécute la strategy donné lors de la construction du joueur.
	 */
	public void jouer() {
		if (strategy.execute(this)) {
			System.out.println(String.format("Je suis %s et j'ai joué", this.toString()));
			cj.passerAuJoueurSuivant();
		}
	}

	public String toString() {
		return "Joueur_" + id;
	}
}
