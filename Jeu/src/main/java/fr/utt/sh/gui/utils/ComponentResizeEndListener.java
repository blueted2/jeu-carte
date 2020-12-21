package fr.utt.sh.gui.utils;

import java.awt.event.*;
import javax.swing.*;

// 

/**
 * Composent trouvé sur <a href=
 * "https://stackoverflow.com/questions/17338322/getting-the-last-resize-event-of-a-component">
 * Stackoverflow</a>. Cette classe agit comme un {@link ComponentAdapter}, mais
 * avec une fontionalité supplémentaire: detecter la fin du changement de la
 * taille d'un composent testant si il n'y a pas eut de changement dans les
 * dernieres n millisecondes.
 * 
 * @author grego
 *
 */
public abstract class ComponentResizeEndListener extends ComponentAdapter implements ActionListener {

	private final Timer timer;

	/**
	 * Créer un adapter detecter la fin du changement de taille avec un timeOut de 200 millisecondes.
	 */
	public ComponentResizeEndListener() {
		this(200);
	}

	/**
	 * Créer un adapter detecter la fin du changement de taille avec un timeOut de {@code delayMS} millisecondes.
	 * 
	 * @param delayMS La durée du timeOut.
	 */
	public ComponentResizeEndListener(int delayMS) {
		timer = new Timer(delayMS, this);
		timer.setRepeats(false);
		timer.setCoalesce(false);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		timer.restart();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		resizeTimedOut();
	}

	/**
	 * Methode appellé lorsque la classe detecte la fin du changement de taille.
	 */
	public abstract void resizeTimedOut();
}