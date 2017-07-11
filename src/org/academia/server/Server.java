package org.academia.server;

import org.academia.sueca.SuecaGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

public class Server {

    public static final int PORT = 8080;
    private ServerSocket ss;
    private LinkedList<ClientHandler> clients;

    public void init(){

        try {
            ss = new ServerSocket(PORT);
            clients = new LinkedList<>();
        } catch (IOException e) {
            System.err.println("Error on the creation the server! " + e.getMessage());
            System.exit(1);
        }

    }

    public void start(){


        while (clients.size() <4){

            try {
                clients.add(new ClientHandler(ss.accept()));
                System.out.println(clients.size());
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        SuecaGame suecaGame = new SuecaGame(clients);
        suecaGame.start();
    }


}
