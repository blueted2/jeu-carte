package fr.utt.sh.core.score;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.tapis.TapisTri;
import fr.utt.sh.core.tapis.TapisRect;

/**
 * Ce interface a pour but d'etre utilisé pour compter le score du tapis pour
 * une certanine carte qui sera donné par la methode {@link #setCarte}.
 * 
 * @author grego
 *
 */
public interface VisitorComptageScore {
	/**
	 * Definir la carte pour laquelle le score est calculé.
	 * 
	 * @param carte La {@link Carte} pour laquelle on caclule le score.
	 */
	public abstract void setCarte(Carte carte);

	/**
	 * Visiter un tapis rectangulaire afin d'y compter le score pour une certaine
	 * carte.
	 * 
	 * @param tapis Le {@link TapisRect} a visiter.
	 */
	public abstract void visit(TapisRect tapis);

	/**
	 * Visiter un tapis triangulaire afin d'y compter le score pour une certaine
	 * carte.
	 * 
	 * @param tapis Le {@link TapisTri} a visiter.
	 */
	public abstract void visit(TapisTri tapis);

	/**
	 * Permet d'obtenir le nombre de points APRES avoir visiter le tapis. Si cette
	 * methode est appelee avant de visiter un tapis, le score retourné sera 0.
	 * 
	 * @return Le nombre de points pour la carte.
	 */
	public abstract int getPoints();

}
