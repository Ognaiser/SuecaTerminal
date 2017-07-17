package org.academia.games;

import org.academia.games.president.PresidentGame;
import org.academia.games.president.PresidentPlayer;
import org.academia.games.roulette.RoulettePlayer;
import org.academia.games.roulette.RouletteGame;
import org.academia.games.strip.StripClub;
import org.academia.games.sueca.SuecaPlayer;
import org.academia.games.sueca.SuecaGame;
import org.academia.server.Server;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientDispatcher {

    private RouletteGame rouletteGame = new RouletteGame();
    private StripClub strip = new StripClub();
    private ExecutorService pool = Executors.newFixedThreadPool(25);
    private LinkedList<SuecaPlayer> suecaPlayerList = new LinkedList<>();
    private LinkedList<PresidentPlayer> presidentPlayerList = new LinkedList<>();

    public ClientDispatcher() {
        Thread rouletteGame = new Thread(this.rouletteGame);
        rouletteGame.start();
        Thread strip = new Thread(this.strip);
        strip.start();
    }

    public void addToSuecaQueue(Server.ClientHandler clientHandler) {

        if (clientHandler.isOnQueue()) {
            clientHandler.send("You are already on Queue!");
            return;
        }

        clientHandler.setOnQueue(true);
        clientHandler.setGameClient(new SuecaPlayer(clientHandler.getClient()));

        suecaPlayerList.add((SuecaPlayer) clientHandler.getGameClient());

        clientHandler.send("Waiting for more players to play Sueca......");

        if (suecaPlayerList.size() == 4) {

            for (GameClient client : suecaPlayerList) {
                //client.removeFromLobby();
                client.removeFromQueue();
            }

            SuecaGame game = new SuecaGame(suecaPlayerList);
            pool.submit(game);
            suecaPlayerList = new LinkedList<>();
        }
    }

    public void removeFromSueca(Server.ClientHandler handler) {

        if (!handler.isOnQueue()) {
            handler.send("Not on queue!");
            return;
        }

        if (handler.getGameClient() instanceof SuecaPlayer) {
            suecaPlayerList.remove(handler.getGameClient());
            handler.send("You left Queue!");
            handler.setOnQueue(false);
        } else {
            handler.send("You are not on Queue For Sueca!");
        }
    }

    public void startRoulette(Server.ClientHandler client) {
        rouletteGame.addPlayer(new RoulettePlayer(client.getClient()));
    }

    public void enterClub(Server.ClientHandler clientHandler) {
        strip.addPlayer(clientHandler);
    }

    public void addToPresidentQueue(Server.ClientHandler clientHandler) {

        if (clientHandler.isOnQueue()) {
            clientHandler.send("You are already on Queue!");
            return;
        }


        clientHandler.setOnQueue(true);
        clientHandler.setGameClient(new PresidentPlayer(clientHandler.getClient()));

        presidentPlayerList.add((PresidentPlayer) clientHandler.getGameClient());

        presidentPlayerList.getLast().sendMessage("Waiting for more players ......");

        if (presidentPlayerList.size() == 4) {

            for (PresidentPlayer client : presidentPlayerList) {
                //client.removeFromLobby();
                client.removeFromQueue();
            }

            PresidentGame game = new PresidentGame(presidentPlayerList);
            pool.submit(game);
            suecaPlayerList = new LinkedList<>();
        }
    }

    public void removeFromPresident(Server.ClientHandler handler) {

        if (!handler.isOnQueue()) {
            handler.send("Not on queue!");
            return;
        }

        if (handler.getGameClient() instanceof PresidentPlayer) {
            suecaPlayerList.remove(handler.getGameClient());
            handler.send("You left Queue!");
            handler.setOnQueue(false);
        } else {
            handler.send("You are not on Queue For President!");
        }

    }
}
