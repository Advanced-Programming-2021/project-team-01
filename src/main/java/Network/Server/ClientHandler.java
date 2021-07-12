package Network.Server;


import Network.Requests.Account.*;
import Network.Requests.InvitationRequest;
import Network.Requests.Request;
import Network.Requests.StartOnlineDuelRequest;
import Network.Responses.Account.*;
import Network.Responses.Response;
import Network.Responses.StartOnlineDuelResponse;
import Network.Utils.Logger;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {

    private Socket socket;
    private PrintWriter out;
    private Scanner in;
    public static YaGsonBuilder gsonBuilder = new YaGsonBuilder();
    public static YaGson gson = gsonBuilder.create();

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
            Logger.log(input + " received");
            Request request = gson.fromJson(input, Request.class);
            Response response = processRequestAndRespond(request);
            Logger.log(response + " sent");
            out.println(gson.toJson(response));
            out.flush();
        }
    }

    private Response processRequestAndRespond(Request request) {
        Response response = null;
        if (request instanceof LoginRequest) {
            response = new LoginResponse(request, this);
            response.handleRequest();
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
        } else if (request instanceof ChangeNicknameRequest){
            response = new ChangeNicknameResponse(request);
            response.handleRequest();
        }else if (request instanceof ChangePasswordRequest){
            response = new ChangePasswordResponse(request);
            response.handleRequest();
        } else if (request instanceof ShopInfoRequest) {
            response = new ShopInfoResponse(request);
            response.handleRequest();
        } else if (request instanceof AddCardToDeckRequest) {
            response = new AddCardToDeckResponse(request);
            response.handleRequest();
        } else if (request instanceof StartOnlineDuelRequest){
            response = new StartOnlineDuelResponse(request);
            response.handleRequest();
            if (response.getException() != null) {
                sendInvitation(request);
            }
        }
        return response;
    }

    private void sendInvitation(Request request) {
        String username = ((StartOnlineDuelRequest)request).getOpponentUsername();
        int noOfRounds = ((StartOnlineDuelRequest)request).getNoRounds();
        InvitationRequest invitationRequest = new InvitationRequest(username, noOfRounds);
    }
}
