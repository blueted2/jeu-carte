package fr.utt.sh.console_ui;

import java.util.Observable;
import java.util.Observer;

public class ConsoleLineReader extends Observable {

	private static String[]          ligneConsole;
	private static ConsoleLineReader instance;

	public static ConsoleLineReader getInstance() {
		if (instance == null)
			instance = new ConsoleLineReader();
		return instance;
	}

	public static String[] getLigneConsole() {

		return ligneConsole;
	}

	public ConsoleLineReader() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					ligneConsole = Utils.getLigneSeparee();
					setChanged();
					notifyObservers();
				}
			}
		}.start();

	}

}
