package Network.Requests;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.ExclusionStrategy;
import com.gilecode.yagson.com.google.gson.FieldAttributes;
import model.OnlineGame;

public class StartBattleSuccessfullyRequest extends Request {
    private String opponent;
    private int rounds;
    private String challenger;
    private OnlineGame onlineGame;
    public StartBattleSuccessfullyRequest(String targetPlayer, String challenger, int rounds,OnlineGame game) {
        this.opponent = targetPlayer;
        this.rounds = rounds;
        this.challenger = challenger;
        this.onlineGame = game;
    }

    public String getOpponent() {
        return opponent;
    }

    public int getRounds() {
        return rounds;
    }

    public String getChallenger() {
        return challenger;
    }

    public OnlineGame getGame() {
        return onlineGame;
    }


}
