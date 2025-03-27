package exam2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        try (DatagramSocket UDPsocket = new DatagramSocket()) {
            Scanner kb = new Scanner(System.in);
            System.out.print("[] nhap UDP port: ");
            int UDPport = Integer.parseInt(kb.nextLine());
            System.out.print("[] nhap TCP port: ");
            int TCPport = Integer.parseInt(kb.nextLine());
            System.out.print("[] nhap host addr: ");
            InetAddress hostAddr = InetAddress.getByName(kb.nextLine());
            
            byte[] buffer = new byte[60000];
            File f = new File("testFile\\emotional.mp3");
            FileInputStream fs = new FileInputStream(f);
            int byteRead = fs.read(buffer);
            System.out.println(byteRead);
            fs.close();
            DatagramPacket sPacket = new DatagramPacket(buffer, byteRead, hostAddr, UDPport);
            UDPsocket.send(sPacket);

            buffer = new byte[60000];
            DatagramPacket rPacket = new DatagramPacket(buffer, buffer.length);
            UDPsocket.receive(rPacket);
            String password = new String(rPacket.getData(),0, rPacket.getLength(), "UTF-8");
            System.out.println("[x] da nhan duoc pwd:" +password);

            try (Socket TCPsocket = new Socket(hostAddr, TCPport)) {
                InputStream is = TCPsocket.getInputStream();
                OutputStream os = TCPsocket.getOutputStream();
                PrintWriter printer = new PrintWriter(os,true);
                Scanner scanner = new Scanner(is);
                
                String localHost = InetAddress.getLocalHost().toString();
                printer.println(localHost);
                System.out.println("[x] da gui localhost:" +localHost);

                String reversePwd = new StringBuilder(password).reverse().toString();
                printer.println(reversePwd);
                System.out.println("[x] da gui password:" +reversePwd);

                System.out.println("[x] Server check pwd:" +scanner.nextLine());

                printer.close();
                scanner.close();
                TCPsocket.close();
            } catch (Exception e) {
            System.out.println("[TCP_] loi socket: "+e);
            }
            
            kb.close();
            UDPsocket.close();
        } catch (Exception e) {
            System.out.println("[UDP] loi socket: "+e);
        }
    }
    
}