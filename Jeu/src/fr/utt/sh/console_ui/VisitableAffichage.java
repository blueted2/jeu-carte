package fr.utt.sh.console_ui;

/**
 * @author grego
 *
 */
public interface VisitableAffichage {

	/**
	 * @param v
	 */
	public void accept(VisitorAffichage v);
}
