package org.academia.server.CommandHandler;

import org.acdemiadecodigo.Server;

/**
 * Created by codecadet on 06/07/2017.
 */
public class CommandManager implements Command {


    public Command getByName(String msg) {

        String firstWord = msg.split(" ")[0];

        if (firstWord.equals(":commands")) {
            return new Commands();
        }

        if (firstWord.equals(":pm")) {
            return new PrivateMessage();
        }

        if (firstWord.equals(":changeNick")) {
            return new ChangeNick();
        }

        if (firstWord.equals(":kick")) {
            return new Kick();
        }

        if (firstWord.equals(":list")) {
            return new List();
        }

        if (firstWord.equals(":exit")) {
            return new Exit();
        }

        return this;
    }

    @Override
    public boolean runCmd(String msg, Server.ClientHandler clientHandler) {

        return false;
    }
}