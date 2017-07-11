package org.academia.server.CommandHandler;

import org.academia.server.ClientHandler;
import org.academia.server.Game;
import org.academia.server.GameClient;


import java.io.IOException;

/**
 * Created by codecadet on 06/07/2017.
 */
public interface Command {

    boolean runCmd(String msg, GameClient gameClient, Game game) throws IOException;

}
