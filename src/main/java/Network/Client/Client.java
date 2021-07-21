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
    private String token;
    ResponseHandler responseHandler;
    private Client(String localHost, int port){
        try {
            socket = new Socket(localHost, port); //set to any port and ip
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            responseHandler = new ResponseHandler(in, out);
            responseHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String message){
        out.println(message);
        out.flush();
    }

    public static void setInstance(String localHost,int port) {
        instance = new Client(localHost, port);
    }

    public static Client getInstance() {
        return instance;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}

