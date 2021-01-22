package fr.utt.sh.core.tapis;


/**
 * Un {@link Tapis} implémentant cette interface est un tapis dont les cartes
 * sont décalables, c'est-a-dire que si une carte est posée sur le bord, et qu'il
 * y a de la place de l'autre côté, toutes les cartes seront decalées.
 * 
 * @author grego
 *
 */
public interface Decalable {
	/**
	 * Décaler les cartes du tapis a gauche.
	 * 
	 * @return Si les cartes ont pu etre décalées.
	 */
	public boolean decalerAGauche();
	
	/**
	 * Décaler les cartes du tapis a droite.
	 * 
	 * @return Si les cartes ont pu etre décalées.
	 */
	public boolean decalerADroite();
	
	/**
	 * Décaler les cartes du tapis en haut.
	 * 
	 * @return Si les cartes ont pu etre décalées.
	 */
	public boolean decalerEnHaut();
	
	/**
	 * Décaler les cartes du tapis en bas.
	 * 
	 * @return Si les cartes ont pu etre décalées.
	 */
	public boolean decalerEnBas();
}
