package GroupChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserThread extends Thread {

    private String name;
    private Socket client;
    private ChatServer server;
    private PrintWriter toUser;
    private BufferedReader fromUser;

    public UserThread(Socket client, ChatServer chatServer) {
        this.client = client;
        this.server = chatServer;
    }

    @Override
    public void run() {
        try {
            this.fromUser = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            this.toUser = new PrintWriter(this.client.getOutputStream(), true);

            this.name = this.fromUser.readLine();
            this.sendMessage("Connected clients: " + this.server.getUserNames());

            this.server.sendToAll("New user connected: " + this.name, this);

            String clientMessage;
            do {
                clientMessage = this.fromUser.readLine();
                if (clientMessage == null)
                    break;

                this.server.sendToAll("[" + this.name + "]: " + clientMessage, this);
            } while(!clientMessage.equalsIgnoreCase("bye"));

            this.server.remove(this);
            this.client.close();
            this.server.sendToAll(this.name + " has left the chat.", this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String s) {

        if (this.toUser != null)
            this.toUser.println(s);
    }

    public String getNickname() {

        return this.name;
    }
}
