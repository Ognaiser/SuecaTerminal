package org.academia.sueca;

import org.academia.server.ClientPOJO;
import org.academia.server.GameClient;
import java.io.IOException;
import java.util.List;

public class SuecaClient extends GameClient {

    private List<Card> hand;
    private String name;
    private int score;
    private boolean cheated = false;
    private boolean isCommand;

    public SuecaClient(ClientPOJO client) {
        super(client);
        out.println("Hello my niggas");
    }

    public void showHand() {

        for (int line = 0; line < 7; line++) {
            for (int i = 0; i < hand.size(); i++) {
                out.print(hand.get(i).getHandRep().split(":")[line]);
                out.print("  ");
            }
            out.println();
        }

        for (int i = 1; i <= hand.size(); i++) {
            out.print("    " + i + "     ");
        }
        out.println();
    }

    public void assingNick() {
        this.name = super.getName();
    }

    public Card play() {


        showHand();
        out.println("Please pick a card number and number of cards to play EXAMPLE(K 2) to play 2 kings:");
        int cardPlayed;

        try {

            String input = in.readLine();

            if (input.length() > 1) {
                checkCommand(input);
            }

            cardPlayed = Integer.parseInt(input);

            while (cardPlayed < 1 || cardPlayed > hand.size()) {

                out.println("That is not a valid card . \nPlease insert a number between 1 and " + (hand.size()));
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
        //TODO MIGUEL verificar o nome no segundo elemento do array

    }


    public boolean isCommand(){
        return isCommand;
    }

    public void sendMessage(String msg) {

        out.println(msg);
    }

    public int getScore() {
        return score;
    }

    public void addScore(Card[] cards) {

        for (int i = 0; i < cards.length; i++) {
            score += cards[i].getValue();
        }

    }

    public boolean hasSuit(Suit suit) {

        for (Card card : hand) {
            if (card.getSuit().equals(suit)) {
                return true;
            }
        }
        return false;
    }


    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void hasCheated() {
        cheated = true;
    }

    public boolean isACheater() {
        return cheated;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

}
