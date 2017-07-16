package org.academia.server;

import org.academia.server.serverClient.ClientPOJO;
import org.academia.server.serverClient.CommandManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public final static long CHIPSCOOLDOWN = 180000;
    public static final int PORT = 8080;
    private ServerSocket ss;
    private List<ClientHandler> clientHandlers;
    private CommandManager commandManager;
    private ExecutorService pool;
    private Map<String, String[]> hosts = new HashMap<>();

    //TODO: prettify things

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.init();
        server.start();
    }

    public void init() {

        try {
            populateHost();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    updateFile();
                }
            });
            ss = new ServerSocket(PORT);
            clientHandlers = new ArrayList<>();
            commandManager = new CommandManager(clientHandlers);
        } catch (IOException e) {
            System.err.println("Error on the creation the server! " + e.getMessage());
            System.exit(1);
        }

    }

    private void updateFile() {

        try {

            PrintWriter writer = new PrintWriter(new FileWriter("resources/know-host"), true);

            Set<String> ips = hosts.keySet();
            String[] values;

            for (String host : ips) {
                values = hosts.get(host);
                writer.println(host + " " + values[0] + " " + values[1]);
                System.out.println(host + " " + values[0] + " " + values[1]);
            }

            writer.close();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }

    }

    private void populateHost() {
        try {

            BufferedReader reader = new BufferedReader(new FileReader("resources/know-host"));

            String line;
            String[] values = new String[2];
            String ip;

            while ((line = reader.readLine()) != null) {


                ip = line.split(" ")[0];
                values[0] = line.split(" ")[1];
                values[1] = line.split(" ")[2];

                hosts.put(ip, values);

            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    public void start() {

        pool = Executors.newFixedThreadPool(25);

        System.out.println("before loop!");

        while (true) {
            System.out.println("started loop!");
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

    public void removeFromList(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }

    public void sendAll(String msg, String name) {
        for (ClientHandler handler : clientHandlers) {
            if (!handler.getClient().getName().equals(name)) {
                handler.out.println();
                handler.out.println(name + " said: " + msg);
                handler.out.print("Say:");
                handler.out.flush();
            }
        }
    }

    private void addToKnow(String hostAddress, String name, int chips) {

        String[] values = new String[]{name, String.valueOf(chips)};

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("resources/know-host", true), true);
            writer.println(hostAddress + " " + name + " " + chips);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }

        hosts.put(hostAddress, values);
    }

    public class ClientHandler implements Runnable {

        private ClientPOJO client;
        private boolean connected = true;
        private PrintWriter out;
        private BufferedReader in;
        private Socket socket;

        public ClientHandler(ClientPOJO client) {

            this.client = client;

            try {
                this.out = new PrintWriter(client.getSocket().getOutputStream(), true);
                this.in = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        public ClientHandler(Socket socket) {

            try {
                client = new ClientPOJO(this);
                this.client.setSocket(socket);
                this.client.setAdmin(false);
                this.socket = socket;
                this.out = new PrintWriter(socket.getOutputStream(), true);
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


        }

        private boolean checkKnow(String hostAddress) {
            String[] values;


            if ((values = hosts.get(hostAddress)) != null) {
                client.setName(values[0]);
                client.setChips(Integer.parseInt(values[1]));
                return true;
            }

            return false;
        }

        public void askNick() {

            boolean validChoice = false;
            String name = "";

            while (!validChoice) {
                try {
                    out.println("Enter your nickname:");
                    name = (in.readLine());
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    System.exit(1);
                }

                if (!checkAvailability(name)) {
                    out.println("Username already taken");
                } else {
                    this.client.setName(name);
                    validChoice = true;
                }

            }
        }

        private boolean checkAvailability(String name) {

            Set<String> ips = hosts.keySet();
            String[] values;

            for (String cena : ips) {
                values = hosts.get(cena);
                if (name.equals(values[0])) {
                    return true;
                }
            }

            return true;
        }

        @Override
        public void run() {

            if (client.getName() == null) {
                if (!checkKnow(socket.getInetAddress().getHostAddress())) {
                    askNick();
                    addToKnow(socket.getInetAddress().getHostAddress(), client.getName(), 500);
                    out.println("\nYou are in the lobby!\n");
                }
            }

            String msg;

            while (connected) {

                try {
                    out.print("Say:");
                    out.flush();
                    msg = in.readLine();

                    if (msg == null) {
                        disconnect();
                        delete();
                        continue;
                    }

                    if (msg.charAt(0) == '!' && msg.length() != 1) {
                        commandManager.handle(this, msg);
                        continue;
                    }

                    sendAll(msg, client.getName());

                } catch (IOException e) {
                    disconnect();
                    delete();
                    out.close();
                }

            }

            delete();
        }

        public void updatePlayerMap() {
            String[] values = new String[]{client.getName(), String.valueOf(client.getChips())};
            hosts.put(socket.getInetAddress().getHostAddress(), values);
        }

        public ClientPOJO getClient() {
            return client;
        }

        public void send(String msg) {
            out.println(msg);
        }

        public void delete() {
            removeFromList(this);
        }

        public void disconnect() {
            connected = false;
        }

        public void getBackToList() {
            send("You are back to the chat!");
            ClientHandler newClient = new ClientHandler(client);
            clientHandlers.add(newClient);
            pool.submit(newClient);
        }
    }

}
