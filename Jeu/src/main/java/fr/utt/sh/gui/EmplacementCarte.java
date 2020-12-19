package fr.utt.sh.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Position;
import fr.utt.sh.gui.utils.ComponentResizeEndListener;

public class EmplacementCarte extends JButton {

	private Carte    carte;
	private Position position;

	private Image originalImage;

	public EmplacementCarte(Position position) {
		super();
		this.position = position;

		addComponentListener(new ComponentResizeEndListener() {
			@Override
			public void resizeTimedOut() {
				updateImage();
			}
		});
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	public void setImage(Image image) {
		originalImage = image;
		updateImage();
	}

	public void updateImage() {
		Dimension dim = getSize();
		int       w   = Math.max(1, dim.width);
		int       h   = Math.max(1, dim.height);

		Image     image     = originalImage;                                              // transform
																							// it
		Image     newimg    = image.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH); // scale it
																							// the
																							// smooth
																							// way
		ImageIcon imageIcon = new ImageIcon(newimg);                                      // transform
																							// it back
		super.setIcon(imageIcon);
	}

	@Override
	public String toString() {
		return position.toString();
	}

	public Position getPosition() {
		return position;
	}

	public Carte getCarte() {
		return carte;
	}

}
