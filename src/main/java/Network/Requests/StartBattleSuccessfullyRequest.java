package Network.Requests;

public class StartBattleSuccessfullyRequest extends Request {
    private String opponent;
    private int rounds;
    private String challenger;

    public StartBattleSuccessfullyRequest(String targetPlayer, String challenger, int rounds) {
        this.opponent = targetPlayer;
        this.rounds = rounds;
        this.challenger = challenger;
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
}
