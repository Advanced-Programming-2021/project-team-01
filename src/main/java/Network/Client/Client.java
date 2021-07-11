package Network.Client;

import model.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private PrintWriter out;
    private Scanner in;
    private static Client instance;
    Socket socket;
    private Player onlinePlayer = null;
    ResponseHandler responseHandler;
    private Client(int port){
        try {
            socket = new Socket("localhost", port); //set to any port
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            responseHandler = new ResponseHandler(in, out);
            responseHandler.start();
            responseHandler.setPriority(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String message){
        out.println(message);
        out.flush();
    }

    public static void setInstance(int port) {
        instance = new Client(port);
    }

    public static Client getInstance() {
        return instance;
    }

    public void setOnlinePlayer(Player onlinePlayer) {
        this.onlinePlayer = onlinePlayer;
    }

    public Player getOnlinePlayer() {
        return onlinePlayer;
    }
}
