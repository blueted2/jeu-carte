package fr.utt.sh.console_ui;

import java.util.Observable;

/**
 * Cette classe singleton créer un thread qui attend que l'utilisateur ecrive
 * une ligne a la console, puis avec le patron Oberser Observable, alerte ses
 * observers.
 * 
 * @author grego
 *
 */
public class ConsoleLineReader extends Observable {

	private static String[]          ligneConsole;
	private static ConsoleLineReader instance;

	private ConsoleLineReader() {
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

	/**
	 * Obtenir l'instace de la classe. Necéssaire car un observer ne peut pas etre
	 * ajouté statiquement.
	 * 
	 * @return {@link ConsoleLineReader}
	 */
	public static ConsoleLineReader getInstance() {
		if (instance == null)
			instance = new ConsoleLineReader();
		return instance;
	}

	/**
	 * Obtenir la derniere ligne qui a été ecrite dans la console.
	 * 
	 * @return Un array de mots.
	 */
	public static String[] getLigneConsole() {

		return ligneConsole;
	}

}
