package org.academia.games;

import org.academia.games.roulette.RoulettePlayer;
import org.academia.games.roulette.RouletteGame;
import org.academia.games.sueca.SuecaPlayer;
import org.academia.games.sueca.SuecaGame;
import org.academia.server.Server;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientDispatcher {

    private RouletteGame game = new RouletteGame();
    private ExecutorService pool = Executors.newFixedThreadPool(25);
    private LinkedList<SuecaPlayer> suecaPlayerList = new LinkedList<>();

    public void addToSuecaQueue(Server.ClientHandler clientHandler) {

        suecaPlayerList.add(new SuecaPlayer(clientHandler.getClient()));

        suecaPlayerList.getLast().sendMessage("Waiting !!!!");

        if (suecaPlayerList.size() == 4){

            SuecaGame game = new SuecaGame(suecaPlayerList);
            pool.submit(game);
            suecaPlayerList = new LinkedList<>();
        }
    }

    public void startRoulette(Server.ClientHandler client){

        game.addPlayer(new RoulettePlayer(client.getClient()));
    }
}
