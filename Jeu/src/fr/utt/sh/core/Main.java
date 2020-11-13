package fr.utt.sh.core;

import fr.utt.sh.core.Carte.Remplissage;

import java.util.Scanner;

import fr.utt.sh.console_ui.VisitorAffichageString;
import fr.utt.sh.core.Carte.Couleur;

/**
 * 
 * 
 * @author grego
 *
 */
public class Main {

	public static void main(String[] args) {
//		ControlleurJeu controlleurJeu = ControlleurJeu.getInstance();
//		
//		controlleurJeu.commencerNouvellePartie(4);
		
		Tapis tapis = new Tapis_5x3();
		tapis.poserCarte(new Carte(Couleur.Rouge, Remplissage.Vide), 2, 2);
		
		
		System.out.println(VisitorAffichageString.getRepresentationString(tapis));
		tapis.echangerCartes(2, 2, 3, 4);
		
		System.out.print(VisitorAffichageString.getRepresentationString(tapis));
	}

}
