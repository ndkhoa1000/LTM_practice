package exam;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static String saveFilePath = "D:\\Code\\java\\LTM_practice\\testFile\\result.mp3"; // chinh sua noi luu file (savefile);
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        try (DatagramSocket UDPsocket = new DatagramSocket()) {
            byte[] buffer, data;
            int length;

            Scanner kb = new Scanner(System.in);
            System.out.print("[] dia chi: ");
            InetAddress hostAddr = InetAddress.getByName(kb.nextLine());
            System.out.print("[] cong: ");
            int UDPport = kb.nextInt();

            // ---------send ip local-------------------
            InetAddress localAddr = InetAddress.getLocalHost();
            data = localAddr.toString().getBytes();
            DatagramPacket sPacket = new DatagramPacket(data, data.length,hostAddr, UDPport);
            UDPsocket.send(sPacket);

            //---------receive 2 packet TCP port, pwd----------------
            buffer = new byte[60000];
            DatagramPacket rPacket = new DatagramPacket(buffer, buffer.length);
            UDPsocket.receive(rPacket);
            data = rPacket.getData();
            length = rPacket.getLength();
            int TCPport = Integer.parseInt(new String(data,0,length,"UTF-8"));
            System.out.println("[x] TCP port: "+ TCPport);

            buffer = new byte[60000];
            rPacket = new DatagramPacket(buffer, buffer.length);
            UDPsocket.receive(rPacket);
            data = rPacket.getData();
            length = rPacket.getLength();
            String pwd = new String(data,0,length,"UTF-8");
            System.out.println("[x] password: " + pwd);

            //---------connect TCP server --------------------------
            try (Socket TCPsocket = new Socket(hostAddr, TCPport)) {
                InputStream is = TCPsocket.getInputStream();
                OutputStream os = TCPsocket.getOutputStream();
                PrintWriter printer = new PrintWriter(os, true);
                Scanner scanner = new Scanner(is);

                printer.println(pwd);
                System.out.println("[] da gui password cho server...");
                String result = scanner.nextLine();
                System.out.println("[x]msg server: "+result);
                if(result.equals("-ERR")){
                    System.out.println("mat khau sai.");
                } else{

                    try {
                        int filesize = Integer.parseInt(result);
                        System.out.println("kich thuoc file: "+ filesize);
                        
                        File f = new File(saveFilePath);
                        FileOutputStream fs = new FileOutputStream(f);
                        DataOutputStream dos = new DataOutputStream(fs);

                        DataInputStream dis = new DataInputStream(is);
                        data = new byte[filesize];
                        dis.read(data, 0, filesize);
                        dos.write(data, 0, filesize);
                        System.out.println("[x]ghi file thanh cong!");
                        dos.close();
                        dis.close();
                    } catch (IOException | NumberFormatException e) {
                        System.out.println("[!] loi convert result " + e);
                    }
                }
                printer.close();
                scanner.close();
            } catch (Exception e) {
                System.out.println("[!] Loi TCP socket: "+e);
            }
            //--------------------------------------------
            kb.close();
        } catch (Exception e) {
            System.out.println("[!] loi UDP socket: " +e);
        }
    }
}
