package org.academia.server;

import org.academia.sueca.SuecaClient;
import org.academia.sueca.SuecaGame;

import java.util.LinkedList;

public class GameHandler {

    private LinkedList<SuecaClient> suecaList = new LinkedList<>();

    public void addSueca(Server.ClientHandler clientHandler) {

        suecaList.add(new SuecaClient(clientHandler.getClient().getSocket()));

        if (suecaList.size() == 4){
            SuecaGame game = new SuecaGame(suecaList);
            game.start();
            suecaList = new LinkedList<>();
        }
    }
}
