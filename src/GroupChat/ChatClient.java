package GroupChat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {

        ChatClient client = new ChatClient("localhost", ChatServer.PORT_SERVER);
        client.execute();

    }

    private String hostname;
    private int port;
    private String name;

    public ChatClient(String localhost, int portServer) {
        this.hostname = localhost;
        this.port = portServer;
    }

    private void execute() {

        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your name: ");
            this.name = sc.nextLine();

            Socket client = new Socket(this.hostname, this.port);
            System.out.println("Connected to the chat @" + this.hostname);

            new ClientReadThread(client, this.name).start();
            new ClientWriteThread(client, this.name).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
