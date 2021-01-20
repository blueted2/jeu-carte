/**
 * Ce package regoupe toutes les classes responsables pour le choix des actions
 * des joueurs bots (et humains) grace au patron de conception "Strategy". Une
 * subtilité de cette implémentaion est que les {@link fr.utt.sh.core.Joueur}
 * humains ont egalement une {@link fr.utt.sh.core.strategy.Strategy}, mais a la
 * place de faire des décisions comme un bot, cette strategie dans au joueur
 * d'entrer ce qui souhait faire a la console. <br>
 * Pour * permettre un fonctionement "concurrant" entre l'interface graphique et
 * vue console, ces strategies implémentent {@link java.lang.Runnable},
 * permettant un fonctionement multi-thread des strategies.
 * 
 * @author grego
 *
 */
package fr.utt.sh.core.strategy;