/**
 * Ce package regoupe toutes les classes responsables pour le stockage et les
 * manipulations des differents tapis possibles du jeu. <br>
 * La classe abstraite {@link fr.utt.sh.core.tapis.Tapis} définit le
 * fonctionement de base, auxquelles les différentes variantes ajoutent leur
 * propres fonctionalités.<br>
 * Certains tapis ont la possibilité de se déplacer (si une carte est placée sur
 * le bord et il reste de place de l'autre coté); ces tapis implémentent donc
 * l'interface {@link fr.utt.sh.core.tapis.Decalable}. <br>
 * Pour simplifier le choix du tapis lors du début du jeu, seulement quelques
 * implémentations (dont la taille est fixé dans leur constructeur) sont
 * accéssibles, grace a une énumération {@link fr.utt.sh.core.tapis.TypeTapis}.
 * 
 * @author grego
 *
 */
package fr.utt.sh.core.tapis;