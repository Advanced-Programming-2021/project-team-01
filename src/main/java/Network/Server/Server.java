package Network.Server;

import Network.Utils.Logger;
import model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    private static ServerSocket serverSocket;
    public static Logger logger;
    private static final int port = 12345;
    private static HashMap<String, Player> loggedInUsers = new HashMap<>();

    public static void main(String[] args) {
        runServer();
    }

    private static void runServer() {
        logger.set();
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Logger.log("Waiting for a client");
                Socket socket = serverSocket.accept();
                Logger.log("Client connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static HashMap<String, Player> getLoggedInUsers() {
        return loggedInUsers;
    }
}

