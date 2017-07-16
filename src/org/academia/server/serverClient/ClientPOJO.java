package org.academia.server.serverClient;

import org.academia.server.Server;

import java.net.Socket;

public class ClientPOJO {

    private long chipsCoolddown = 0;
    private String name = null;
    private Socket socket;
    private boolean isAdmin = false;
    private int chips = 500;
    private Server.ClientHandler clientHandler;

    public ClientPOJO(Server.ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void getBackToList(){
        clientHandler.getBackToList();
    }

    public int getChips() {
        return chips;
    }

    public void addChips(int chips){
        this.chips+=chips;
    }

    public boolean removeChips(int chips){
        if(chips> this.chips) {
            return false;
        }else{
            this.chips -=chips;
            return true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public long getChipsCoolddown() {
        return chipsCoolddown;
    }

    public void setChipsCoolddown(long chipsCoolddown) {
        this.chipsCoolddown = chipsCoolddown;
    }
}
