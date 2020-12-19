package fr.utt.sh.core.strategy;
/**
 * Classe interface de strategy. Lorsque le joueur veux jouer, elle appelle la
 * méthode {@link #execute}. La méthode {@link #execute} décide ensuite quelles
 * actions seront prises en fonction de l'etat du joueur et le tapis.
 * 
 * @author grego
 *
 */
public interface Strategy {
	/**
	 * Appelé quand le joueur doit jouer.
	 * 
	 * @return {@code true} si la strategy a pu être effectuée, {@code false} sinon.
	 */
	public boolean execute();
}
