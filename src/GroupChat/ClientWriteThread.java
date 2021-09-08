package GroupChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientWriteThread extends Thread {

    private String name;
    private PrintWriter toServer;

    public ClientWriteThread(Socket client, String name) {
        this.name = name;
        try {
            this.toServer = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        this.toServer.println(this.name);

        try (Scanner sc = new Scanner(System.in)) {

            String text;

            do {
                System.out.printf("\r[%s]: ", this.name);
                text = sc.nextLine();
                this.toServer.println(text);
            } while (!text.equalsIgnoreCase("bye"));
        }

    }
}
