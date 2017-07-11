package org.academia.server.CommandHandler;

import org.acdemiadecodigo.Server;

import java.io.IOException;

/**
 * Created by codecadet on 06/07/2017.
 */
public class Exit implements Command {
    @Override
    public boolean runCmd(String msg, Server.ClientHandler clientHandler) throws IOException {
        System.out.println("exiting");
        clientHandler.close();

        return true;
    }
}
