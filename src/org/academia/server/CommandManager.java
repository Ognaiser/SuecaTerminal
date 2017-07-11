package org.academia.server;

import java.util.List;

public class CommandManager {

    private List<Server.ClientHandler> clientHandlers ;

    public CommandManager(List<Server.ClientHandler> clientHandlers) {
        this.clientHandlers = clientHandlers;
    }

    public void handle(Server.ClientHandler clientHandler, String msg) {

        switch (msg){
            case "!help":
                list(clientHandler);
                break;
        }
    }

    private void list(Server.ClientHandler handler){
        handler.send("!help -> List all commands!");
    }


}
