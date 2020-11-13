package fr.utt.sh.console_ui;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Tapis_5x3;

/**
 * @author grego
 *
 */
public interface VisitorAffichage {
	/**
	 * @param tapis
	 */
	public void visit(Tapis_5x3 tapis);
	/**
	 * @param carte
	 */
	public void visit(Carte carte);
	
}
