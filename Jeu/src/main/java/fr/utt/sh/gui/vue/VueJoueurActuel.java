package fr.utt.sh.gui.vue;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;
import fr.utt.sh.core.actions.ActionMainJoueur;
import fr.utt.sh.core.actions.NouveauJoueur;
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
	private JPanel           panelCarteDansMain;
	private JLabel           labelJoueurActuel;

	private CopyOnWriteArrayList<EmplacementCarte> emCartesDansMain;

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
		
		if(arg1 instanceof ActionMainJoueur) {
			updateComponentValues();
			return;
		}
		
		if(arg1 instanceof NouveauJoueur) {
			joueurActuel = cj.getJoueurActuel();
			updateComponentValues();
			return;
		}
		
		
//		if (arg0 instanceof ControlleurJeu) {
//			if (joueurActuel != null)
//				joueurActuel.deleteObserver(this);
//
//			joueurActuel = cj.getJoueurActuel();
//
//			// Au debut, le joueur actuel est nul, donc on ne peut pas ajouter l'observer
//			// directement. Il faut attendre que la methode passerAuJoueurSuivant soit
//			// effectué dans le controlleurJeu, ce qui informe la vue d'un nouveau joueur
//			// actuel.
//			joueurActuel.addObserver(this);
//
//			updateComponentValues();
//		} else if (arg0 instanceof Joueur) {
//			updateComponentValues();
//		}
	}

	private void initialize() {
		cj.addObserver(this);
		labelJoueurActuel = new JLabel();
		add(labelJoueurActuel);

		switch (cj.getRegles()) {
			case Standard:
				emCarteVictoire = new EmplacementCarte(null);
				emCartePiochee = new EmplacementCarte(null);

				add(emCarteVictoire);
				add(emCartePiochee);
				break;
			case Advanced:
			case Variante:	
				panelCarteDansMain = new JPanel();
				emCartesDansMain = new CopyOnWriteArrayList<EmplacementCarte>();

				add(panelCarteDansMain);
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + cj.getRegles());
		}

		setLayout(null);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateComponentBounds();
			}
		});
	}

	private void updateComponentBounds() {
		Dimension dimPanel = getSize();
		int       lPanel   = dimPanel.width;
		int       hPanel   = dimPanel.height;

		int hCarte = hPanel;
		int lCarte = (int) (hCarte / InterfaceJeu.RATIO_CARTE);
		
		labelJoueurActuel.setBounds(lCarte/2, 0, 2*lCarte, hCarte);
		labelJoueurActuel.setFont(labelJoueurActuel.getFont().deriveFont(20f));

		switch (cj.getRegles()) {

			case Standard:
				emCarteVictoire.setBounds(lPanel - 2 * lCarte, 0, lCarte, hCarte);
				emCartePiochee.setBounds((lPanel - lCarte) / 2, 0, lCarte, hCarte);
				break;
			case Advanced:
			case Variante:
				if (joueurActuel == null)
					return;
				int nbCartesDansMain = joueurActuel.getNombreCartesDansMain();
				int lCarteDansMains = nbCartesDansMain * lCarte;
				panelCarteDansMain.setBounds((lPanel - lCarteDansMains) / 2, 0, lCarteDansMains, hCarte);
				panelCarteDansMain.setLayout(new GridLayout(1, nbCartesDansMain));
				panelCarteDansMain.revalidate();

				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + cj.getRegles());
		}

	}

	/**
	 * Obtenir le panel regroupant les {@link EmplacementCarte} representant les
	 * cartes du joueur.
	 * 
	 * @return {@code JPanel}
	 */
	public JPanel getPanelCarteDansMain() {
		return panelCarteDansMain;
	}

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
	 * Obtenir la liste des {@link EmplacementCarte} representant la main du joueur.
	 * 
	 * @return {@code ArrayList<EmplacementCarte>}
	 */
	public CopyOnWriteArrayList<EmplacementCarte> getEmCartesDansMain() {
		return emCartesDansMain;
	}

	private void updateComponentValues() {
		labelJoueurActuel.setText(joueurActuel.toString());

		switch (cj.getRegles()) {

			case Standard:
				emCarteVictoire.setCarte(joueurActuel.getCarteVictoire());
				emCartePiochee.setCarte(joueurActuel.getCartePiochee());
				break;
			case Advanced:
			case Variante:
				// Supprimer tout les emplacements (plus facile que de trouver quel sont ceux
				// qui n'ont pas changé...)
				for (EmplacementCarte emCarteDansMain : emCartesDansMain) {
					panelCarteDansMain.remove(emCarteDansMain);
				}
				emCartesDansMain = new CopyOnWriteArrayList<EmplacementCarte>();

				for (Carte carte : joueurActuel.getCartesDansMain()) {
					EmplacementCarte emCarte = new EmplacementCarte(null);
					emCarte.setCarte(carte);
					emCartesDansMain.add(emCarte);
					panelCarteDansMain.add(emCarte);
				}
				updateComponentBounds();

				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + cj.getRegles());
		}

	}
}
