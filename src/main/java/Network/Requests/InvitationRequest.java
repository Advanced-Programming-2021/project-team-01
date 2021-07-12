package Network.Requests;

public class InvitationRequest {
    private String username;
    private int noOfRounds;

    public InvitationRequest(String username, int noOfRounds) {
        this.username = username;
        this.noOfRounds = noOfRounds;
    }
}
