package fr.utt.sh.core.score;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.tapis.TapisTri;
import fr.utt.sh.core.tapis.TapisRect;

/**
 * Cette interface a pour but d'être utilisée pour compter le score du tapis pour
 * une certaine carte qui sera donnée par la methode {@link #setCarte}.
 * 
 * @author grego
 *
 */
public interface VisitorComptageScore {
	/**
	 * Définir la carte pour laquelle le score est calculé.
	 * 
	 * @param carte La {@link Carte} pour laquelle on calcule le score.
	 */
	public abstract void setCarte(Carte carte);

	/**
	 * Visiter un tapis rectangulaire afin d'y compter le score pour une certaine
	 * carte.
	 * 
	 * @param tapis Le {@link TapisRect} à visiter.
	 */
	public abstract void visit(TapisRect tapis);

	/**
	 * Visiter un tapis triangulaire afin d'y compter le score pour une certaine
	 * carte.
	 * 
	 * @param tapis Le {@link TapisTri} à visiter.
	 */
	public abstract void visit(TapisTri tapis);

	/**
	 * Permet d'obtenir le nombre de points APRÈS avoir visité le tapis. Si cette
	 * méthode est appelée avant de visiter un tapis, le score retourné sera 0.
	 * 
	 * @return Le nombre de points pour la carte.
	 */
	public abstract int getPoints();

}
