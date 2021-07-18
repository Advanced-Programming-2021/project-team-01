package Network.Requests.Battle;

import Network.Requests.Request;

public class NewMatchmakingRequest extends Request {
    int numberOfRounds;

    public NewMatchmakingRequest(String authToken, int numberOfRounds) {
        this.authToken = authToken;
        this.numberOfRounds = numberOfRounds;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }
}
