package fr.utt.sh.gui.vue;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Position;
import fr.utt.sh.core.tapis.Tapis;
import fr.utt.sh.gui.InterfaceJeu;

/**
 * La visualisation du tapis du jeu. Cette classe emploie le patron de
 * conception Oberser/Observable afin de detecter lorsqu'un {@link Tapis} change
 * d'etat.
 * 
 * @author grego
 *
 */
@SuppressWarnings({ "serial" })
public class VueTapis extends JPanel implements Observer {

	private ArrayList<EmplacementCarte> emCartes;

	/**
	 * @return Les emplacements des cartes de cette visualisation du tapis.
	 */
	public ArrayList<EmplacementCarte> getEmplacementsCartes() {
		return emCartes;
	}

	/**
	 * Constructeur pour la visualisation du tapis. <br>
	 * Remarque: Ce constructeur n'est pas appelé directement dans
	 * {@link InterfaceJeu}, a la place un visitor puis un generateur sont utilisé
	 * afin de "generer" l'instance de la visualisation.
	 */
	public VueTapis() {
		super();
		emCartes = new ArrayList<EmplacementCarte>();
	}

	/**
	 * Ajouter un {@link EmplacementCarte} a la liste des emplacements cartes. Une
	 * liste séparée de ces emplacement est utilisé afin de plus facilement afectuer
	 * des actions sur ces emplacements.
	 * 
	 * @param emCarte {@link EmplacementCarte} a ajouter.
	 */
	public void addEmCarte(EmplacementCarte emCarte) {
		emCartes.add(emCarte);
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		if (!(arg0 instanceof Tapis))
			return;

		Tapis tapis = (Tapis) arg0;

		updateEmplacementsCarte(tapis);
		revalidate();
	}

	private void updateEmplacementsCarte(Tapis tapis) {
		boolean debutPartie = tapis.estVide();

		for (EmplacementCarte emCarte : emCartes) {
			Position posCarte = emCarte.getPosition();
			Carte    carte    = tapis.getCarteAt(posCarte);

			if (emCarte.getCarte() != carte)
				emCarte.setCarte(carte);

			boolean carteEstVisible;

			if (debutPartie) {
				carteEstVisible = !(emCarte instanceof EmplacementCarteBord);
			} else {
				int x = emCarte.getPosition().getX();
				int y = emCarte.getPosition().getY();

				carteEstVisible = tapis.positionAVoisins(x, y) || emCarte.getCarte() != null;

			}
			emCarte.setVisible(carteEstVisible);

		}
	}

}
