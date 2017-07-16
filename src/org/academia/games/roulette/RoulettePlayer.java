package org.academia.games.roulette;

import org.academia.server.serverClient.ClientPOJO;
import org.academia.games.GameClient;

import java.io.IOException;

public class RoulettePlayer extends GameClient {

    private RouletteBets play;
    private int betValue;
    private RouletteOptions playNumber;

    public RoulettePlayer(ClientPOJO client) {
        super(client);
    }

    public void sayToPlayer(String msg) {
        out.println(msg);
    }

    public RouletteOptions getPlayNumber() {
        return playNumber;
    }

    public RouletteBets getPlay() {
        return play;
    }

    public int getBetValue() {
        return betValue;
    }

    public void askPlay() {

        boolean validPlay = false;
        boolean validAmount = false;
        String bet = null;
        while (!validPlay) {

            out.println("\nPlease chose your bet from the following options:");
            out.println(" Number 0  |  Number [1-36]  |  BLACK  |  RED  |  ODD  |  EVEN  |  DOWN [1-18] |  UP [19-36] | 1st Dozen [1-12] |  2nd Dozen [13-24] | 3rd Dozen [25-36]");
            out.println("    0              1              2        3       4       5          6              7                 8                   9                  10    ");

            try {
                bet = in.readLine();
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }

            playNumber = null;
            validPlay = validatePlay(bet);

            if (!validPlay) {
                out.println("Invalid Play! Please type again!");
            }

        }

        while (!validAmount) {

            out.println("\nYour chips: " + getChips());
            out.println("How many chips you want to bet? ");

            try {
                validAmount = validateAmount(in.readLine());
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }

            if (!validAmount) {
                out.println("Invalid Amount! Please type again!");
            }
        }

    }

    private boolean validateAmount(String amount) {

        try {
            betValue = Integer.parseInt(amount);
        } catch (NumberFormatException nfe) {
            return false;
        }


        if (getChips() < betValue) {
            return false;
        }

        removeChips(betValue);
        out.println("Your bet is : " + betValue + " chips!\n" + "JOGO FEITO, NADA MAIS...");
        return true;
    }

    private boolean validatePlay(String play) {

        int number;

        try {
            number = Integer.parseInt(play);
        } catch (NumberFormatException nfe) {
            return false;
        }

        if (number < 0 || number >10) {
            return false;
        }

        switch (number) {
            case 0:
                this.play = RouletteBets.ZERO;
                break;
            case 1:

                int numberBet =0;

                while(numberBet < 1 || numberBet > 36) {
                    out.println("Please enter the specific number you want to bet [between 1 and 36]");
                    try {
                        numberBet = Integer.parseInt(in.readLine());
                        handleNumberBet(numberBet);

                    } catch (IOException e) {
                        System.err.println("Error: " + e.getMessage());
                        System.exit(1);
                    }
                }

                this.play = RouletteBets.NUMBER;
                break;
            case 2:
                this.play = RouletteBets.BLACK;
                break;
            case 3:
                this.play = RouletteBets.RED;
                break;
            case 4:
                this.play = RouletteBets.ODD;
                break;
            case 5:
                this.play = RouletteBets.EVEN;
                break;
            case 6:
                this.play = RouletteBets.DOWN;
                break;
            case 7:
                this.play = RouletteBets.UP;
                break;
            case 8:
                this.play = RouletteBets.DOZEN1;
                break;
            case 9:
                this.play = RouletteBets.DOZEN2;
                break;
            case 10:
                this.play = RouletteBets.DOZEN3;
                break;
            default:
                System.out.println("Something went wrong");
                System.exit(1);
                break;
        }

        return true;
    }

    private void handleNumberBet(int numberBet) {
        if(numberBet < 1 || numberBet > 36){
            out.println("You entered an invalid number!");
            return;
        }
        this.playNumber = RouletteOptions.values()[numberBet];
    }


    public boolean askOut() {

        String option = null;

        while (true) {

            out.println("Do you want to exit? (y/n)");

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
