package fr.utt.sh.gui;

import fr.utt.sh.core.tapis.Tapis;

public class GenerateurVueTapis {

	
	public static VueTapis generate(Tapis tapis) {
		VisitorAffichageGenerateurEmplacementsCarte vis = new VisitorAffichageGenerateurEmplacementsCarte();
		tapis.accept(vis);
		VueTapis vueTapis = vis.getVueTapis();
		
		return vueTapis;
	}
}
