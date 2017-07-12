package org.academia.server;

import java.net.Socket;

public class ClientPOJO {

    private String name;
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
}
