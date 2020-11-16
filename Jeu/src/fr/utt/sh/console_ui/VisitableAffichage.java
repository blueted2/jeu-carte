package fr.utt.sh.console_ui;

/**
 * @author grego
 *
 */
public interface VisitableAffichage {

	/**
	 * Accepter un {@link VisitorAffichage}.
	 * @param v {@link VisitorAffichage}.
	 */
	public void accept(VisitorAffichage v);
}
