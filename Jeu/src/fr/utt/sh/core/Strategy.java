package fr.utt.sh.core;

/**
 * Classe interface de strategy. Lorsque le joueur veux jouer, elle appelle la
 * méthode {@link #execute} avec lui même comme paramètre. La méthode
 * {@link #execute} décide ensuite quelles actions seront prises en fonction de
 * l'etat du joueur et le tapis.
 * 
 * @author grego
 *
 */
public interface Strategy {
	/**
	 * Appelé quand le joueur doit jouer. Prend en paramètre un {@link Joueur}. 
	 * @param joueur Le {@link Joueur} auquel cette strategy va s'appliquer. 
	 * @return {@code true} si la strategy a pu être effectuée, {@code false} sinon.
	 */
	public boolean execute(Joueur joueur);
}
