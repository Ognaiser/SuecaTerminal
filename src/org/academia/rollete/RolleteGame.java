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

            showRound(number);

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

    private void showRound(int number) {

        for (RolleteClient client : players) {
            if (number == 0) {
                client.sayToPlayer("The Rolette stopped at: " +RoletteColors.GREEN + number + RoletteColors.BLACK);
                continue;
            }
            if (number % 2 == 0) {
                client.sayToPlayer("The Rolette stopped at: " + number );
                continue;
            } else {
                client.sayToPlayer("The Rolette stopped at: " +RoletteColors.RED + number + RoletteColors.BLACK);
                continue;
            }
        }
    }

    private void checkRound(RoletteColors color) {
        for (RolleteClient player :
                players) {
            if (player.getPlay() == color) {
                switch (color){
                    case RED:
                        player.addChips(player.getBet() * 2);
                        break;
                    case BLACK:
                        player.addChips(player.getBet()* 2 );
                        break;
                    case GREEN:
                        player.addChips(player.getBet() * 7);
                        break;
                }
                player.sayToPlayer("You won! Your chips are: " + player.getChips());
            }else {
                player.sayToPlayer("You lose!");
            }
        }
    }

    private void askPlay() {
        for (RolleteClient client : players) {
            client.askPlay();
        }
    }

    private void askOut() {

        List<RolleteClient> toRemove = new ArrayList<>();

        for (RolleteClient player : players) {
           if (player.askOut()){
               toRemove.add(player);
           }
        }

        removePlayer(toRemove);
    }

    public void removePlayer(List<RolleteClient> toRemove){
        for (RolleteClient player :
                toRemove) {
            players.remove(player);
            player.getBacktoChat();
        }
    }

    public void addPlayer(RolleteClient client) {
        players.add(client);
        welcomeMsg(client);
        notifyAll();
    }

    private void welcomeMsg(RolleteClient client) {
        client.sayToPlayer("");
        client.sayToPlayer("Welcome to Roulette!");
        client.sayToPlayer("");
    }
}
