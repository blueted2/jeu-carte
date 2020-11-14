package fr.utt.sh.console_ui;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.tapis.Tapis_5x3;

/**
 * @author grego
 *
 */
public interface VisitorAffichage {
	/**
	 * Visiter un {@link Tapis_5x3}, pour pouvoir créer une version affichable.
	 * @param tapis {@link Tapis_5x3}
	 */
	public void visit(Tapis_5x3 tapis);
	
	/**
	 * Visiter une {@link Carte}, pour pouvoir créer une version affichable.
	 * @param carte {@link Carte}
	 */
	public void visit(Carte carte);
	
}
