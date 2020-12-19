package fr.utt.sh.gui;

import java.awt.Component;
import java.awt.Image;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Position;
import fr.utt.sh.core.tapis.Tapis;

@SuppressWarnings("deprecation")
public class VueTapis extends JPanel implements Observer {

	private HashMap<Carte, Image> images;

	public VueTapis() {
		super();
		loadIcons();

	}

	private void loadIcons() {
		ControlleurJeu cj = ControlleurJeu.getInstance();
		images = new HashMap<>();

		for (Carte carte : cj.getToutesCartes()) {

			String nomFichier = "/Cartes/";
			nomFichier += carte.getForme().name().toLowerCase() + "_";
			nomFichier += carte.getCouleur().name().toLowerCase() + "_";
			nomFichier += carte.getRemplissage().name().toLowerCase() + ".jpg";

			ImageIcon icon  = new ImageIcon(getClass().getResource(nomFichier));
			Image     image = icon.getImage();
			images.put(carte, image);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		if (!(arg0 instanceof Tapis))
			return;

		Tapis tapis = (Tapis) arg0;
		updateEmplacementsCarte(tapis);
	}

	private void updateEmplacementsCarte(Tapis tapis) {
		for (Component comp : getComponents()) {
			if (comp instanceof EmplacementCarte) {
				EmplacementCarte emCarte  = (EmplacementCarte) comp;
				Position         posCarte = emCarte.getPosition();
				Carte            carte    = tapis.getCarteAt(posCarte);

				if (emCarte.getCarte() == carte)
					continue;

				emCarte.setCarte(carte);

				if (carte == null)
					continue;

				Image image = images.get(carte);
				emCarte.setImage(image);
			}
		}
	}

}
