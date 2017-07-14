package org.academia.games.roulette;

/**
 * Created by miguelferreira on 13/07/17.
 */
public enum RouletteBets {

/*      Number 0  |
        Number [1-36]  |
        BLACK  |
        RED  |
        ODD  |
        EVEN  |
        UP [1-18] |
        DOWN [19-36] |
        1st Dozen [1-12] |
        2nd Dozen [13-24] |
        3rd Dozen [25-36]");    */

    ZERO("ZERO"),
    NUMBER(""),
    BLACK("BLACK"),
    RED("RED"),
    ODD("ODD"),
    EVEN("EVEN"),
    UP("UP"),
    DOWN("DOWN"),
    DOZEN1(""+1),
    DOZEN2(""+2),
    DOZEN3(""+3);

    String name;

    RouletteBets(String name) {
        this.name = name;
    }
}
