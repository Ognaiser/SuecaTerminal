package org.academia.server.CommandHandler;

import org.acdemiadecodigo.Server;

import java.io.IOException;

/**
 * Created by codecadet on 06/07/2017.
 */
public class Kick implements Command {
    @Override
    public boolean runCmd(String msg, Server.ClientHandler clientHandler) throws IOException {
        System.out.println("kicking");

        String userToKick = msg.split(" ")[1];
        Server.serverKick( userToKick);
        return true;
    }
}
