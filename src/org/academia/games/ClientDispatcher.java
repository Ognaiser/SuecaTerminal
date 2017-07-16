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
    private LinkedList<PresidentPlayer> presidentPlayerList= new LinkedList<>();

    public ClientDispatcher() {
        Thread rouletteGame = new Thread(this.rouletteGame);
        rouletteGame.start();
        Thread strip = new Thread(this.strip);
        strip.start();
    }

    public void addToSuecaQueue(Server.ClientHandler clientHandler) {

        suecaPlayerList.add(new SuecaPlayer(clientHandler.getClient()));

        suecaPlayerList.getLast().sendMessage("You left the lobby and entered SUECA GAME!");
        suecaPlayerList.getLast().sendMessage("Waiting for more players......");

        if (suecaPlayerList.size() == 4){

            SuecaGame game = new SuecaGame(suecaPlayerList);
            pool.submit(game);
            suecaPlayerList = new LinkedList<>();
        }
    }

    public void startRoulette(Server.ClientHandler client){
        rouletteGame.addPlayer(new RoulettePlayer(client.getClient()));
    }

    public void enterClub(Server.ClientHandler clientHandler){
        strip.addPlayer(clientHandler);
    }

    public void addToPresidentQueue(Server.ClientHandler clientHandler) {

        presidentPlayerList.add(new PresidentPlayer(clientHandler.getClient()));

        presidentPlayerList.getLast().sendMessage("Waiting for more players ......");

        if (presidentPlayerList.size() == 3){
            PresidentGame game = new PresidentGame(presidentPlayerList);
            pool.submit(game);
            suecaPlayerList = new LinkedList<>();
        }
    }
}
