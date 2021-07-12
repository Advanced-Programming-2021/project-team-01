package Network.Requests;

public class InvitationRequest extends Request {
    private String opponentUsername;
    private int noOfRounds;

    public InvitationRequest(String opponentUsername, String authToken, int noOfRounds) {
        this.authToken = authToken;
        this.opponentUsername = opponentUsername;
        this.noOfRounds = noOfRounds;
    }

}
