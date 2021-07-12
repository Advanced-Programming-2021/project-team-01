package Network.Requests;

public class StartOnlineDuelRequest extends Request{

    private String opponentUsername;
    private int noRounds;
    public StartOnlineDuelRequest(String authToken,String opponentUsername,int noRounds){
        this.authToken = authToken;
        this.opponentUsername = opponentUsername;
        this.noRounds = noRounds;
    }

    public int getNoRounds() {
        return noRounds;
    }

    public String getOpponentUsername() {
        return opponentUsername;
    }
}
