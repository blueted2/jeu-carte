package fr.utt.sh.core.score;

/**
 * Une interface que peuvent implementer d'autres classes afin qu'elles puissent
 * avoir leur score compté. Seulement utilisé pour compter le score d'une carte
 * dans un tapis.
 * 
 * @author grego
 *
 */
public interface VisitableComptageScore {

	/**
	 * Accepter qu'un visiteur puisser venir compter le score.
	 * @param visitorComptageScore Le {@link VisitorComptageScore} souhaité. 
	 */
	public abstract void accept(VisitorComptageScore visitorComptageScore);
}
