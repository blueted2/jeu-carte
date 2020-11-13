/**
 * 
 */
package fr.utt.sh.core;

import java.util.ArrayList;

/**
 * Cette classe représente un joueur. Elle implemente le patron de conception
 * strategy. Chaque tour, ControlleurJeu appelle la methode {@link Joueur#jouer()}, ce
 * qui en tour appelle la methode execute d'une {@link Strategy}.
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
	 * Constructeur par defaut.
	 */
	public Joueur() {
		 this.id = "defaut";
	}

	/**
	 * Construvteur avec un id, pour l'affichaige dans la console. 
	 * @param id Un {@code String} pour l'affichage dans la console.
	 */
	public Joueur(String id) {
		this.id = id;
	}

	/**
	 * Seulement utilisé pour les regles standards.
	 * 
	 * @return La {@link Carte} precedement piochée.
	 */
	public Carte getCartePiochee() {
		return cartePiochee;
	}

	/**
	 * Seulement utilisé pour les regles standards.
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
	 * Seuelement utiliser avec les regles standards.
	 * @return Une {@link ArrayList} des cartes dans le deck du joueur.
	 */
	public ArrayList<Carte> getCartes() {
		return cartes;
	}

	/**
	 * Pose la carte qui vient d'etre piochee. Doit seuelement etre utilisé pour les
	 * regles normales.
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
	 * Poser une carte du deck. 
	 * @param i L'index de la carte a poser.
	 * @param x Abscisse de la carte.
	 * @param y Ordonnée de la carte.
	 * @return {@code true} si la carte a pu etre posée, {@code false} sinon.
	 */
	public boolean poserCarte(int i, int x, int y) {
		return false;
	}

	/**
	 * @return {@code true} si une carte a pu etre piochée, {@code false} sinon.
	 */
	public boolean piocherCarte() {
		Carte nouvelleCarte = cj.piocherCarte(this);
		if (cj == null)
			return false;

		cartePiochee = nouvelleCarte;
		return true;
	}

	/**
	 * Excute la strategy donné lors de la construction du joueur.
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
