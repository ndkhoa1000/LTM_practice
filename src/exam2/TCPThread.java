package exam2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPThread extends Thread{
    public Socket socket;
    public String ServerPassword;
    public String filepath;
    public TCPThread (Socket reqSocket, String password, String filepath){
        this.socket = reqSocket;
        this.ServerPassword = password;
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void run() {
        System.out.println("[TCP_] Client ket noi, khoi tao thread thanh cong.");
         try {            
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            PrintWriter printer = new PrintWriter(os, true);
            Scanner scanner = new Scanner(is);

            String reversePwd = new StringBuilder(ServerPassword).reverse().toString();
            // ---------------------------
            System.out.println("[TCP_] Server password: " + ServerPassword);
            String localAddr = scanner.nextLine(); 
            System.out.println("[TCP_] IP client: "+localAddr);
            String password = scanner.nextLine();
            System.out.println("[TCP_] password tu client: "+password);

            if (password.equals(reversePwd)){
                printer.println("chung thuc thanh cong");
                System.out.println("[TCP] mat khau dung.");
            } else{
                printer.println("chung thuc that bai");
                System.out.println("[TCP_Client_err] mat khau sai.");
            }
            // ---------------------------
            socket.close();
            printer.close();
            scanner.close();
        } catch (IOException e) {
            System.out.println("[!] Loi Thread: "+ e);
        }
    }
    
}
