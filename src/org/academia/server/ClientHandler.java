package org.academia.server;

import org.academia.server.CommandHandler.CommandManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler {

    private BufferedReader in;
    private PrintWriter out;
    private GameClient player;
    private CommandManager cmd;

    public ClientHandler(Socket socket) {

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error:" + e.getMessage());
            System.exit(1);
        }

        out.println("Hello my niggas");

    }

    private boolean handleMessage(String msg, Game game) throws IOException {

        cmd = new CommandManager();
        return cmd.getByName(msg).runCmd(msg, player ,game);
    }

}
