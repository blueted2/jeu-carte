package fr.utt.sh.core.score;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.tapis.Tapis_Rectangulaire;
import fr.utt.sh.core.tapis.Tapis_Triangulaire;

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
	 * @param tapis Le {@link Tapis_Rectangulaire} a visiter.
	 */
	public abstract void visit(Tapis_Rectangulaire tapis);

	/**
	 * Visiter un tapis triangulaire afin d'y compter le score pour une certaine
	 * carte.
	 * 
	 * @param tapis Le {@link Tapis_Triangulaire} a visiter.
	 */
	public abstract void visit(Tapis_Triangulaire tapis);

	/**
	 * Permet d'obtenir le nombre de points APRES avoir visiter le tapis. Si cette
	 * methode est appelee avant de visiter un tapis, le score retourné sera 0.
	 * 
	 * @return Le nombre de points pour la carte.
	 */
	public abstract int getPoints();

}
