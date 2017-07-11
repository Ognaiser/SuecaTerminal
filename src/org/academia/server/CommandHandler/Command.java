package org.academia.server.CommandHandler;

import org.acdemiadecodigo.Server;

import java.io.IOException;

/**
 * Created by codecadet on 06/07/2017.
 */
public interface Command {

    boolean runCmd(String msg, Server.ClientHandler clientHandler) throws IOException;

}
