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

        RouletteOptions number;


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

            number = RouletteOptions.values()[(int)(Math.random() * 36 + 1)];

            showRound(number);

            checkRound(number);

        }

    }

    private void showRound(RouletteOptions number) {

        for (RoulettePlayer client : players) {
                client.sayToPlayer("The Roulette stopped at: " + number.toString());
        }
    }

    private void checkRound(RouletteOptions number) {
        for (RoulettePlayer player : players) {

            //TODO: alterar estas condições para checkRound()

           // if (player.getPlay() == number) {
                switch (number) {
                    case ZERO:
                        player.addChips(player.getBet() * 36);
                        break;
                    case ONE:
                        player.addChips(player.getBet() * 36);
                        break;
                    case TWO:
                        player.addChips(player.getBet() * 36);
                        break;


                }
                player.sayToPlayer("You won! Your chips are: " + player.getChips());
            //} else {
                player.sayToPlayer("You lose!");
            //}
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
            if (player.askOut()) {
                toRemove.add(player);
            }
        }

        removePlayer(toRemove);
    }

    public void removePlayer(List<RoulettePlayer> toRemove) {
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
        client.sayToPlayer("\nWelcome to the Roulette!\n");
    }
}
