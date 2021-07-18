package Network.Server;


import Network.Requests.*;
import Network.Requests.Account.*;
import Network.Requests.Battle.*;
import Network.Responses.*;
import Network.Responses.Account.*;
import Network.Responses.Battle.*;
import Network.Utils.Logger;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class ClientHandler extends Thread {

    public static YaGsonBuilder gsonBuilder = new YaGsonBuilder();
    public static YaGson gson = gsonBuilder.create();
    public PrintWriter out;
    public Scanner in;
    private Socket socket;


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

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void run() {
        String input = "";
        while (true) {
            try {
                input = in.nextLine();
            } catch (NoSuchElementException e) {
                String username = ClientHandler.getKeyByValue(Server.getClientHandlers(), this);
                String[] token = {""};
                Server.getLoggedInUsers().forEach((s, player) -> {
                    if (player.getUsername().equals(username))
                        token[0] = player.getAuthToken();
                });
                Server.getClientHandlers().remove(username);
                Server.getLoggedInUsers().remove(token[0]);
                Logger.log("Client disconnected");
                break;
            }
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
        } else if (request instanceof StartBattleSuccessfullyRequest) {
            startGame(request);
            return;
        } else if (request instanceof BattleActionRequest) {
            ClientHandler clientHandler = Server.getClientHandlers().get(((BattleActionRequest) request).getOpponentUserName());
            BattleActionResponse battleResponse = new BattleActionResponse(request);
            clientHandler.out.println(battleResponse);
            clientHandler.out.flush();
            Logger.log("Sent: " + battleResponse);
            return;
        } else if (request instanceof EnterChatRoomRequest) {
            response = new EnterChatRoomResponse(request);
            response.handleRequest();
        } else if (request instanceof ExitChatRoomRequest) {
            response = new ExitChatRoomResponse(request);
            response.handleRequest();
        } else if (request instanceof SendMessageRequest) {
            response = new SendMessageResponse(request);
            response.handleRequest();
        } else if (request instanceof DeleteMessageRequest) {
            response = new DeleteMessageResponse(request);
            response.handleRequest();
        } else if (request instanceof SendNeededCardsRequest) {
            response = new GetNeededCardResponse(request);
            response.handleRequest();
            ClientHandler clientHandler = Server.getClientHandlers().get(((SendNeededCardsRequest) request).getOpponent());
            clientHandler.out.println(gson.toJson(response));
            clientHandler.out.flush();
            return;
        } else if (request instanceof ScoreboardInfoRequest) {
            response = new ScoreboardInfoResponse(request);
            response.handleRequest();
        } else if (request instanceof ActivateChainRequest) {
            response = new ActivateChainResponse(request);
            response.handleRequest();
            ClientHandler clientHandler = Server.getClientHandlers().get(((ActivateChainRequest) request).getOpponent());
            clientHandler.out.println(gson.toJson(response));
            clientHandler.out.flush();
            return;
        } else if (request instanceof UpdateActionRequest) {
            response = new UpdateActionResponse(request);
            response.handleRequest();
            ClientHandler clientHandler = Server.getClientHandlers().get(((UpdateActionRequest) request).getOpponentUsername());
            clientHandler.out.println(gson.toJson(response));
            clientHandler.out.flush();
            return;
        } else if (request instanceof NewMatchmakingRequest) {
            response = new MatchmakingResponse(request);
            response.handleRequest();
        } else if (request instanceof CancelMatchMakingRequest){
            response = new CancelMatchMakingResponse(request);
            response.handleRequest();
        }
        Logger.log("Sent: " + response);
        out.println(gson.toJson(response));
        out.flush();
    }

    private void startGame(Request request) {
        StartBattleSuccessfullyRequest clientRequest = (StartBattleSuccessfullyRequest) request;
        Response response = new StartBattleSuccessfullyResponse(request);
        response.handleRequest();
        ClientHandler clientHandler = Server.getClientHandlers().get(clientRequest.getChallenger());
        clientHandler.out.println(gson.toJson(response));
        clientHandler.out.flush();
        Logger.log("Sent: " + response);
    }

    private void sendInvitation(Response response) {
        String username = ((StartOnlineDuelRequest) response.getRequest()).getOpponentUsername();
        ClientHandler clientHandler = Server.getClientHandlers().get(username);
        clientHandler.out.println(gson.toJson(response));
        clientHandler.out.flush();
        Logger.log("Sent: " + response);
    }

    public void updateChatRoom(String username) {
        EnterChatRoomResponse response = new EnterChatRoomResponse(new Request());
        response.setUsername(username);
        out.println(gson.toJson(response));
        out.flush();
    }
}
