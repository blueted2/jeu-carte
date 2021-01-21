/**
 * Ce package regoupe toutes les classes responsables pour caluler le score que
 * donnerait une certaine carte sur le tapis du jeu. Cette logique pour calculer
 * le score est effectué grace au patron de conception Visitor/Visitable, dans
 * laquelle les tapis "visitables" sont visités par un "visitor" ayant été passé
 * comme argument de constructeur la carte pour laquelle le score calculé. Le
 * résultat est ensuite accessible par un getter sur le visiteur. <br>
 * Seulement une implémentation existe actuelement, mais si necessaire un autre
 * algorithme peut etre implémenté sans grande difficulté.
 * 
 * @author grego
 *
 */
package fr.utt.sh.core.score;