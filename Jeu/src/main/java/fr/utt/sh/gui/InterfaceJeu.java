package fr.utt.sh.gui;

import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.gui.utils.ComponentResizeEndListener;
import fr.utt.sh.gui.vue.VueJeu;

/**
 * Classe singleton créer une visualisation graphique de l'etat du jeu en
 * cours.
 * 
 * @author grego
 *
 */
public class InterfaceJeu {

	private JFrame              frame;
	private static InterfaceJeu instance;
	private VueJeu              vueJeu;

	/**
	 * Obtenir la partie visualisation du tapis.
	 * 
	 * @return {@link VueTapis}
	 */

	/**
	 * Le ratio entre la hauteur et la largeur des cartes. Cette valeur est
	 * constante.
	 */
	public static final double RATIO_CARTE = 1.5;

	/**
	 * Cet attribut contient un map entre les objets {@link Carte} et leurs image
	 * associés.
	 */
	public static HashMap<Carte, Image> imagesCartes;

	/**
	 * Commencer l'interface du jeu.
	 */
	public static void begin() {
		if (instance != null)
			return;
		instance = new InterfaceJeu();
	}

	/**
	 * Obtenir l'instance de l'interface graphique du jeu.
	 * 
	 * @return {@link InterfaceJeu}
	 */
	public static InterfaceJeu getInstance() {
		if (instance == null)
			begin();
		return instance;
	}

	private InterfaceJeu() {
		initialize();
	}

	private void initialize() {
		loadIcons();

		frame = new JFrame();
		frame.getContentPane().setLayout(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);

		vueJeu = new VueJeu();

		frame.getContentPane().add(vueJeu);

		frame.addComponentListener(new ComponentResizeEndListener(100) {
			@Override
			public void resizeTimedOut() {
				vueJeu.setBounds(frame.getContentPane().getBounds());
			}
		});
		frame.setSize(1024, 720);
	}

	private void loadIcons() {
		ControlleurJeu cj = ControlleurJeu.getInstance();
		imagesCartes = new HashMap<>();

		for (Carte carte : cj.getToutesCartes()) {

			String nomFichier = "/Cartes/";
			nomFichier += carte.getForme().name().toLowerCase() + "_";
			nomFichier += carte.getCouleur().name().toLowerCase() + "_";
			nomFichier += carte.getRemplissage().name().toLowerCase() + ".jpg";

			ImageIcon icon  = new ImageIcon(getClass().getResource(nomFichier));
			Image     image = icon.getImage();
			imagesCartes.put(carte, image);
		}
	}

	/**
	 * @return Le {@link JFrame} de l'interface graphique.
	 */
	public JFrame getFrame() {
		return frame;
	}

}
