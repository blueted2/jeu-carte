package fr.utt.sh.gui.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Position;
import fr.utt.sh.gui.InterfaceJeu;

/**
 * La représentation graphique d'une position potentielle d'une carte.
 * 
 * @author grego
 *
 */
@SuppressWarnings("serial")
public class EmplacementCarte extends JButton {

	private Carte    carte;
	private Position position;

	private Image  originalImage;
	private Border originalBorder;

	/**
	 * Créer un {@code EmplacementCarte}, avec comme parametre la position que cet
	 * emplacement aurait sur le tapis. Mettre {@code null} si cet emplacement n'est
	 * pas sur un tapis.
	 * 
	 * @param position La position qu'aurait l'emplacement sur le tapis.
	 */
	public EmplacementCarte(Position position) {
		super();
		originalBorder = getBorder();
		this.position  = position;

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateImage();
			}
		});

	}

	/**
	 * Affectuer une {@link Carte} a cette position. Cela mettra egalement a jour
	 * l'icon de cet emplacement.
	 * 
	 * @param carte La {@link Carte} a cette position.
	 */
	public void setCarte(Carte carte) {
		this.carte = carte;
		if (carte == null)
			originalImage = null;

		originalImage = InterfaceJeu.imagesCartes.get(carte);
		updateImage();
	}

	private void updateImage() {
		if (carte == null) {
			setIcon(null);
			return;
		}

		Dimension dim = getSize();
		int       w   = Math.max(1, dim.width);
		int       h   = Math.max(1, dim.height);

		Image image = originalImage;

		Image newimg = image.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);

		ImageIcon imageIcon = new ImageIcon(newimg);

		setIcon(imageIcon);
		revalidate();
	}

	@Override
	public String toString() {
		return position.toString();
	}

	/**
	 * Obtenir la position que cet emplacement aurait sur le tapis.
	 * 
	 * @return La position de l'emplacement.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Obtenir la carte que represent cet emplacement.
	 * 
	 * @return La {@link Carte} de cet emplacement.
	 */
	public Carte getCarte() {
		return carte;
	}

	/**
	 * Activer/desactivé un effet de "selection" sur l'emplacement.
	 * 
	 * @param etat Si l'effet est active ou non.
	 */
	public void setSelectionee(boolean etat) {
		if (etat) {
			setBorder(new LineBorder(Color.red));
		} else {
			setBorder(originalBorder);
		}
	}

}
