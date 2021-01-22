package fr.utt.sh.core.strategy;
/**
 * Classe interface de strategy. Lorsque le joueur veut jouer, elle appelle la
 * méthode {@link #execute}. La méthode {@link #execute} décide ensuite quelles
 * actions seront prises en fonction de l'état du joueur et du tapis.
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
	
	/**
	 * Demander à la strategy d'arrêter. 
	 */
	public void arreter();
}
