package org.academia;

import org.academia.games.president.PCard;
import org.academia.games.president.PCardSuits;
import org.academia.games.president.PCardValues;

/**
 * Created by codecadet on 12/07/2017.
 */
public class Test {

    public static void main(String[] args) {
        //System.out.println(PCardSuits.JOKER.getSymbol());

        PCard card = new PCard(PCardSuits.CLUBS, PCardValues.TWO);


        System.out.println(PCardValues.valueOf("TEN").ordinal() >  card.getValue().ordinal());

    }
}
