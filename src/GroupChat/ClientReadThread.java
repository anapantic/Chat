package GroupChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReadThread extends Thread {

    private String name;
    private BufferedReader fromServer;

    public ClientReadThread(Socket client, String name) {
        this.name = name;
        try {
            this.fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {

            try {
                String response = this.fromServer.readLine();
                if (response == null){
                    System.err.println("\rConnection lost");
                    return;
                }
                System.out.println("\r" + response);
                System.out.printf("\r[%s]: ", this.name);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
