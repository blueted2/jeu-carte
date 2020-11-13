/**
 * 
 */
package fr.utt.sh.core;

import java.util.ArrayList;

/**
 * Cette classe représente un joueur.
 * 
 * @author grego
 *
 */
public class Joueur {

	ArrayList<Carte> cartes = new ArrayList<Carte>();
	String id;
	
	public Joueur(String id) {
		this.id = id;
	}

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
	

	public void jouer() {
		System.out.println(String.format("Je suis %s et j'ai joué", this.toString()));
	}
	
	
	public String toString() {
		return "Joueur_" + id;
	}
}
