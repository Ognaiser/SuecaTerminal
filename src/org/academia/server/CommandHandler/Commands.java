package org.academia.server.CommandHandler;

import org.acdemiadecodigo.Server;

import java.io.IOException;

/**
 * Created by codecadet on 06/07/2017.
 */
public class Commands implements Command {
    @Override
    public boolean runCmd(String msg, Server.ClientHandler clientHandler) throws IOException {

        System.out.println("displaying commands");

        clientHandler.send("\nThere are nice commands for tou to use:\n" +
                "-->:changeNick newUserName\n" +
                "-->:kick userName\n" +
                "-->:pm userName\n" +
                "-->:exit");
        return false;
    }
}
