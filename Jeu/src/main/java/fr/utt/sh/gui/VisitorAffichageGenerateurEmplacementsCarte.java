package fr.utt.sh.gui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import fr.utt.sh.console_ui.VisitorAffichage;
import fr.utt.sh.core.Position;
import fr.utt.sh.core.tapis.Tapis_Rectangulaire;
import fr.utt.sh.core.tapis.Tapis_Triangulaire;

public class VisitorAffichageGenerateurEmplacementsCarte implements VisitorAffichage {

	private VueTapis vueTapis;

	@Override
	public void visit(Tapis_Rectangulaire tapis) {

		vueTapis = new VueTapis();
		vueTapis.setLayout(new GridLayout(tapis.getHauteur(), tapis.getLargeur()));

		for (int y = 0; y < tapis.getHauteur(); y++) {
			for (int x = 0; x < tapis.getLargeur(); x++) {
				EmplacementCarte emCarte = new EmplacementCarte(new Position(x, y));
				emCarte.setCarte(tapis.getCarteAt(x, y));
				vueTapis.add(emCarte);
			}
		}
		

	}

	@Override
	public void visit(Tapis_Triangulaire tapis) {

	}

	public VueTapis getVueTapis() {
		return vueTapis;
	}

}
