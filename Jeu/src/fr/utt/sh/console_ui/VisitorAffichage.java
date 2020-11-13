package fr.utt.sh.console_ui;

import fr.utt.sh.core.Carte;
import fr.utt.sh.core.Tapis_5x3;

public interface VisitorAffichage {
	public void visit(Tapis_5x3 tapis);
	public void visit(Carte carte);
	
}
