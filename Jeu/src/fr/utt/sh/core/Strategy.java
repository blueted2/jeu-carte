package fr.utt.sh.core;

/**
 * Classe interface de strategy. Lorsque le joueur veux jouer, il appelera la
 * methode {@code execute} avec lui meme comme parametre. La methode
 * {@code execute} decidera ensuite quelles actions seront prises en fonction de
 * l'etat du joueur et le tapis.
 * 
 * @author grego
 *
 */
public interface Strategy {
	public boolean execute(Joueur joueur);
}
