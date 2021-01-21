package fr.utt.sh.gui.vue;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;
import fr.utt.sh.core.actions.FinJeu;
import fr.utt.sh.core.actions.FinPartie;
import fr.utt.sh.core.actions.NouveauJoueur;
import fr.utt.sh.core.tapis.Tapis;
import fr.utt.sh.gui.GenerateurVueTapis;
import fr.utt.sh.gui.controlleur.ControlleurInterfaceJeu;

/**
 * Une représentation graphique du jeu actuel. <br>
 * Nécessite que {@link ControlleurJeu} soit intialisé.
 * 
 * @author grego
 *
 */
@SuppressWarnings("serial")
public class VueJeu extends JPanel implements Observer {
	private VueTapis         vueTapis;
	private VueJoueurActuel  vueJoueurActuel;
	private VueScoresJoueurs vueScoresJoueurs;
	private JButton          boutonPioche;
	private JButton          boutonFinTour;

	private ControlleurJeu cj;
	private Tapis          tapis;

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
	 * Constructeur pour {@link VueJeu}.
	 */
	public VueJeu() {
		cj    = ControlleurJeu.getInstance();
		tapis = cj.getTapis();
		initialize();
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
	 * Obtenir le bouton de fin de tour.
	 * 
	 * @return {@link JButton}
	 */
	public JButton getBoutonFinTour() {
		return boutonFinTour;
	}

	private void initialize() {
		cj.addObserver(this);

		vueTapis         = GenerateurVueTapis.generate(tapis);
		vueJoueurActuel  = new VueJoueurActuel();
		vueScoresJoueurs = new VueScoresJoueurs();
		boutonPioche     = new JButton("Piocher");
		boutonFinTour    = new JButton("Finir Tour");

		setLayout(null);

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				updatePositionsComposents();
			};
		});

		add(vueTapis);
		add(vueJoueurActuel);
		add(vueScoresJoueurs);
		add(boutonPioche);
		add(boutonFinTour);

		new ControlleurInterfaceJeu(this);

		switch (cj.getRegles()) {
			case Standard:
			case Advanced:
				break;
			case Variante:
				boutonPioche.setVisible(false);
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + cj.getRegles());
		}

		updatePositionsComposents();
	}

	private void updatePositionsComposents() {
		double proportionTapis   = .7;
		double proportionJoueur  = .2;
		double proportionPioche  = .2;
		double proportionFinTour = .2;

		Dimension dim    = getSize();
		int       lFrame = dim.width;
		int       hFrame = dim.height;

		int xVueTapis = (int) (lFrame * (1 - proportionTapis) / 2);
		int yVueTapis = 0;
		int lVueTapis = (int) (lFrame * proportionTapis);
		int hVueTapis = (int) (hFrame * proportionTapis);

		vueTapis.setBounds(xVueTapis, yVueTapis, lVueTapis, hVueTapis);

		int hVueJoueur = (int) (hFrame * proportionJoueur);

		vueJoueurActuel.setBounds(0, hFrame - hVueJoueur, lFrame, hVueJoueur);

		int hPioche = (int) (hFrame * proportionPioche);
		int lPioche = (int) (hFrame * proportionPioche / RATIO_CARTE);

		boutonPioche.setBounds(lFrame - lPioche, (hFrame - hPioche) / 2, lPioche, hPioche);

		int hFinTour = (int) (hFrame * proportionFinTour);
		int lFinTour = (int) (hFrame * proportionFinTour);

		boutonFinTour.setBounds(lFrame - lFinTour, 0, lFinTour, hFinTour);

		vueScoresJoueurs.setBounds(0, 100, lFinTour, 100);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof NouveauJoueur) {
			NouveauJoueur nouveauJoueur = (NouveauJoueur) arg1;
			if (nouveauJoueur.getJoueur().isHumain()) {
				boutonFinTour.setVisible(true);
				boutonPioche.setVisible(cj.ilResteDesCartes());
			} else {
				boutonFinTour.setVisible(false);
				boutonPioche.setVisible(false);
			}
		}

		else if (arg1 instanceof FinPartie) {
			new Thread() {
				public void run() {
//					JFrame frame = InterfaceJeu.getInstance().getFrame();
					Joueur gagnant = cj.getGagnant();

					JOptionPane.showMessageDialog(null, "<html>Fin du jeu <br>" + gagnant.toString() + " a gangné avec "
							+ gagnant.getScore() + " points" + "</html>");
				};
			}.start();
		} else if (arg1 instanceof FinJeu) {
			new Thread() {
				public void run() {
//					JFrame frame = InterfaceJeu.getInstance().getFrame();

					JOptionPane.showMessageDialog(null,
							"<html>Fin de la partie !<br> Cliquez sur OK pour passer a la suivante. </html>");

					cj.commencerNouvellePartie();

				};
			}.start();
		}

	}
}
