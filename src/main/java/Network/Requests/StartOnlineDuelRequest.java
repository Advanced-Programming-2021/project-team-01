package Network.Requests;

public class StartOnlineDuelRequest extends Request{

    private String opponentUsername;
    private int noRounds;
    private boolean isReversed;
    public StartOnlineDuelRequest(String authToken,String opponentUsername,int noRounds,boolean isReversed){
        this.authToken = authToken;
        this.opponentUsername = opponentUsername;
        this.noRounds = noRounds;
        this.isReversed = isReversed;
    }

    public int getNoRounds() {
        return noRounds;
    }

    public boolean isReversed() {
        return isReversed;
    }

    public String getOpponentUsername() {
        return opponentUsername;
    }
}
