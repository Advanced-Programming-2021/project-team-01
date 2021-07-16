package Network.Server;

import Network.Utils.Logger;
import controller.DatabaseController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.OnlineGame;
import model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server extends Application {

    private static ServerSocket serverSocket;
    private static final int port = 12345;
    private static final HashMap<String, Player> loggedInUsers = new HashMap<>();
    private static final HashMap<String, ClientHandler> clientHandlers = new HashMap<>();
    private static final HashMap<String, OnlineGame> onlineGames = new HashMap<String, OnlineGame>();
    private static final List<Message> messages = new ArrayList<>();
    private static final List<String> chatRoomOnlineUsernames = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        DatabaseController.loadGameCards();
        runServer();
    }

    private static void runServer() {
        Logger.set();
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

    public static HashMap<String, OnlineGame> getOnlineGames() {
        return onlineGames;
    }

    public static HashMap<String, ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public static void removeUser(String onlinePlayer) {
        loggedInUsers.remove(onlinePlayer);
    }

    public static ArrayList<Message> getMessages() {
        return (ArrayList<Message>) messages;
    }

    public static List<String> getChatRoomOnlineUsernames() {
        return chatRoomOnlineUsernames;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}

