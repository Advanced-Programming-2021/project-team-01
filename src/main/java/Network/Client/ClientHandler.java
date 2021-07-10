package Network.Client;

import java.net.Socket;

public class ClientHandler extends Thread{

    Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
    }
}
