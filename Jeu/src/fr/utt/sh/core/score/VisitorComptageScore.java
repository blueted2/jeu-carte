package fr.utt.sh.core.score;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.tapis.Tapis_Rectangulaire;

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
	 * @param carte La {@link Carte} pour laquelle on caclule le score.
	 */
	public abstract void setCarte(Carte carte);
	
	/**
	 * Visiter le tapis.
	 * @param tapis Le {@link Tapis_Rectangulaire} a visiter.
	 */
	public abstract void visit(Tapis_Rectangulaire tapis);

	public abstract int getPoints();
}
