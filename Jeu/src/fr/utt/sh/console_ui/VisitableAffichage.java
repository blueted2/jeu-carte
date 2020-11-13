package fr.utt.sh.console_ui;

/**
 * @author grego
 *
 */
public interface VisitableAffichage {

	/**
	 * Accepter un {@code VisitorAffichage}.
	 * @param v {@code VisitorAffichage}.
	 */
	public void accept(VisitorAffichage v);
}
