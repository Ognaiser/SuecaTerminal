package org.academia.server;

import org.academia.sueca.SuecaClient;
import org.academia.sueca.SuecaGame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static final int PORT = 8080;
    private ServerSocket ss;
    private List<ClientHandler> clientHandlers;
    private CommandManager commandManager;
    //TODO: TO remove!
    private LinkedList<SuecaClient> clients;

    public void init() {

        try {
            ss = new ServerSocket(PORT);
            clientHandlers = new ArrayList<>();
            commandManager = new CommandManager(clientHandlers);

            //TODO: to remove!
            clients = new LinkedList<>();
        } catch (IOException e) {
            System.err.println("Error on the creation the server! " + e.getMessage());
            System.exit(1);
        }

    }

    public void start2(){

        ExecutorService pool = Executors.newFixedThreadPool(25);

        while (ss.isBound()){

            try {

                ClientHandler clientHandler = new ClientHandler(ss.accept());
                clientHandlers.add(clientHandler);
                pool.submit(clientHandler);

            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }

        }

    }

    public void removeFromList(ClientHandler clientHandler){
        clientHandlers.remove(clientHandler);
    }

    public void sendAll(String msg){
        for (ClientHandler handler : clientHandlers) {
            handler.send(msg);
        }
    }

    //TODO: to remove
    public void start() {


        while (clients.size() < 4) {

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

    public class ClientHandler implements Runnable {

        private ClientPOJO client = new ClientPOJO();
        private boolean connected = true;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {


            try {
                this.client.setSocket(socket);
                this.out = new PrintWriter(socket.getOutputStream());
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }

            this.out.println(" ___       __    _______    ___        ________   ________   _____ ______    _______      \n" +
                    "|\\  \\     |\\  \\ |\\  ___ \\  |\\  \\      |\\   ____\\ |\\   __  \\ |\\   _ \\  _   \\ |\\  ___ \\     \n" +
                    "\\ \\  \\    \\ \\  \\\\ \\   __/| \\ \\  \\     \\ \\  \\___| \\ \\  \\|\\  \\\\ \\  \\\\\\__\\ \\  \\\\ \\   __/|    \n" +
                    " \\ \\  \\  __\\ \\  \\\\ \\  \\_|/__\\ \\  \\     \\ \\  \\     \\ \\  \\\\\\  \\\\ \\  \\\\|__| \\  \\\\ \\  \\_|/__  \n" +
                    "  \\ \\  \\|\\__\\_\\  \\\\ \\  \\_|\\ \\\\ \\  \\____ \\ \\  \\____ \\ \\  \\\\\\  \\\\ \\  \\    \\ \\  \\\\ \\  \\_|\\ \\ \n" +
                    "   \\ \\____________\\\\ \\_______\\\\ \\_______\\\\ \\_______\\\\ \\_______\\\\ \\__\\    \\ \\__\\\\ \\_______\\\n" +
                    "    \\|____________| \\|_______| \\|_______| \\|_______| \\|_______| \\|__|     \\|__| \\|_______|\n");

            askNick();
        }

        public void askNick() {

            out.println("Enter your nickname:");

            try {

                this.client.setName(in.readLine());

            } catch (IOException e) {

                System.err.println(e.getMessage());
                System.exit(1);

            }
        }

        @Override
        public void run() {

            String msg;

            while (connected){

                try {
                    msg = in.readLine();

                    if (msg == null){
                        disconnect();
                        delete();
                        continue;
                    }

                    if (msg.charAt(0) == '!' && msg.length() != 1){
                        commandManager.handle(this, msg);
                        continue;
                    }

                   sendAll(msg);

                } catch (IOException e) {
                    disconnect();
                    delete();
                }

            }
        }

        public ClientPOJO getClient(){
            return client;
        }

        public void send(String msg){
            out.println(msg);
        }

        public void delete(){
            removeFromList(this);
        }

        public void disconnect(){
            connected = false;
        }
    }
}
