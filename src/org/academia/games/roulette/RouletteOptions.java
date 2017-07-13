package org.academia.games.roulette;

/**
 * Created by codecadet on 13/07/2017.
 */
public enum RouletteOptions {

    /**
     *@param value refers to the number value
     *@param color refers to the color RED or BLACK, the zero is the only GREEN
     *@param oddOrEven if the value is odd #1, if the value is even #2, the zero is #0
     *@param upOrDown refers to the first #1 or second #2 half of values, the zero is #0
     *@param dozen refers to the first #1, second #2 or third #3 dozen
     */
    //TODO: fazer novo enum para as 10 apostas diferentes

    ZERO(0, "GREEN", 0, "ZERO", 0),
    ONE(1, "RED", 1, "DOWN", 1),
    TWO(2, "BLACK", 2, "DOWN", 1),
    THREE(3, "RED", 1, "DOWN", 1),
    FOUR(4, "BLACK", 2, "DOWN", 1),
    FIVE(5, "RED", 1, "DOWN", 1),
    SIX(6, "BLACK", 2, "DOWN", 1),
    SEVEN(7, "RED", 1, "DOWN", 1),
    EIGHT(8, "BLACK", 2, "DOWN", 1),
    NINE(9, "RED", 1, "DOWN", 1),
    TEN(10, "BLACK", 2, "DOWN", 1),
    ELEVEN(11, "BLACK", 1, "DOWN", 1),
    TWELVE(12, "RED", 2, "DOWN", 1),
    THIRTEEN(13, "BLACK", 1, "DOWN", 2),
    FOURTEEN(14, "RED", 2, "DOWN", 2),
    FIFTEEN(15, "BLACK", 1, "DOWN", 2),
    SIXTEEN(16, "RED", 2, "DOWN", 2),
    SEVENTEEN(17, "BLACK", 1, "DOWN", 2),
    EIGHTEEN(18, "RED", 2, "DOWN", 2),
    NINETEEN(19, "RED", 1, "UP", 2),
    TWENTY(20, "BLACK", 2, "UP", 2),
    TWENTYONE(21, "RED", 1, "UP", 2),
    TWENTYTWO(22, "BLACK", 2, "UP", 2),
    TWENTYTHREE(23, "RED", 1, "UP", 2),
    TWENTYFOUR(24, "BLACK", 2, "UP", 2),
    TWENTYFIVE(25, "RED", 1, "UP", 3),
    TWENTYSIX(26, "BLACK", 2, "UP", 3),
    TWENTYSEVEN(27, "RED", 1, "UP", 3),
    TWENTYEIGHT(28, "BLACK", 2, "UP", 3),
    TWENTYNINE(29, "BLACK", 1, "UP", 3),
    THIRTY(30, "RED", 2, "UP", 3),
    THIRTYONE(31, "BLACK", 1, "UP", 3),
    THIRTYTWO(32, "RED", 2, "UP", 3),
    THIRTYTHREE(33, "BLACK", 1, "UP", 3),
    THIRTYFOUR(34, "RED", 2, "UP", 3),
    THIRTYFIVE(35, "BLACK", 1, "UP", 3),
    THIRTYSIX(36, "RED", 2, "UP", 3);

    private int value;
    private String color;
    private int oddOrEven;
    private String upOrDown;
    private int dozen;

    RouletteOptions(int value, String color, int oddOrEven, String upOrDown, int dozen) {

        this.value = value;
        this.color = color;
        this.oddOrEven = oddOrEven;
        this.upOrDown = upOrDown;
        this.dozen = dozen;

    }

    public int getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public int getOddOrEven() {
        return oddOrEven;
    }

    public String getUpOrDown() {
        return upOrDown;
    }

    public int getDozen() {
        return dozen;
    }
}
