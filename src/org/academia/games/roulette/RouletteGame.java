package org.academia.games.roulette;

import java.util.ArrayList;
import java.util.List;

public class RouletteGame implements Runnable {

    private List<RoulettePlayer> players = new ArrayList<>();
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
                checkRound(RouletteColors.GREEN);
                continue;
            }

            if (number % 2 == 0) {
                checkRound(RouletteColors.BLACK);
                continue;
            } else {
                checkRound(RouletteColors.RED);
                continue;
            }


        }

    }

    private void showRound(int number) {

        for (RoulettePlayer client : players) {
            if (number == 0) {
                client.sayToPlayer("The Rolette stopped at: " + RouletteColors.GREEN + number + RouletteColors.BLACK);
                continue;
            }//TODO: manel corrige esta merda
            if (number % 2 == 0) {
                client.sayToPlayer("The Rolette stopped at: " + number );
                continue;
            } else {
                client.sayToPlayer("The Rolette stopped at: " + RouletteColors.RED + number + RouletteColors.BLACK);
                continue;
            }
        }
    }

    private void checkRound(RouletteColors color) {
        for (RoulettePlayer player :
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
        for (RoulettePlayer client : players) {
            client.askPlay();
        }
    }

    private void askOut() {

        List<RoulettePlayer> toRemove = new ArrayList<>();

        for (RoulettePlayer player : players) {
           if (player.askOut()){
               toRemove.add(player);
           }
        }

        removePlayer(toRemove);
    }

    public void removePlayer(List<RoulettePlayer> toRemove){
        for (RoulettePlayer player :
                toRemove) {
            players.remove(player);
            player.getBacktoChat();
        }
    }

    public void addPlayer(RoulettePlayer client) {
        players.add(client);
        welcomeMsg(client);
        notifyAll();
    }

    private void welcomeMsg(RoulettePlayer client) {
        client.sayToPlayer("");
        client.sayToPlayer("Welcome to Roulette!");
        client.sayToPlayer("");
    }
}
