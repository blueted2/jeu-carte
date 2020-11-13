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
	/**
	 * Appelé quand le joueur doit jouer. Prend en parametre un {@code Joueur}. 
	 * @param joueur
	 * @return {@code true} si la strategy a pu etre effectuée, {@code false} sinon.
	 */
	public boolean execute(Joueur joueur);
}
