package org.academia.games.roulette;

/**
 * Created by miguelferreira on 13/07/17.
 */
public enum RouletteBets {

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

    private String name;

    RouletteBets(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
