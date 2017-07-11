package org.academia.server;

import org.academia.sueca.SuecaClient;
import org.academia.sueca.SuecaGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.LinkedList;

public class Server {

    public static final int PORT = 8080;
    private ServerSocket ss;
    private LinkedList<SuecaClient> clients;

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
                clients.add(new SuecaClient(ss.accept()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        SuecaGame suecaGame = new SuecaGame(clients);
        suecaGame.start();
    }


    private class ClientHandler implements Runnable{

        private ClientPOJO client;
        private boolean isOver;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler() {

        }

        @Override
        public void run() {

        }
    }
}
