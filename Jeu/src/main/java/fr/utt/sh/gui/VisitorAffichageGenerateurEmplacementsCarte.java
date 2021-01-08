package fr.utt.sh.gui;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fr.utt.sh.console_ui.VisitorAffichage;
import fr.utt.sh.core.Position;
import fr.utt.sh.core.tapis.TapisTri;
import fr.utt.sh.core.tapis.decalable.TapisRectDecalable;
import fr.utt.sh.core.tapis.TapisRect;
import fr.utt.sh.gui.vue.EmplacementCarte;
import fr.utt.sh.gui.vue.EmplacementCarteBord;
import fr.utt.sh.gui.vue.VueTapis;

/**
 * Une implémentation de {@link VisitorAffichage} qui generere la partie tapis
 * de l'interface graphique.
 * 
 * @author grego
 *
 */
public class VisitorAffichageGenerateurEmplacementsCarte implements VisitorAffichage {

	private VueTapis vueTapis;

	@Override
	public void visit(TapisRect tapis) {

		vueTapis = new VueTapis(tapis);
		vueTapis.setLayout(new GridLayout(tapis.getHauteur(), tapis.getLargeur()));

		for (int y = 0; y < tapis.getHauteur(); y++) {
			for (int x = 0; x < tapis.getLargeur(); x++) {
				JComponent emplacement;

				if (!tapis.positionLegale(x, y))
					emplacement = new JPanel();

				else if (tapis.positionSurTapis(x, y)) {
					EmplacementCarte emCarte = new EmplacementCarte(new Position(x, y));
					emCarte.setCarte(tapis.getCarteAt(x, y));
					emplacement = emCarte;
					vueTapis.addEmCarte(emCarte);
				} else {
					EmplacementCarte emCarte = new EmplacementCarteBord(new Position(x, y));
					emplacement = emCarte;
					vueTapis.addEmCarte(emCarte);
				}

				vueTapis.add(emplacement);

			}
		}
	}
	
	@Override
	public void visit(TapisRectDecalable tapis) {

		vueTapis = new VueTapis(tapis);
		vueTapis.setLayout(new GridLayout(tapis.getHauteur() + 2, tapis.getLargeur() + 2));

		for (int y = -1; y < tapis.getHauteur() + 1; y++) {
			for (int x = -1; x < tapis.getLargeur() + 1; x++) {
				JComponent emplacement;

				if (!tapis.positionLegale(x, y))
					emplacement = new JPanel();

				else if (tapis.positionSurTapis(x, y)) {
					EmplacementCarte emCarte = new EmplacementCarte(new Position(x, y));
					emCarte.setCarte(tapis.getCarteAt(x, y));
					emplacement = emCarte;
					vueTapis.addEmCarte(emCarte);
				} else {
					EmplacementCarte emCarte = new EmplacementCarteBord(new Position(x, y));
					emplacement = emCarte;
					vueTapis.addEmCarte(emCarte);
				}

				vueTapis.add(emplacement);

			}
		}
	}

	@Override
	public void visit(TapisTri tapis) {
		vueTapis = new VueTapis(tapis);
		vueTapis.setLayout(new GridLayout(tapis.getHauteur() + 2, tapis.getLargeur() + 2));

		for (int y = -1; y < tapis.getHauteur() + 1; y++) {
			for (int x = -1; x < tapis.getLargeur() + 1; x++) {
				JComponent emplacement;

				if (!tapis.positionLegale(x, y))
					emplacement = new JPanel();

				else if (tapis.positionSurTapis(x, y)) {
					EmplacementCarte emCarte = new EmplacementCarte(new Position(x, y));
					emCarte.setCarte(tapis.getCarteAt(x, y));
					emplacement = emCarte;
					vueTapis.addEmCarte(emCarte);
				} else {
					EmplacementCarte emCarte = new EmplacementCarteBord(new Position(x, y));
					emplacement = emCarte;
					vueTapis.addEmCarte(emCarte);
				}

				vueTapis.add(emplacement);

			}
		}
	}

	/**
	 * Obtenir la {@link VueTapis} une fois que le tapis a été visité.
	 * 
	 * @return La vue graphique du tapis.
	 */
	public VueTapis getVueTapis() {
		return vueTapis;
	}

}
