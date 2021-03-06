package Network.Client;


import Network.Requests.StartBattleSuccessfullyRequest;
import Network.Responses.Response;
import Network.Responses.StartBattleSuccessfullyResponse;
import Network.Utils.Logger;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import javafx.application.Platform;

import java.io.PrintWriter;
import java.util.Scanner;

public class ResponseHandler extends Thread {

    private Scanner scanner;
    private PrintWriter printWriter;
    private YaGson yaGson = new YaGson();

    public ResponseHandler(Scanner scanner, PrintWriter printWriter) {
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    @Override
    public void run() {
        while (true) {
            String input = scanner.nextLine();
            Response response = yaGson.fromJson(input, Response.class);
            Logger.log("received: " + input);
            Platform.runLater(response::handleResponse);
            //response.handleResponse(); //fixme Potential Bug need to start a JavaFx Thread!
        }
    }
}
