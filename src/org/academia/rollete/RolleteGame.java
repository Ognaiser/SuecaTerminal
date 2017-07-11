package org.academia.rollete;

import java.util.ArrayList;
import java.util.List;

public class RolleteGame implements Runnable{

    private List<RolleteClient> players = new ArrayList<>();

    public RolleteGame(List<RolleteClient> clients) {
        this.players = clients;
    }

    @Override
    public void run() {

    }


}
