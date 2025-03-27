package buoi2.bai2;

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

public class Ex22Thread extends Thread{
    public Socket socket;
    public Ex22Thread (Socket reqSocket){
        this.socket = reqSocket;
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void run() {
        System.out.println("[x] Client ket noi, khoi tao thread thanh cong.");
         try {            
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            PrintWriter printer = new PrintWriter(os, true);
            Scanner scanner = new Scanner(is);
            // ---------------------------
            String[] msg = scanner.nextLine().split(" ");
            String command = msg[0];
            String path =  msg[1];
            System.out.println("command: " + command);
            System.out.println("path: " + path);

            if(msg[0].equals("LIST")){
                File f = new File(path);                
                if(f.exists() && f.isDirectory()){
                    String[] paths = f.list();
                    for (String listPath : paths) {
                        File f2 = new File(path + "\\"+ listPath);
                        if (f2.isFile())
                            printer.println("[file]-" + listPath);
                        else if(f2.isDirectory())
                            printer.println("[folder]-" + listPath);
                        else 
                            printer.println("[unknown]-" + listPath);
                    }
                    printer.println("end.");
                }else {
                    printer.println("[!] folder khong ton tai");
                }
            } else if (msg[0].equals("READ")){
                File f = new File(path);
                if(f.isFile()){
                    int filesize = (int)f.length();
                    printer.println(filesize);
                    try {
                        FileInputStream fs = new FileInputStream(f);
                        DataInputStream dis = new DataInputStream(fs);
                        DataOutputStream dos = new DataOutputStream(os);
                        byte[] buffer = new byte[4000000];
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
                    printer.println("[!] File khong ton tai.");
                }
            } else{
                printer.println("[!] sai cu phap, vui long thu lai.");
            }
            // ---------------------------
            printer.close();
            scanner.close();
        } catch (IOException e) {
            System.out.println("[!] Loi Thread: "+ e);
        }
    }
    
}
