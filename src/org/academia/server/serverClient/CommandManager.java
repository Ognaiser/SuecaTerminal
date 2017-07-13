package org.academia.server.serverClient;

import org.academia.games.ClientDispatcher;
import org.academia.server.Server;

import java.util.List;

public class CommandManager {

    private List<Server.ClientHandler> clientHandlers ;
    private ClientDispatcher clientDispatcher = new ClientDispatcher();

    public CommandManager(List<Server.ClientHandler> clientHandlers) {
        this.clientHandlers = clientHandlers;
    }

    public void handle(Server.ClientHandler clientHandler, String msg) {

        switch (msg){
            case "!help":
                list(clientHandler);
                break;
            case "!play sueca":
                clientHandler.disconnect();
                clientHandler.delete();
                clientDispatcher.addToSuecaQueue(clientHandler);
                break;
            case "!play roulette":
                clientHandler.delete();
                clientHandler.disconnect();
                clientDispatcher.startRoulette(clientHandler);
                break;
            case "!play president":
                clientHandler.delete();
                clientHandler.disconnect();
                clientDispatcher.addToPresidentQueue(clientHandler);
                break;
            default:
                defaultMsg(clientHandler);
                break;
        }
    }

    private void defaultMsg(Server.ClientHandler clientHandler) {

        clientHandler.send("Command not Found! type !help for help!");
    }

    private void list(Server.ClientHandler handler){

        handler.send("!help -> List all commands!");
        handler.send("!play sueca-> Play a game of sueca!");
        handler.send("!play president-> Play a game of president!");
    }


}
