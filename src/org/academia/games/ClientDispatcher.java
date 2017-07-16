package org.academia.games;

import org.academia.games.president.PresidentGame;
import org.academia.games.president.PresidentPlayer;
import org.academia.games.roulette.RoulettePlayer;
import org.academia.games.roulette.RouletteGame;
import org.academia.games.sueca.SuecaPlayer;
import org.academia.games.sueca.SuecaGame;
import org.academia.server.Server;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientDispatcher {

    private RouletteGame rouletteGame = new RouletteGame();
    private ExecutorService pool = Executors.newFixedThreadPool(25);
    private LinkedList<SuecaPlayer> suecaPlayerList = new LinkedList<>();
    private LinkedList<PresidentPlayer> presidentPlayerList= new LinkedList<>();

    public ClientDispatcher() {
        Thread rouletteGame = new Thread(this.rouletteGame);
        rouletteGame.start();
    }

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

        rouletteGame.addPlayer(new RoulettePlayer(client.getClient()));
    }

    public void addToPresidentQueue(Server.ClientHandler clientHandler) {

        presidentPlayerList.add(new PresidentPlayer(clientHandler.getClient()));

        presidentPlayerList.getLast().sendMessage("Waiting !!!!");

        if (presidentPlayerList.size() == 3){
            System.out.println("enough players");
            PresidentGame game = new PresidentGame(presidentPlayerList);
            pool.submit(game);
            suecaPlayerList = new LinkedList<>();
        }
    }
}
