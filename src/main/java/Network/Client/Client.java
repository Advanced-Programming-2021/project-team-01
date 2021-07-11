package Network.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public PrintWriter out;
    public Scanner in;
    private static Client instance;
    Socket socket;
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

    public static void setInstance(int port) {
        instance = new Client(port);
    }

    public static Client getInstance() {
        return instance;
    }
}
