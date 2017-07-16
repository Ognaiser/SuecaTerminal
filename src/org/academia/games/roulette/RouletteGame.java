package org.academia.games.roulette;

import java.util.ArrayList;
import java.util.List;

public class RouletteGame implements Runnable {

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
            client.sayToPlayer("\nTHE ROULETTE STOPPED AT:\n" + number.toString());
        }
    }

    private void checkRound(RouletteOptions number) {
        for (RoulettePlayer player : players) {

            if (player.getPlayNumber() != null) {
                if (player.getPlayNumber().equals(number)) {
                    handleWinningBet(player,36);
                    continue;
                }
            }

            if (player.getPlay().equals(RouletteBets.BLACK) || player.getPlay().equals(RouletteBets.RED)) {
                if (player.getPlay().getName().equals(number.getColor())) {
                    handleWinningBet(player,2);
                    continue;
                }
            }

            if (player.getPlay().equals(RouletteBets.ODD) || player.getPlay().equals(RouletteBets.EVEN)) {
                if (player.getPlay().getName().equals(number.getOddOrEven())) {
                    handleWinningBet(player,2);
                    continue;
                }
            }

            if (player.getPlay().equals(RouletteBets.UP) || player.getPlay().equals(RouletteBets.DOWN)) {
                if (player.getPlay().getName().equals(number.getUpOrDown())) {
                    handleWinningBet(player,2);
                    continue;
                }
            }

            if (player.getPlay().getName().equals(number.getDozen())) {
                handleWinningBet(player,3);
                continue;
            }

            player.sayToPlayer("You lost! Now you have " + player.getChips());
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

            if (player.getChips() == 0 ){
                player.sayToPlayer("You have no chips get back to chat!");
                toRemove.add(player);
                continue;
            }

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

        if(client.getChips() == 0){
            client.sayToPlayer("You have no chips get back to chat!");
            client.getBacktoChat();
            return;
        }

        client.sayToPlayer("Waiting for Turn to end!");

        synchronized (this) {
            players.add(client);
            welcomeMsg(client);
            notifyAll();
        }
    }

    private void handleWinningBet(RoulettePlayer player, int multiple){
        player.addChips(player.getBetValue() * multiple);
        player.sayToPlayer("You won! Your chips are: " + player.getChips());
    }

    private void welcomeMsg(RoulettePlayer client) {
        client.sayToPlayer("\nWelcome to the Roulette!\n");
    }
}
