package exam;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {     
        int UDPport = 2000; //chinh sua cong UDP tai day
        int TCPport = 4000; // chinh sua cong TCP tai day
        String password = "strongpassword"; // chinh sua password tai day
        String filepath = "D:\\Code\\java\\LTM_practice\\testFile\\emotional.mp3"; // chinh sua filepath tai day

        //chay 2 thread UDP va TCP rieng biet, 
        //sua lai logic tai TCPThread.java va UDPThread.java

        try (ServerSocket server = new ServerSocket(TCPport)) {
            System.out.println("[TCP_] cho client ket noi...");
                try {
                    UDPThread UDPThread = new UDPThread(UDPport, TCPport, password);
                    UDPThread.start();
                    Socket socket = server.accept();
                    TCPThread TCPThread = new TCPThread(socket, password, filepath);
                    TCPThread.start();
                } catch (IOException e) {
                    System.out.println("[!TCP_] loi socket:" +e);
                }
        } catch (Exception e) {
            System.out.println("[!TCP_] loi server socket: "+ e);
        }
    }
}
