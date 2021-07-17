package Network.Server;

public class Message {
    private String sender;
    private String content;
    private int ID, profileNum;
    private static int idCounter;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.ID = idCounter++;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public int getID() {
        return ID;
    }

    public int getProfileNum() {
        return profileNum;
    }

    public void setProfileNum(int profileNum) {
        this.profileNum = profileNum;
    }
}
