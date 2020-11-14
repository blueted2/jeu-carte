package fr.utt.sh.console_ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe utilitaire pour l'interacteur avec l'utilisateur avec la console. 
 * @author grego
 *
 */
public class Utils {
	/**
	 * Lire la console, et obtenir une liste de string separées par des whitespace.
	 * @return Une liste de {@code String}.
	 */
	public static String[] getLigneSeparee() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String string;
		
		try {
			String mots[];
			string = reader.readLine();
			mots = string.split("\s");
			return mots;
		} catch (Exception e) {
			return null;
			
		}
	}	
}
