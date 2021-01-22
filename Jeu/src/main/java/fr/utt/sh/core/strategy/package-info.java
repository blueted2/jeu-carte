/**
 * Ce package regroupe toutes les classes responsables pour le choix des actions
 * des joueurs bots (et humains) grâce au patron de conception "Strategy". Une
 * subtilité de cette implémentation est que les {@link fr.utt.sh.core.Joueur}
 * humains ont également une {@link fr.utt.sh.core.strategy.Strategy}, mais à la
 * place de faire des décisions comme un bot, cette strategie demande au joueur
 * d'entrer ce qu'il souhaite faire à la console / via l'interface graphique. <br>
 * Pour permettre un fonctionnement "concurrent" entre l'interface graphique et
 * la vue console, ces stratégies implémentent {@link java.lang.Runnable},
 * permettant un fonctionnement multi-thread des stratégies.
 * 
 * @author grego
 *
 */
package fr.utt.sh.core.strategy;