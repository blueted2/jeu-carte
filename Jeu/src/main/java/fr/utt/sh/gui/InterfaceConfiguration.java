package fr.utt.sh.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Regles;
import fr.utt.sh.core.tapis.TypeTapis;

public class InterfaceConfiguration {

	JComboBox<TypeTapis> selectionTapis;
	JComboBox<String>    selectionNombreHumains;
	JComboBox<String>    selectionNombreBots;
	JComboBox<Regles>    selectionRegles;
	
	JButton boutonCommencer;
	

	private static InterfaceConfiguration instance;

	private JFrame frame;

	private ControlleurJeu cj;

	public static void begin() {
		if (instance != null)
			return;
		instance = new InterfaceConfiguration();
	}

	private InterfaceConfiguration() {
		cj = ControlleurJeu.getInstance();
		initialize();
		addListeners();
	}

	private void initialize() {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Container contentPane = frame.getContentPane();

		contentPane.setLayout(new FlowLayout());

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 300));


		contentPane.add(panel, BorderLayout.CENTER);

		GroupLayout layout = new GroupLayout(panel);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		panel.setLayout(layout);

		JLabel labelConfiguration = new JLabel("Configuration Jeu");
		JLabel labelTypeTapis = new JLabel("Type du Tapis");
		JLabel labelRegles = new JLabel("Regles du Jeu");
		JLabel labelHumains = new JLabel("Humains");
		JLabel labelBots = new JLabel("Bots");

		String[] nombres = { "0", "1", "2", "3" };

		selectionTapis         = new JComboBox<TypeTapis>(TypeTapis.values());
		selectionNombreHumains = new JComboBox<String>(nombres);
		selectionNombreBots    = new JComboBox<String>(nombres);
		selectionRegles        = new JComboBox<Regles>(Regles.values());
		
		boutonCommencer = new JButton("Commencer");

		labelHumains.setVerticalAlignment(JLabel.CENTER);
		labelHumains.setMaximumSize(new Dimension(1000, 20));
		
		labelBots.setVerticalAlignment(JLabel.CENTER);
		labelBots.setMaximumSize(new Dimension(1000, 20));

		
		selectionTapis.setMaximumSize(new Dimension(1000, 20));
		selectionRegles.setMaximumSize(new Dimension(1000, 20));
		selectionNombreHumains.setMaximumSize(new Dimension(1000, 20));
		selectionNombreBots.setMaximumSize(new Dimension(1000, 20));

		labelConfiguration.setFont(labelConfiguration.getFont().deriveFont(20f));
		
		boutonCommencer.setMaximumSize(new Dimension(300, 300));

		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(labelConfiguration)
				.addComponent(labelTypeTapis)
				.addComponent(selectionTapis)
				.addComponent(labelRegles)
				.addComponent(selectionRegles)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(labelHumains)
								.addComponent(labelBots)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(selectionNombreHumains)
								.addComponent(selectionNombreBots)
								)
						)
				.addComponent(boutonCommencer)
				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(labelConfiguration)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(labelTypeTapis)
				.addComponent(selectionTapis)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(labelRegles)
				.addComponent(selectionRegles)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup()

						.addComponent(selectionNombreHumains)
						.addComponent(labelHumains)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(selectionNombreBots)
						.addComponent(labelBots)		
						)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(boutonCommencer)
				);

		frame.pack();

		frame.setVisible(true);

	}
	
	private void addListeners() {
		boutonCommencer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Regles regles = (Regles) selectionRegles.getSelectedItem();
				TypeTapis typeTapis = (TypeTapis) selectionTapis.getSelectedItem();
				int nbHumains = selectionNombreHumains.getSelectedIndex();
				int nbBots = selectionNombreBots.getSelectedIndex();
				
				cj.commencerNouveauJeu(nbHumains, nbBots, regles, typeTapis, 2);
				frame.dispose();
			}
		});
	}
}
