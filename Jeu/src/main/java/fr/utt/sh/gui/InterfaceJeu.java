package fr.utt.sh.gui;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JFrame;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.tapis.Tapis;
import fr.utt.sh.gui.controlleur.ControlleurEmplacementCarte;

public class InterfaceJeu {

	private VueTapis vueTapis;
	private JFrame frame;

	private ControlleurJeu cj;

	public InterfaceJeu(Tapis tapis) {
		
		int l = tapis.getLargeur();
		int h = tapis.getHauteur();
		
		frame = new JFrame();
		frame.setSize(l * 100, h * 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(null);
		
		frame.setVisible(true);
		
		cj = ControlleurJeu.getInstance();

		vueTapis = GenerateurVueTapis.generate(tapis);
		
		
		vueTapis.setSize(l * 100, h * 150);
		tapis.addObserver(vueTapis);
		
		frame.getContentPane().add(vueTapis);

		for (Component comp : vueTapis.getComponents()) {
			if (comp instanceof EmplacementCarte) {
				EmplacementCarte emCarte = (EmplacementCarte) comp;
				new ControlleurEmplacementCarte(emCarte);
			}
		}
		
		
	}

}
