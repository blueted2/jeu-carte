package fr.utt.sh.gui.vue;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.sh.core.ControlleurJeu;
import fr.utt.sh.core.Joueur;

public class VueScoresJoueurs extends JPanel implements Observer {
	
	private ArrayList<JLabel> labelsScores;
	private ControlleurJeu c;
	
	public VueScoresJoueurs() {
		c = ControlleurJeu.getInstance();
		labelsScores = new ArrayList<JLabel>();
		
		for(Joueur joueur : c.getJoueurs()) {
			JLabel label = new JLabel();
			labelsScores.add(label);
			add(label);
		}
		c.addObserver(this);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		updateComponentValues();
	}
	
	
	private void updateComponentValues() {
		ArrayList<Joueur> joueurs = c.getJoueurs();
		for(int i = 0; i<joueurs.size(); i++) {
			Joueur joueur = joueurs.get(i);
			labelsScores.get(i).setText(String.format("%s : %s", joueur.toString(), joueur.getScore()));
		}
	}
}
