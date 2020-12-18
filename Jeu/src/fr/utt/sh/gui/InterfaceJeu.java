package fr.utt.sh.gui;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Cette class regroupe la logique pour l'affiche et l'interaction avec
 * l'utilisateur grace aux libraries Swing.
 * 
 * @author grego
 *
 */
public class InterfaceJeu {

	static InterfaceJeu instance;

	private JFrame frameJeu;

	InterfaceJeu() {
		frameJeu = new JFrame();
		frameJeu.setVisible(true);// making the frame visible
		
		JButton b = new JButton("click");// creating instance of JButton
		b.setBounds(130, 100, 100, 40);// x axis, y axis, width, height
		frameJeu.add(b);
		frameJeu.setSize(400,500);//400 width and 500 height  
		frameJeu.setLayout(null);//using no layout managers  
		
		
	}

	/**
	 * Getter static pour l'instance du singleton {@code InterfaceJeu}. Si une
	 * instance n'exsite par encore, une sera créée.
	 * 
	 * @return L'instance singleton de {@code InterfaceJeu}
	 */
	public static InterfaceJeu getInstance() {
		if (instance == null)
			instance = new InterfaceJeu();

		return instance;
	}

}
