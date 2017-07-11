package org.academia.server;

import java.util.List;

public class CommandManager {

    private List<Server.ClientHandler> clientHandlers ;
    private GameHandler gameHandler = new GameHandler();

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
                gameHandler.addSueca(clientHandler);
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
    }


}
