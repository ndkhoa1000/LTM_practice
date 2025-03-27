package buoi2.bai2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Ex22Server {
    public static void main(String[] args) {
        int port = 2000;
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("[v] cho client ket noi...");
            while (true) { 
                try {
                    Socket socket = server.accept();
                    Ex22Thread thread = new Ex22Thread(socket);
                    thread.start();
                } catch (IOException e) {
                    System.out.println("[!] loi socket:" +e);
                }
            }
        } catch (Exception e) {
            System.out.println("[!] loi server socket: "+ e);
        }
    }
}
