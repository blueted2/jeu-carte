/**
 * 
 */
package fr.utt.sh.core;

import java.util.ArrayList;

/**
 * Cette classe représente un joueur. Elle implemente le patron de conception strategy. 
 * Chaque tour, ControlleurJeu appelle la methode {@code joueur}, ce qui en tour appelle la methode execute d'une {@code Strategy}.
 * 
 * @author grego
 *
 */
public class Joueur {

	ArrayList<Carte> cartes = new ArrayList<Carte>();
	String id;
	
	Strategy strategy = new StrategyTest();
	
	public Joueur(String id) {
		this.id = id;
	}

	/**
	 * Donne la carte a l'index donné.
	 * @param index
	 * @return
	 */
	public Carte getCarte(int index) {
		if (cartes.size() <= index) {
			return null;
		}

		return cartes.get(index);
	}
	
	public int getNombreCartes() {
		return cartes.size();
	}
	
	public ArrayList<Carte> getCartes(){
		return cartes;
	}
	

	/**
	 * Excute la strategy donné lors de la construction du joueur.
	 */
	public void jouer() {
		if(strategy.execute(this)) 
			System.out.println(String.format("Je suis %s et j'ai joué", this.toString()));
	}
	
	
	public String toString() {
		return "Joueur_" + id;
	}
}
