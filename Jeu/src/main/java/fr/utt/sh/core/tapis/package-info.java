/**
 * Ce package regroupe toutes les classes responsables pour le stockage et les
 * manipulations des différents tapis possibles du jeu. <br>
 * La classe abstraite {@link fr.utt.sh.core.tapis.Tapis} définit le
 * fonctionnement de base, auxquelles les différentes variantes ajoutent leurs
 * propres fonctionnalités.<br>
 * Certains tapis ont la possibilité de se déplacer (si une carte est placée sur
 * le bord et il reste de place de l'autre côté); ces tapis implémentent donc
 * l'interface {@link fr.utt.sh.core.tapis.Decalable}. <br>
 * Pour simplifier le choix du tapis lors du début du jeu, seulement quelques
 * implémentations (dont la taille est fixée dans leur constructeur) sont
 * accessibles, grâce a une énumération {@link fr.utt.sh.core.tapis.TypeTapis}.
 * 
 * @author grego
 *
 */
package fr.utt.sh.core.tapis;