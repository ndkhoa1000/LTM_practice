package exam;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {     
        int UDPport = 2000;
        int TCPport = 4000;
        String password = "strongpassword";

        try (ServerSocket server = new ServerSocket(TCPport)) {
            System.out.println("[TCP_] cho client ket noi...");
                try {
                    UDPThread UDPThread = new UDPThread(UDPport, TCPport, password);
                    UDPThread.start();
                    Socket socket = server.accept();
                    TCPThread TCPThread = new TCPThread(socket, password);
                    TCPThread.start();
                } catch (IOException e) {
                    System.out.println("[!TCP_] loi socket:" +e);
                }
        } catch (Exception e) {
            System.out.println("[!TCP_] loi server socket: "+ e);
        }
    }
}
