package fr.utt.sh.gui.utils;

import java.awt.event.*;
import javax.swing.*;

// https://stackoverflow.com/questions/17338322/getting-the-last-resize-event-of-a-component

public abstract class ComponentResizeEndListener extends ComponentAdapter implements ActionListener {

	private final Timer timer;

	public ComponentResizeEndListener() {
		this(200);
	}

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

	public abstract void resizeTimedOut();
}