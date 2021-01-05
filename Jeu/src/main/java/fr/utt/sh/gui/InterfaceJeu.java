package fr.utt.sh.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.tapis.Tapis;
import fr.utt.sh.gui.controlleur.ControlleurInterfaceJeu;
import fr.utt.sh.gui.utils.ComponentResizeEndListener;
import fr.utt.sh.gui.vue.VueJoueurActuel;
import fr.utt.sh.gui.vue.VueScoresJoueurs;
import fr.utt.sh.gui.vue.VueTapis;

/**
 * Visualisation graphique de l'etat du jeu en cours.
 * 
 * @author grego
 *
 */
public class InterfaceJeu {

	private VueTapis         vueTapis;
	private VueJoueurActuel  vueJoueurActuel;
	private VueScoresJoueurs vueScoresJoueurs;
	private JButton          boutonPioche;
	private JButton          boutonFinTour;

	private JFrame              frame;
	private static InterfaceJeu instance;
	private ControlleurJeu      cj;
	private Tapis               tapis;

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
	 * Cette attribut contient un map entre les objets {@link Carte} et leur image
	 * associé.
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

	private InterfaceJeu() {

		cj    = ControlleurJeu.getInstance();
		tapis = cj.getTapis();
		initialize();
	}

	/**
	 * Obtenir l'instance de l'interface jeu.
	 * 
	 * @return {@link InterfaceJeu}
	 */
	public InterfaceJeu getInstance() {
		return instance;
	}

	/**
	 * Obtenir la partie visualisation du tapis.
	 * 
	 * @return {@link VueTapis}
	 */
	public VueTapis getVueTapis() {
		return vueTapis;
	}

	/**
	 * Obtenir la partie visualisation du joueur actuel.
	 * 
	 * @return {@link VueJoueurActuel}
	 */
	public VueJoueurActuel getVueJoueurActuel() {
		return vueJoueurActuel;
	}

	/**
	 * Obtenir le bouton pour piocher une carte.
	 * 
	 * @return Un {@link JButton}
	 */
	public JButton getBoutonPioche() {
		return boutonPioche;
	}

	/**
	 * Obtenir le frame principal de l'interface.
	 * 
	 * @return {@link JFrame}
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Obtenir le bouton de fin de tour.
	 * 
	 * @return {@link JButton}
	 */
	public JButton getBoutonFinTour() {
		return boutonFinTour;
	}

	private void initialize() {
		loadIcons();

		vueTapis         = GenerateurVueTapis.generate(tapis);
		vueJoueurActuel  = new VueJoueurActuel();
		vueScoresJoueurs = new VueScoresJoueurs();
		boutonPioche     = new JButton("Piocher");
		boutonFinTour    = new JButton("Finir Tour");

		frame = new JFrame();
		frame.getContentPane().setLayout(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
		frame.setSize(1024, 720);

		tapis.addObserver(vueTapis);

		frame.addComponentListener(new ComponentResizeEndListener(100) {
			@Override
			public void resizeTimedOut() {
				updatePositionsComposents();
			}
		});

		frame.getContentPane().add(vueTapis);
		frame.getContentPane().add(vueJoueurActuel);
		frame.getContentPane().add(vueScoresJoueurs);
		frame.getContentPane().add(boutonPioche);
		frame.getContentPane().add(boutonFinTour);

		new ControlleurInterfaceJeu(this);

		updatePositionsComposents();
	}

	private void updatePositionsComposents() {
		double proportionTapis   = .8;
		double proportionJoueur  = .2;
		double proportionPioche  = .2;
		double proportionFinTour = .2;

		Dimension dim    = frame.getContentPane().getSize();
		int       lFrame = dim.width;
		int       hFrame = dim.height;

		int nbCartesLargeur = tapis.getLargeur() + 2; // +2 pour les bords
		int nbCartesHauteur = tapis.getHauteur() + 2;

		double ratioVueTapis = (double) nbCartesHauteur / (double) nbCartesLargeur * RATIO_CARTE;

		int hVueTapis;
		int lVueTapis;

		if (hFrame * proportionTapis / ratioVueTapis < lFrame * proportionTapis) {
			hVueTapis = (int) (hFrame * proportionTapis);
			lVueTapis = (int) (hFrame * proportionTapis / ratioVueTapis);
		} else {
			hVueTapis = (int) (lFrame * proportionTapis * ratioVueTapis);
			lVueTapis = (int) (lFrame * proportionTapis);
		}

		vueTapis.setBounds((lFrame - lVueTapis) / 2, 0, lVueTapis, hVueTapis);

		int hVueJoueur = (int) (hFrame * proportionJoueur);

		vueJoueurActuel.setBounds(0, hFrame - hVueJoueur, lFrame, hVueJoueur);
		
		vueScoresJoueurs.setBounds(0, 100, 100, 100);

		int hPioche = (int) (hFrame * proportionPioche);
		int lPioche = (int) (hFrame * proportionPioche / RATIO_CARTE);

		boutonPioche.setBounds(lFrame - lPioche, (hFrame - hPioche) / 2, lPioche, hPioche);

		int hFinTour = (int) (hFrame * proportionFinTour);
		int lFinTour = (int) (hFrame * proportionFinTour);

		boutonFinTour.setBounds(lFrame - lFinTour, 0, lFinTour, hFinTour);

		frame.revalidate();

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

}
