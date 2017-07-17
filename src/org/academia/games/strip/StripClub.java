package org.academia.games.strip;

import org.academia.server.Server;

import java.util.ArrayList;
import java.util.List;

public class StripClub implements Runnable {

    private List<Server.ClientHandler> spectators = new ArrayList<>();
    private int imageNumber = 0;

    @Override
    public void run() {
        synchronized (this) {
            while (true) {
                try {

                    if (imageNumber == 7){
                        removeAll();
                        imageNumber = 0;
                    }

                    if (spectators.size() == 0) {
                        wait();
                    }

                    showImage();

                    if (imageNumber<1) {
                        wait(40);
                    }

                    imageNumber++;

                    Thread.sleep(1500);

                } catch (InterruptedException e) {
                    System.err.println("Error: " + e.getMessage());
                    System.exit(1);
                }
            }
        }
    }

    private void removeAll() {

        List<Server.ClientHandler> toRemove = new ArrayList<>();

        for (Server.ClientHandler client : spectators) {
            toRemove.add(client);
        }

        for (Server.ClientHandler client :
                toRemove) {
            client.send("");
            client.send("Show is over!");
            spectators.remove(client);
            client.getBackToList();
        }


    }

    private void showImage() {
        for (Server.ClientHandler client :
                spectators) {
            client.send("");
            client.send(StripImages.values()[imageNumber].getShape());
        }
    }

    public void addPlayer(Server.ClientHandler clientHandler) {

        if (imageNumber > 1 ) {
            clientHandler.send("\nWaiting for current show to end!\n");
        }
        synchronized (this) {
            spectators.add(clientHandler);
            notifyAll();
        }
    }

}
