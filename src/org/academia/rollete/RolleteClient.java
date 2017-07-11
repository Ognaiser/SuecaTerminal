package org.academia.rollete;

import org.academia.server.ClientPOJO;
import org.academia.server.GameClientTest;

import java.io.IOException;

public class RolleteClient extends GameClientTest {

    public RolleteClient(ClientPOJO client) {
        super(client);
    }

    public void sayToPlayer(String msg){
        out.println(msg);
    }

    public String getInputFromPlayer(){
        try {
            return in.readLine();
        } catch (IOException e) {
            System.err.println("Error: "+ e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
