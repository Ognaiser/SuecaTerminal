package org.academia.server;

import org.academia.rollete.RolleteClient;
import org.academia.rollete.RolleteGame;
import org.academia.sueca.SuecaClient;
import org.academia.sueca.SuecaGame;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameHandler {

    private RolleteGame game = new RolleteGame();
    private ExecutorService pool = Executors.newFixedThreadPool(25);
    private LinkedList<SuecaClient> suecaList = new LinkedList<>();

    public void addSueca(Server.ClientHandler clientHandler) {

        suecaList.add(new SuecaClient(clientHandler.getClient()));

        suecaList.getLast().sendMessage("Waiting !!!!");

        clientHandler.delete();
        clientHandler.disconnect();

        if (suecaList.size() == 4){
            SuecaGame game = new SuecaGame(suecaList);
            pool.submit(game);
            suecaList = new LinkedList<>();
        }
    }

    public void addRollete(Server.ClientHandler client){
        client.delete();
        client.disconnect();
        game.addPlayer(new RolleteClient(client.getClient()));
    }
}
