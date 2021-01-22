package fr.utt.sh.console_ui;

import fr.utt.sh.core.tapis.TapisTri;
import fr.utt.sh.core.tapis.decalable.TapisRectDecalable;
import fr.utt.sh.core.tapis.TapisRect;

/**
 * @author grego
 *
 */
public interface VisitorAffichage {
	/**
	 * Visiter un {@link TapisRect}, pour pouvoir créer une version affichable.
	 * @param tapis {@link TapisRect}
	 */
	public void visit(TapisRect tapis);
	
	/**Visiter un {@link TapisTri}, pour pouvoir créer une version affichable.
	 * @param tapis {@link TapisTri}
	 */
	public void visit(TapisTri tapis);

	/**
	 * Visiter un {@link TapisRectDecalable}, pour pouvoir créer une version affichable.
	 * @param tapis {@link TapisRectDecalable}
	 */
	public void visit(TapisRectDecalable tapis);

}
