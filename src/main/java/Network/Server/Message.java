package Network.Server;

public class Message {
    private String sender;
    private String content;
    private int ID;
    private static int idCounter;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.ID = idCounter++;
    }
}
