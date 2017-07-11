package org.academia.server.CommandHandler;

import org.acdemiadecodigo.Server;

/**
 * Created by codecadet on 06/07/2017.
 */
public class ChangeNick implements Command {
    @Override
    public boolean runCmd(String msg, Server.ClientHandler clientHandler) {

        System.out.println("changing nick");
        clientHandler.setName (msg.split(" ")[1]);
        return true;
    }
}
