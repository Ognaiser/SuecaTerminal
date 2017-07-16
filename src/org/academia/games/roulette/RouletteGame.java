package org.academia.games.roulette;

import java.util.ArrayList;
import java.util.List;

public class RouletteGame implements Runnable {

    //TODO: BUG: when you dont have chips to bet or you put more chips then you have game fricks out remove form the game and reset status
    //(dont let people with 0 chips keep playing)!

    private List<RoulettePlayer> players = new ArrayList<>();

    @Override
    public void run() {
        start();
    }

    private void start() {

        RouletteOptions number;

        while (true) {

            synchronized (this) {
                if (players.size() == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        System.err.println("Error: " + e.getMessage());
                        System.exit(1);
                    }
                }

                askPlay();

                number = RouletteOptions.values()[(int) (Math.random() * 36 + 1)];

                showRound(number);

                checkRound(number);

                askOut();

                try {
                    wait(10);
                } catch (InterruptedException e) {
                    System.err.println("Error: " + e.getMessage());
                    System.exit(1);
                }
            }


        }
    }

    private void showRound(RouletteOptions number) {

        for (RoulettePlayer client : players) {
            client.sayToPlayer("THE ROULETTE STOPPED AT:\n" + number.toString());
        }
    }

    private void checkRound(RouletteOptions number) {
        for (RoulettePlayer player : players) {

            if (player.getPlayNumber() != null) {
                if (player.getPlayNumber().equals(number)) {
                    player.addChips(player.getBetValue() * 36);
                    player.sayToPlayer("You won! Your chips are: " + player.getChips());
                    continue;
                }
                player.sayToPlayer("You lost! Now you have " + player.getChips());
                continue;
            }

            if (player.getPlay().equals(RouletteBets.BLACK) || player.getPlay().equals(RouletteBets.RED)) {
                if (player.getPlay().getName().equals(number.getColor())) {
                    System.out.println(player.getBetValue());
                    player.addChips(player.getBetValue() * 2);
                    player.sayToPlayer("You won! Your chips are: " + player.getChips());
                    continue;
                }
                player.sayToPlayer("You lost! Now you have " + player.getChips());
                continue;
            }

            if (player.getPlay().equals(RouletteBets.ODD) || player.getPlay().equals(RouletteBets.EVEN)) {
                if (player.getPlay().getName().equals(number.getOddOrEven())) {
                    player.addChips(player.getBetValue() * 2);
                    player.sayToPlayer("You won! Your chips are: " + player.getChips());
                    continue;
                }
                player.sayToPlayer("You lost! Now you have " + player.getChips());
                continue;
            }

            if (player.getPlay().equals(RouletteBets.UP) || player.getPlay().equals(RouletteBets.DOWN)) {
                if (player.getPlay().getName().equals(number.getUpOrDown())) {
                    player.addChips(player.getBetValue() * 2);
                    player.sayToPlayer("You won! Your chips are: " + player.getChips());
                    continue;
                }
                player.sayToPlayer("You lost! Now you have " + player.getChips());
                continue;
            }

            //  if (player.getPlay().equals(RouletteBets.DOZEN1) || player.getPlay().equals(RouletteBets.DOZEN2) || player.getPlay().equals(RouletteBets.DOZEN3)) {
            if (player.getPlay().getName().equals(number.getDozen())) {
                player.addChips(player.getBetValue() * 3);
                player.sayToPlayer("You won! Your chips are: " + player.getChips());
                continue;
            }
            player.sayToPlayer("You lost! Now you have " + player.getChips());
            //   }
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

        client.sayToPlayer("Waiting for Turn to end!");

        synchronized (this) {
            players.add(client);
            welcomeMsg(client);
            notifyAll();
        }
    }

    private void welcomeMsg(RoulettePlayer client) {
        client.sayToPlayer("\nWelcome to the Roulette!\n");
    }
}
