package nio;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by weinanli on 11/07/2017.
 */
public class PlayWithAsyncHTTP {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        Socket socket = serverSocket.accept();
        

    }
}
