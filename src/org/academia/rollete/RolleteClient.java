package org.academia.rollete;

import org.academia.server.ClientPOJO;
import org.academia.server.GameClient;

import java.io.IOException;

public class RolleteClient extends GameClient {

    private int play;

    public RolleteClient(ClientPOJO client) {
        super(client);
    }

    public void sayToPlayer(String msg) {
        out.println(msg);
    }

    public String getInputFromPlayer() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public int getPlay() {
        return play;
    }

    public void askPlay() {

        boolean validPlay = false;

        while (!validPlay) {
            out.println("Witch color do you want to bet?");
            out.println(RoletteColors.RED + "\u25A8" + " " + RoletteColors.GREEN + "\u25A8 " + RoletteColors.BLACK + "\u25A8");
            out.println("1 2 3");
            try {
                validPlay = validatePlay(in.readLine());
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    private boolean validatePlay(String play){
        return false;
    }
}
