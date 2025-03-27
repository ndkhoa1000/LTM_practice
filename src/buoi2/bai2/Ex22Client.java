package buoi2.bai2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Ex22Client {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 2000;
        try (Socket socket = new Socket(host, port)) {
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            PrintWriter printer = new PrintWriter(os, true);
            Scanner scanner = new Scanner(is);
            Scanner kb = new Scanner(System.in);
            String msg;
            // ----------------------------
            System.out.println("nhap cau lenh:");
            msg = kb.nextLine();
            String cmd = msg.split(" ")[0];
            if (cmd.equals("LIST")) {
                printer.println(msg);
                String ServerMsg = "";
                while (true) {
                    if (ServerMsg.equals("end."))
                        break;
                    ServerMsg = scanner.nextLine();
                    System.out.println("server msg: " + ServerMsg);
                }
            } else if (cmd.equals("READ")){
                System.out.println("[x] nhap ten file can luu: ");
                String saveFilePath = kb.nextLine();
        
                printer.println(msg);
                int filesize = scanner.nextInt();
                int total = 0;
                if(filesize > 0){
                    try {
                        DataInputStream dis = new DataInputStream(is);
                        File f = new File(saveFilePath);
                        FileOutputStream fs = new FileOutputStream(f);
                        DataOutputStream dos = new DataOutputStream(fs);
                        byte[] buffer = new byte[4000000];
                        int byteWrite;
                        while (true) { 
                            byteWrite = dis.read(buffer);
                            if(byteWrite >0){
                                dos.write(buffer, 0, byteWrite);
                                total += byteWrite;
                            }
                            if(total >= filesize)
                                break;
                        }
                        dis.close();
                        dos.close();
                        System.out.println("[X] ghi file thanh cong.");
                    } catch (IOException e) {
                        System.out.println("[!] loi ghi file: "+ e);
                    }
                }
                else System.out.println("[!] loi filesize bang 0.");
            } 
            // ----------------------------
            printer.close();
            scanner.close();
            kb.close();
        } catch (Exception e) {
            System.out.println("[!]loi socket: " + e);
        }
    }}