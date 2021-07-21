package Network.Server;

import Network.Utils.Logger;
import controller.DatabaseController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.OnlineGame;
import model.Player;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class Server extends Application {

    private static final int port = 12345;
    private static final HashMap<String, Player> loggedInUsers = new HashMap<>();
    private static final HashMap<String, ClientHandler> clientHandlers = new HashMap<>();
    private static final HashMap<String, OnlineGame> onlineGames = new HashMap<>();
    private static final List<Message> messages = new ArrayList<>();
    private static final List<String> chatRoomOnlineUsernames = new ArrayList<>();
    private static final List<String> oneRoundGames = new ArrayList<>();
    private static final List<String> threeRoundGames = new ArrayList<>();
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws Exception {
        DatabaseController.loadGameCards();
        runServer();
    }

    private static void runServer() {
        Logger.set();
        try {
            serverSocket = new ServerSocket(12345,0,InetAddress.getByName("localhost"));
            while (true) {
                Logger.log("Waiting for a client");
                Socket socket = serverSocket.accept();
                Logger.log("Client connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            }
        }catch (IOException e) {
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
        clientHandlers.remove(loggedInUsers.get(onlinePlayer).getUsername());
        loggedInUsers.remove(onlinePlayer);
    }

    public static ArrayList<Message> getMessages() {
        return (ArrayList<Message>) messages;
    }

    public static List<String> getChatRoomOnlineUsernames() {
        return chatRoomOnlineUsernames;
    }

    public static List<String> getOneRoundGames() {
        return oneRoundGames;
    }

    public static List<String> getThreeRoundGames() {
        return threeRoundGames;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}

