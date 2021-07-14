package Network.Server;


import Network.Requests.AcceptOnlineGameRequest;
import Network.Requests.Account.*;
import Network.Requests.RejectOnlineGameRequest;
import Network.Requests.Request;
import Network.Requests.StartOnlineDuelRequest;
import Network.Responses.AcceptOnlineGameResponse;
import Network.Responses.Account.*;
import Network.Responses.RejectOnlineGameResponse;
import Network.Responses.Response;
import Network.Utils.Logger;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import org.apache.commons.logging.Log;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {

    public static YaGsonBuilder gsonBuilder = new YaGsonBuilder();
    public static YaGson gson = gsonBuilder.create();
    private Socket socket;
    private PrintWriter out;
    private Scanner in;

    //Server Side: handle requests that come from clients

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            String input = in.nextLine();
            Logger.log("Recieved: " + input);
            Request request = gson.fromJson(input, Request.class);
            processRequestAndRespond(request);
        }
    }

    private void processRequestAndRespond(Request request) {
        Response response = null;
        if (request instanceof LoginRequest) {
            response = new LoginResponse(request);
            ((LoginResponse) response).handleRequest(this);
        } else if (request instanceof RegisterRequest) {
            response = new RegisterResponse(request);
            response.handleRequest();
        } else if (request instanceof LogoutRequest) {
            response = new LogoutResponse(request);
            response.handleRequest();
        } else if (request instanceof BuyRequest) {
            response = new BuyResponse(request);
            response.handleRequest();
        } else if (request instanceof ProfileInfoRequest) {
            response = new ProfileInfoResponse(request);
            response.handleRequest();
        } else if (request instanceof ChangeNicknameRequest) {
            response = new ChangeNicknameResponse(request);
            response.handleRequest();
        } else if (request instanceof ChangePasswordRequest) {
            response = new ChangePasswordResponse(request);
            response.handleRequest();
        } else if (request instanceof ShopInfoRequest) {
            response = new ShopInfoResponse(request);
            response.handleRequest();
        } else if (request instanceof AddCardToDeckRequest) {
            response = new AddCardToDeckResponse(request);
            response.handleRequest();
        } else if (request instanceof StartOnlineDuelRequest) {
            response = new StartOnlineDuelResponse(request);
            response.handleRequest();
            if (response.getException() == null) {
                sendInvitation(response);
                return;
            }
        } else if (request instanceof ActivateDeckRequest) {
            response = new ActivateDeckResponse(request);
            response.handleRequest();
        } else if (request instanceof DeckInfoRequest) {
            response = new DeckInfoResponse(request);
            response.handleRequest();
        } else if (request instanceof CreateDeckRequest) {
            response = new CreateDeckResponse(request);
            response.handleRequest();
        } else if (request instanceof DeleteDeckRequest) {
            response = new DeleteDeckResponse(request);
            response.handleRequest();
        } else if (request instanceof CustomizeDeckRequest) {
            response = new CustomizeDeckResponse(request);
            response.handleRequest();
        } else if (request instanceof RejectOnlineGameRequest) {
            response = new RejectOnlineGameResponse(request);
            response.handleRequest();
        } else if (request instanceof AcceptOnlineGameRequest) {
            response = new AcceptOnlineGameResponse(request);
            response.handleRequest();
        } else if (request instanceof RemoveCardFromDeckRequest) {
            response = new RemoveCardFromDeckResponse(request);
            response.handleRequest();
        }
        Logger.log("Sent: " + response);
        out.println(gson.toJson(response));
        out.flush();
    }

    private void sendInvitation(Response response) {
        String username = ((StartOnlineDuelRequest) response.getRequest()).getOpponentUsername();
        ClientHandler clientHandler = Server.getClientHandlers().get(username);
        clientHandler.out.println(gson.toJson(response));
        clientHandler.out.flush();
        Logger.log("*Sent: " + response);
    }
}
