package fr.utt.sh.gui.controlleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.gui.EmplacementCarte;

public class ControlleurEmplacementCarte {

	private ControlleurJeu   cj;
	private EmplacementCarte emCarte;

	public ControlleurEmplacementCarte(EmplacementCarte emCarte) {
		cj = ControlleurJeu.getInstance();

		this.emCarte = emCarte;

		emCarte.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Clique sur: " + emCarte);
			}
		});
	}

}
