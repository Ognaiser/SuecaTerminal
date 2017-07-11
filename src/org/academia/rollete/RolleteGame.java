package org.academia.rollete;

import java.util.ArrayList;
import java.util.List;

public class RolleteGame implements Runnable{

    private List<RolleteClient> players = new ArrayList<>();
    private boolean isOver = false;


    @Override
    public void run() {
        start();
    }

    private void start(){

        while (true){

            if (players.size() == 0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }

        }

    }


    public void addPlayer(RolleteClient client){
        players.add(client);
    }
}
