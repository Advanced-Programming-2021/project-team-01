package Network.Requests.Account;

import Network.Requests.Request;
import model.Player;

public class LogoutRequest extends Request {
    Player onlinePlayer;

    public LogoutRequest(Player player){
        this.onlinePlayer = player;
    }

    public Player getOnlinePlayer() {
        return onlinePlayer;
    }
}
