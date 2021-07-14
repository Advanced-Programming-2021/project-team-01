package Network.Requests;

public class RejectOnlineGameRequest extends Request {
    private String opponent;
    public RejectOnlineGameRequest(String opponent) {
        super();
        this.opponent = opponent;
    }

    public String getOpponent() {
        return opponent;
    }
}
