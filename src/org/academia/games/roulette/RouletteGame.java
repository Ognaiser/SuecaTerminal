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

            number = (RouletteOptions) (Math.random() * 36 + 1);

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
                client.sayToPlayer("The Roulette stopped at: " + number + RouletteColors.BLACK);
                continue;
            }
            if (number % 2 == 0) {
                client.sayToPlayer("The Roulette stopped at: " + number);
                continue;
            } else {
                client.sayToPlayer("The Roulette stopped at: " + RouletteColors.RED + number + RouletteColors.BLACK);
                continue;
            }
        }
    }

    private void checkRound(RouletteOptions number) {
        for (RoulettePlayer player :
                players) {
            if (player.getPlay() == number) {
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
                    //TODO: ver getters do rouletteOptions para o switch
                }
                player.sayToPlayer("You won! Your chips are: " + player.getChips());
            } else {
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
