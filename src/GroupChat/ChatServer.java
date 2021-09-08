package GroupChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatServer {

    public static int PORT_SERVER = 12345;

    public static void main(String[] args) {
        ChatServer server = new ChatServer(PORT_SERVER);
        server.execute();
    }

    private int port;
    private Set<UserThread> users = new HashSet<>();

    public ChatServer(int portServer) {
        this.port = portServer;
    }

    private void execute() {

        try (ServerSocket server = new ServerSocket(this.port)) {

            System.err.println("Chat server listening on port: " + this.port);

            while (true) {
                Socket client = server.accept();
                System.err.println("Client connected!");

                UserThread user = new UserThread(client, this);
                this.users.add(user);
                user.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getUserNames() {
        return this.users.stream()
                .map(UserThread::getNickname)
                .collect(Collectors.toList());
    }

    public void sendToAll(String s, UserThread userThread) {
        this.users.stream().filter(u -> u != userThread)
                .forEach(u -> u.sendMessage(s));
    }

    public void remove(UserThread userThread) {
        String userName = userThread.getNickname();
        this.users.remove(userThread);
        System.err.println("Client disconnected: " + userName);
    }
}
