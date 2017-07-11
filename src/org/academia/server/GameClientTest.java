package org.academia.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by ognaiser on 11-07-2017.
 */
public abstract class GameClientTest {

    private BufferedReader in;
    private PrintWriter out;
    private ClientPOJO client;

    public GameClientTest(ClientPOJO client) {

        try {
            this.client = client;
            this.in = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
            this.out = new PrintWriter(client.getSocket().getOutputStream());
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    public void getBacktoChat(){
        client.getBackToList();
    }
}
