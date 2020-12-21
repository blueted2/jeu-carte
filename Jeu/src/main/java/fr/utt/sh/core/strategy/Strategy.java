package fr.utt.sh.core.strategy;
/**
 * Classe interface de strategy. Lorsque le joueur veux jouer, elle appelle la
 * méthode {@link #execute}. La méthode {@link #execute} décide ensuite quelles
 * actions seront prises en fonction de l'etat du joueur et le tapis.
 * 
 * @author grego
 *
 */
public interface Strategy extends Runnable {
	/**
	 * Appelé quand le joueur doit jouer.
	 * 
	 * @throws InterruptedException JSP
	 */
	public void execute() throws InterruptedException;
	
	public void arreter();
}
