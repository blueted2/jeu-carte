package fr.utt.sh.gui;

import fr.utt.sh.core.tapis.Tapis;
import fr.utt.sh.gui.vue.VueTapis;

/**
 * Ce générateur est un abstraction du visiteur
 * {@link VisitorAffichageGenerateurEmplacementsCarte}, permettant de créer une
 * instance de {@link VueTapis} avec les emplacements correctes pour tout types
 * de tapis.
 * 
 * @author grego
 *
 */
public class GenerateurVueTapis {

	/**
	 * Générer l'instance de {@link VueTapis}
	 * 
	 * @param tapis Le tapis pour lequel generer la visualisation.
	 * @return La visualisation {@link VueTapis} pour le tapis du jeu en cours.
	 */
	public static VueTapis generate(Tapis tapis) {
		VisitorAffichageGenerateurEmplacementsCarte vis = new VisitorAffichageGenerateurEmplacementsCarte();
		tapis.accept(vis);
		VueTapis vueTapis = vis.getVueTapis();

		return vueTapis;
	}
}
