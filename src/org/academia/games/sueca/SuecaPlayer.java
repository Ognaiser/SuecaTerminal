package org.academia.games.sueca;

import org.academia.server.serverClient.ClientPOJO;
import org.academia.games.GameClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SuecaPlayer extends GameClient {


    private List<SuecaCard> hand;
    private String name;
    private int score;
    private boolean cheated = false;
    private boolean isCommand = false;
    private String accusedPlayer;

    public SuecaPlayer(ClientPOJO client) {
        super(client);
    }

    public void showHand() {

        for (int line = 0; line < 7; line++) {
            for (int i = 0; i < hand.size(); i++) {
                out.print(hand.get(i).getHandRep().split(":")[line]);
                out.print(" ");
            }
            out.println();
        }

        for (int i = 1; i <= hand.size(); i++) {
            out.print("    " + i + "     ");
        }
        out.println();
        out.println("Your Hand is ^");
    }

    public void assignNick() {
        this.name = super.getName();
    }


    //TODO:REFACTOR PLAY !

    public SuecaCard play() {


        showHand();
        out.println("Please pick a card number:");
        int cardPlayed;

        try {

            String input = in.readLine();

            if (input.length() > 2) {
                checkCommand(input);
            }

            cardPlayed = Integer.parseInt(input);


            while (cardPlayed < 1 || cardPlayed > hand.size()) {

                out.println("That is not a valid card number. \nPlease insert a number between 1 and " + (hand.size()));
                cardPlayed = Integer.parseInt(in.readLine());

            }

            cardPlayed -= 1;

            return hand.remove(cardPlayed);


        } catch (IOException e) {

            System.err.println(e.getMessage());
            System.exit(1);

        }
        return null;
    }

    public void checkCommand(String input) {

        String[] words = input.split(" ");
        if (words[0].equals("!waived")) {
            isCommand = true;
        }
        accusedPlayer = words[1];
        //TODO MIGUEL verificar o nome no segundo elemento do array

    }


    public boolean isCommand() {
        return isCommand;
    }

    public void sendMessage(String msg) {

        out.println(msg);
    }

    public int getScore() {
        return score;
    }

    public void addScore(SuecaCard[] suecaCards) {
        //TODO: decide on where this logic should be
        for (int i = 0; i < suecaCards.length; i++) {
            score += suecaCards[i].getValue();
        }

    }

    public boolean hasSuit(SuecaSuit suecaSuit) {

        for (SuecaCard suecaCard : hand) {
            if (suecaCard.getSuecaSuit().equals(suecaSuit)) {
                return true;
            }
        }
        return false;
    }


    public String getName() {
        return name;
    }

    public List<SuecaCard> getHand() {
        return hand;
    }

    public void setHand(List<SuecaCard> hand) {
        this.hand = hand;
        sortHand();
    }

    public void hasCheated() {
        cheated = true;
    }

    public boolean isACheater() {
        return cheated;
    }

    private void sortHand() {

        if (this.hand instanceof LinkedList) {

            LinkedList<SuecaCard> orderedHand = (LinkedList<SuecaCard>) this.hand;

            for (int i = 0; i < SuecaSuit.values().length; i++) {
                for (int j = 0; j < orderedHand.size(); j++) {
                    if (orderedHand.get(j).getSuecaSuit() == SuecaSuit.values()[i]) {
                        orderedHand.addFirst(orderedHand.remove(j));
                    }
                }
            }

            for (int i = 0; i < orderedHand.size(); i++) {
                for (int j = i; j > 0; j--) {
                    if (j!= 0 && orderedHand.get(j).getSuecaSuit() == orderedHand.get(j - 1).getSuecaSuit() && orderedHand.get(j).getCardNumber().compareTo(orderedHand.get(j - 1).getCardNumber()) < 0) {
                        orderedHand.add(j - 1, orderedHand.remove(j));
                    } else {
                        break;
                    }
                }
            }

            this.hand = orderedHand;
        } else {
            System.out.println("Error while ordering hand!");
        }
    }

    public String getAccusedPlayer() {
        return accusedPlayer;
    }
}


/**
 * for (int i = 0; i < orderedHand.size(); i++) {
 * for (int j = i; j > 0; j--) {
 * if (j!=0 && orderedHand.get(j).getCardNumber().compareTo(orderedHand.get(j-1).getCardNumber()) > 0){
 * orderedHand.add(j-1,orderedHand.remove(j));
 * }else {
 * break;
 * }
 * }
 * }
 */