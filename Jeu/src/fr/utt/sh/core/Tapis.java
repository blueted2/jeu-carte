package fr.utt.sh.core;

public abstract class Tapis implements VisitableAffichage{
	
	public abstract boolean echangerCartes(int x1, int y1, int x2, int y2);
	public abstract Carte getCarteAt(int x, int y);

	public abstract boolean poserCarte(Carte carte, int x, int y);
	
}
