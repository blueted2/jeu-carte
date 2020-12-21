package fr.utt.sh.gui.vue;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;
import fr.utt.sh.gui.InterfaceJeu;

/**
 * Classe responsable pour la visualisation graphique des cartes du joueur
 * actuel. Cette classe emploie le pattron de conception Observer/Observable, en
 * ecoutant pour des changements dans l'objet {@link Joueur} du joueur actuel,
 * et des changements dans l'instance de {@link ControlleurJeu}
 * 
 * @author grego
 *
 */
@SuppressWarnings("serial")
public class VueJoueurActuel extends JPanel implements Observer {
	private ControlleurJeu   cj;
	private Joueur           joueurActuel;
	private EmplacementCarte emCarteVictoire;
	private EmplacementCarte emCartePiochee;

	/**
	 * @return {@link EmplacementCarte} de la carte victoire du joueur actuel.
	 */
	public EmplacementCarte getEmCarteVictoire() {
		return emCarteVictoire;
	}

	/**
	 * @return {@link EmplacementCarte} de la carte piochée du joueur actuel.
	 */
	public EmplacementCarte getEmCartePiochee() {
		return emCartePiochee;
	}

	/**
	 * Constructeur de la visualisation du joueur actuel.
	 */
	public VueJoueurActuel() {
		cj = ControlleurJeu.getInstance();
		setLayout(null);
		initialize();
		revalidate();

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof ControlleurJeu) {
			if (joueurActuel != null)
				joueurActuel.deleteObserver(this);

			joueurActuel = cj.getJoueurActuel();
			joueurActuel.addObserver(this);
			updateValeursCartes();
		} else if (arg0 instanceof Joueur) {
			updateValeursCartes();
		}
	}

	private void initialize() {
		cj.addObserver(this);
		joueurActuel = cj.getJoueurActuel();
//		joueurActuel.addObserver(this);

		emCarteVictoire = new EmplacementCarte(null);
		emCartePiochee  = new EmplacementCarte(null);
		add(emCarteVictoire);
		add(emCartePiochee);
		setLayout(null);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateBoundsCartes();
			}
		});
	}

	private void updateBoundsCartes() {
		Dimension dimPanel = getSize();
		int       lPanel   = dimPanel.width;
		int       hPanel   = dimPanel.height;

		int hCarte = hPanel;
		int lCarte = (int) (hCarte / InterfaceJeu.RATIO_CARTE);

		emCarteVictoire.setBounds(lPanel - 2 * lCarte, 0, lCarte, hCarte);
		emCartePiochee.setBounds((lPanel - lCarte) / 2, 0, lCarte, hCarte);
	}

	private void updateValeursCartes() {
		emCarteVictoire.setCarte(joueurActuel.getCarteVictoire());
		emCartePiochee.setCarte(joueurActuel.getCartePiochee());
	}
}
