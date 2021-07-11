package Network.Client;


import Network.Requests.Request;
import Network.Responses.Response;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{

    private Socket socket;
    private PrintWriter out;
    private Scanner in;
    public static YaGsonBuilder gsonBuilder = new YaGsonBuilder();
    public static YaGson gson = gsonBuilder.create();

    //Server Side: handle requests that come from clients

    public ClientHandler(Socket socket){
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            String input = in.nextLine();
            Request request = gson.fromJson(input, Request.class);
            Response response = processRequest(request);
            out.println(gson.toJson(response));
            out.flush();
        }
    }

    private Response processRequest(Request request) {

        return null;
    }
}
