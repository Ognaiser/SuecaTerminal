package org.academia.server;

import org.academia.sueca.ClientHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;


public class Server {

    public static final int PORT = 1991;
    private List<ClientHandler> clients= new LinkedList<>();


    public void start(){

    }


    public void init() throws IOException {

        Socket clientSocket;
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {

            System.out.println("* Waiting for connection...*");
            clientSocket = serverSocket.accept();

            clients.add(new ClientHandler(clientSocket));
        }
    }

}
