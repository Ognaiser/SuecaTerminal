package org.academia.rollete;

import java.util.ArrayList;
import java.util.List;

public class RolleteGame implements Runnable {

    private List<RolleteClient> players = new ArrayList<>();
    private boolean isOver = false;


    @Override
    public void run() {
        start();
    }

    private void start() {

        int number;


        while (true) {

            askOut();

            if (players.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }

            askPlay();

            number = (int) (Math.random() * 36);

            if (number == 0) {
                checkRound(RoletteColors.GREEN);
                continue;
            }

            if (number % 2 == 0) {
                checkRound(RoletteColors.BLACK);
                continue;
            } else {
                checkRound(RoletteColors.RED);
                continue;
            }
        }

    }

    private void checkRound(RoletteColors colors) {

    }

    private void askPlay() {

        for (RolleteClient client : players) {

            client.sayToPlayer("Witch color do you want to bet?");
            client.sayToPlayer(RoletteColors.RED+"\u25A8" + " " + RoletteColors.GREEN+"\u25A8 "+ RoletteColors.BLACK+"\u25A8" );
            client.sayToPlayer("1 2 3");

        }
    }

    private void askOut() {

    }


    public void addPlayer(RolleteClient client) {
        players.add(client);
    }
}
