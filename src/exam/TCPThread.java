package exam;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPThread extends Thread{
    public Socket socket;
    public String ServerPassword;
    public TCPThread (Socket reqSocket, String password){
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
            // ---------------------------
            String password = scanner.nextLine();
            System.out.println("[TCP_] password tu client: "+password);

            if (password.equals(ServerPassword)){
                File f = new File("D:\\Code\\java\\LTM_practice\\testFile\\emotional.mp3");
                if(f.isFile()){
                    int filesize = (int)f.length();
                    printer.println(filesize);
                    try {
                        FileInputStream fs = new FileInputStream(f);
                        DataInputStream dis = new DataInputStream(fs);
                        DataOutputStream dos = new DataOutputStream(os);
                        byte[] buffer = new byte[filesize];
                        int byteRead, EOF = -1;
                        while((byteRead = dis.read(buffer)) != EOF){
                            dos.write(buffer, 0, byteRead);
                            dos.flush();
                            System.out.println("da gui: " + byteRead);
                        }
                        dis.close();
                       System.out.println("[x] gui file thanh cong.");
                    } catch (IOException e) {
                        System.out.println("[!]loi data stream: "+ e);
                    }
                }else{
                    System.out.println("[!] File khong ton tai.");
                    socket.close();
                }
            } else{
                printer.println("-ERR");
                System.out.println("[TCP_Client_err] sai password.");
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
