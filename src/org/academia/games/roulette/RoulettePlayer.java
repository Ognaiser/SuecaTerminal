package org.academia.games.roulette;

import org.academia.server.serverClient.ClientPOJO;
import org.academia.games.GameClient;

import java.io.IOException;

public class RoulettePlayer extends GameClient {

    private RouletteOptions play;
    private int bet;

    public RoulettePlayer(ClientPOJO client) {
        super(client);
    }

    public void sayToPlayer(String msg) {
        out.println(msg);
    }

    public RouletteOptions getPlay() {
        return play;
    }

    public int getBet() {
        return bet;
    }

    public void askPlay() {

        boolean validPlay = false;
        boolean validAmount = false;
        String bet = null;
        while (!validPlay) {

            out.println();
            out.println("Please chose your bet from the following options:");
            out.println(" Number 0  |  Number [1-36]  |  BLACK  |  RED  |  ODD  |  EVEN  |  UP [1-18] |  DOWN [19-36] | 1st Dozen [1-12] |  2nd Dozen [13-24] | 3rd Dozen [25-36]");
            out.println("    0              1              2        3       4       5          6              7                 8                   9                  10    ");

            try {
                bet = in.readLine();
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }


            validPlay = validatePlay(bet);


            if (!validPlay) {
                out.println("Invalid Play! Please type again!");
            }

        }

        while (!validAmount) {

            out.println();
            out.println("Your chips: " + getChips());
            out.println("How many chips you want to bet? ");

            try {
                validAmount = validateAmount(in.readLine());
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }

            if (!validPlay) {
                out.println("Invalid Amount! Please type again!");
            }
        }

    }

    private boolean validateAmount(String amount) {

        int number;

        try {
            number = Integer.parseInt(amount);
        } catch (NumberFormatException nfe) {
            return false;
        }

        if (getChips() < number) {
            return false;
        }

        removeChips(number);
        out.println(" You bet is : " + getChips() + " chips!");
        return true;
    }

    private boolean validatePlay(String play) {

        int number;

        try {
            number = Integer.parseInt(play);
        } catch (NumberFormatException nfe) {
            return false;
        }

        if (number != 1 && number != 2 && number != 3) {
            return false;
        }

        switch (number) {
            case 0:
                this.play = RouletteOptions.ZERO;
                break;
           //TODO: ver as 10 apostas possiveis no novo enum de apostas
            default:
                System.out.println("Something went wrong");
                System.exit(1);
                break;
        }

        return true;
    }

    public boolean askOut() {

        String option = null;
        
        while (true) {

            out.println("Do you want to continue? (y/n)");

            try {
                option = in.readLine();
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }

            if (option.equals("y")) {
                return true;
            }

            if (option.equals("n")) {
                return false;
            }

            out.println("Invalid answer! Type again!");
        }
    }
}
