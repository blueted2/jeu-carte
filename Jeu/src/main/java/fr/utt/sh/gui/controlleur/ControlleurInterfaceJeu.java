package fr.utt.sh.gui.controlleur;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.concurrent.CopyOnWriteArrayList;

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

	private InterfaceJeu interfaceJeu;

	private EmplacementCarte emCarteSelectionee;
	private boolean          carteSelectioneeEstSurTapis;

	/**
	 * Constructeur pour le controlleu. Prend en argument l'{@link InterfaceJeu}
	 * pour laquel ce controlleur est responsable.
	 * 
	 * @param interfaceJeu L'{@link InterfaceJeu} du controlleur.
	 */
	public ControlleurInterfaceJeu(InterfaceJeu interfaceJeu) {
		this.interfaceJeu = interfaceJeu;
		cj                = ControlleurJeu.getInstance();

		addButtonListeners();
	}

	private void addButtonListeners() {
		EmplacementCarte emCartePiochee = interfaceJeu.getVueJoueurActuel().getEmCartePiochee();

		// Listener pour le bouton piocher une carte.
		interfaceJeu.getBoutonPioche().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!cj.getJoueurActuel().isHumain())
					return;
				cj.joueurActuelPiocheCarte();
			}
		});

		// Listeners pour les cartes dans du tapis.
		interfaceJeu.getVueTapis().getEmplacementsCartes().forEach((emCarteTapis) -> {
			emCarteTapis.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (!cj.getJoueurActuel().isHumain())
						return;
					if (emCarteSelectionee == emCarteTapis) {
						selectionerEmCarte(null);
						return;
					}

					if (emCarteSelectionee != null) {
						if (carteSelectioneeEstSurTapis) {
							int x1 = emCarteSelectionee.getPosition().getX();
							int y1 = emCarteSelectionee.getPosition().getY();
							int x2 = emCarteTapis.getPosition().getX();
							int y2 = emCarteTapis.getPosition().getY();

							cj.joueurActuelDeplaceCarte(x1, y1, x2, y2);
							selectionerEmCarte(null);
							return;
						}
					}

					switch (cj.getRegles()) {
						case Standard:
							if (emCarteSelectionee == emCartePiochee) {
								int x = emCarteTapis.getPosition().getX();
								int y = emCarteTapis.getPosition().getY();

								cj.joueurActuelPoseCartePiochee(x, y);
								selectionerEmCarte(null);
								return;
							}

							break;
						case Advanced:
						case Variante:
							CopyOnWriteArrayList<EmplacementCarte> emCartesDansMain = interfaceJeu.getVueJoueurActuel()
									.getEmCartesDansMain();
							if (emCartesDansMain.contains(emCarteSelectionee)) {
								int x = emCarteTapis.getPosition().getX();
								int y = emCarteTapis.getPosition().getY();

								cj.joueurActuelPoseCarteDansMain(emCarteSelectionee.getCarte(), x, y);
								selectionerEmCarte(null);
								return;
							}

							break;
						default:
							throw new IllegalArgumentException("Unexpected value: " + cj.getRegles());

					}

					if (emCarteTapis.getCarte() != null) {
						selectionerEmCarte(emCarteTapis);
						carteSelectioneeEstSurTapis = true;
					} else
						selectionerEmCarte(null);

					return;

				}
			});
		});

		// Listener pour le bouton fin de tour.
		interfaceJeu.getBoutonFinTour().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!cj.getJoueurActuel().isHumain())
					return;

				cj.terminerTourJoueurActuel();

			}
		});

		switch (cj.getRegles()) {
			case Standard:

				// Listener pour la carte piochee.
				emCartePiochee.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (!cj.getJoueurActuel().isHumain())
							return;
						if (emCarteSelectionee == emCartePiochee) {
							selectionerEmCarte(null);
							return;
						}

						if (emCartePiochee.getCarte() == null)
							selectionerEmCarte(null);
						else
							selectionerEmCarte(emCartePiochee);
					}

				});

				break;
			case Advanced:
			case Variante:
				// Listeners pour les cartes dans la main ud joueur. Les listeners sont ajouté quand les composants de la vueJoueurActuel sont ajoutés. 
				interfaceJeu.getVueJoueurActuel().getPanelCarteDansMain().addContainerListener(new ContainerAdapter() {
					@Override
					public void componentAdded(ContainerEvent e) {
						Component c = e.getChild();
						if (!(c instanceof EmplacementCarte))
							return;

						EmplacementCarte emCarte = (EmplacementCarte) c;
						emCarte.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								if (!cj.getJoueurActuel().isHumain())
									return;
								if (emCarteSelectionee == emCarte) {
									selectionerEmCarte(null);
									return;
								}

								if (emCarte.getCarte() == null)
									selectionerEmCarte(null);
								else {
									selectionerEmCarte(emCarte);
								}
							}
						});

					}
				});
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + cj.getRegles());

		}
	}

	// Selectionner une nouvelle carte.
	private void selectionerEmCarte(EmplacementCarte emCarte) {
		carteSelectioneeEstSurTapis = false;
		if (emCarteSelectionee != null)
			emCarteSelectionee.setSelectionee(false); // Enlever l'effet de selection de la carte selectionée
														// précédente.

		emCarteSelectionee = emCarte;
		if (emCarte != null)
			emCarte.setSelectionee(true); // Ajouter l'effet de selection a la nouvelle carte.
	}

}
