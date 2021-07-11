package Network.Client;


import Network.Responses.Response;
import com.gilecode.yagson.YaGson;
import com.sun.tools.example.debug.tty.EventHandler;
import javafx.application.Platform;

import java.awt.*;
import java.io.PrintWriter;
import java.util.Scanner;

public class ResponseHandler extends Thread{

    private Scanner scanner;
    private PrintWriter printWriter;
    private YaGson yaGson = new YaGson();

    public ResponseHandler(Scanner scanner, PrintWriter printWriter){
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    @Override
    public void run() {
        while (true){
            String input = scanner.nextLine();
            Response response = yaGson.fromJson(input, Response.class);
            Platform.runLater(response::handleResponse);
            //response.handleResponse(); //fixme Potential Bug need to start a JavaFx Thread!
        }
    }
}
