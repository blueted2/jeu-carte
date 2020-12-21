package fr.utt.sh.gui.controlleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.gui.InterfaceJeu;
import fr.utt.sh.gui.vue.EmplacementCarte;

/**
 * Cette classe est le controlleur pour une {@link InterfaceJeu}. Elle ajoute
 * les listeners sur les emplacements des cartes sur le tapis, dans la main du
 * joueur..., afin de permettre aux actions effectués sur l'interface graphique
 * d'avoir un effet sur le jeu.
 * 
 * @author grego
 *
 */
public class ControlleurInterfaceJeu {

	private ControlleurJeu cj;

	private EmplacementCarte carteSelectionee;
	private boolean          carteSelectioneeEstSurTapis;

	/**
	 * Constructeur pour le controlleu. Prend en argument l'{@link InterfaceJeu}
	 * pour laquel ce controlleur est responsable.
	 * 
	 * @param i L'{@link InterfaceJeu} du controlleur.
	 */
	public ControlleurInterfaceJeu(InterfaceJeu i) {
		cj = ControlleurJeu.getInstance();

		// Listener pour le bouton piocher une carte.
		i.getBoutonPioche().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!cj.getJoueurActuel().isHumain())
					return;
				cj.joueurActuelPiocheCarte();
			}
		});

		// Listener pour la carte piochee.
		EmplacementCarte emCartePiochee = i.getVueJoueurActuel().getEmCartePiochee();
		emCartePiochee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!cj.getJoueurActuel().isHumain())
					return;
				if (carteSelectionee == emCartePiochee) {
					selectionerEmCarte(null);
					return;
				}

				if (emCartePiochee.getCarte() == null)
					selectionerEmCarte(null);
				else
					selectionerEmCarte(emCartePiochee);
			}

		});

		// Listeners pour les cartes dans du tapis.
		i.getVueTapis().getEmplacementsCartes().forEach((emCarte) -> {
			emCarte.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (!cj.getJoueurActuel().isHumain())
						return;
					if (carteSelectionee == emCarte) {
						selectionerEmCarte(null);
						return;
					}

					if (carteSelectionee != null) {
						if (carteSelectioneeEstSurTapis) {
							int x1 = carteSelectionee.getPosition().getX();
							int y1 = carteSelectionee.getPosition().getY();
							int x2 = emCarte.getPosition().getX();
							int y2 = emCarte.getPosition().getY();

							cj.joueurActuelDeplaceCarte(x1, y1, x2, y2);
							selectionerEmCarte(null);

						} else {
							if (carteSelectionee == emCartePiochee) {
								int x = emCarte.getPosition().getX();
								int y = emCarte.getPosition().getY();

								cj.joueurActuelPoseCartePiochee(x, y);
								selectionerEmCarte(null);
							}

						}
						return;
					}

					if (emCarte.getCarte() != null) {
						selectionerEmCarte(emCarte);
						carteSelectioneeEstSurTapis = true;
					} else
						selectionerEmCarte(null);

				}
			});
		});

		// Listener pour le bouton fin de tour.
		i.getBoutonFinTour().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!cj.getJoueurActuel().isHumain())
					return;
				cj.terminerTourJoueurActuel();
			}
		});

	}

	// Selectionner une nouvelle carte.
	private void selectionerEmCarte(EmplacementCarte emCarte) {
		carteSelectioneeEstSurTapis = false;
		if (carteSelectionee != null)
			carteSelectionee.setSelectionee(false); // Enlever l'effet de selection de la carte selectionée précédente.

		carteSelectionee = emCarte;
		if (emCarte != null)
			emCarte.setSelectionee(true); // Ajouter l'effet de selection a la nouvelle carte.
	}

}
