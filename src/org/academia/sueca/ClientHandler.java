package org.academia.sueca;

import java.util.List;

public class ClientHandler implements Runnable {

    private List<Card> hand;
    private String name;

    //Maby this on Run!
    public void askNick(){}

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public Card play(){
        return null;
    }

    public void uiUpdate(String msg){

    }

    @Override
    public void run() {

    }
}
