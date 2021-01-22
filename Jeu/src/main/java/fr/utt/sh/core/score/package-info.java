/**
 * Ce package regroupe toutes les classes responsables du calul du score que
 * donnerait une certaine carte sur le tapis du jeu. Cette logique de calcul
 * du score est effectuée grâce au patron de conception Visitor/Visitable, dans
 * lequel les tapis "visitables" sont visités par un "visitor" ayant été passé
 * comme argument du constructeur de la carte pour laquelle le score est calculé. Le
 * résultat est ensuite accessible par un getter sur le visiteur. <br>
 * Seulement une implémentation existe actuelement, mais si nécessaire un autre
 * algorithme peut être implémenté sans grande difficulté.
 * 
 * @author grego
 *
 */
package fr.utt.sh.core.score;