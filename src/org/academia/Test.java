package org.academia;

import org.academia.games.president.PresidentCard;
import org.academia.games.president.PresidentCards;
import org.academia.games.president.PresidentSuit;

/**
 * Created by codecadet on 12/07/2017.
 */
public class Test {

    public static void main(String[] args) {
        //System.out.println(PresidentSuit.JOKER.getSymbol());

        PresidentCard card = new PresidentCard(PresidentSuit.CLUBS,PresidentCards.TWO);


        System.out.println(PresidentCards.valueOf("TEN").ordinal() >  card.getValue().ordinal());

    }
}
