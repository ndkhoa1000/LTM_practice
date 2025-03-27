package buoi3.bai1;

import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ex31Client {

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress host = InetAddress.getByName("localhost");
            int port = 4000;
            byte[] buffer = new byte[60000];
            Scanner kb = new Scanner(System.in);
            
            System.out.print("[] nhap ten file: ");
            String msg = kb.nextLine();
            System.out.print("[] dia chi luu: ");
            String savefile = kb.nextLine();
            
            byte[] data = msg.getBytes();
            DatagramPacket sPacket = new DatagramPacket(data, data.length, host, port);
            socket.send(sPacket);
            
            DatagramPacket rPacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(rPacket);
            buffer = rPacket.getData();
            int length = rPacket.getLength();

            if(length >0){
                File f = new File(savefile);
                FileOutputStream fs = new FileOutputStream(f);
                fs.write(buffer, 0, length);
                System.out.println("[x] Ghi file thanh cong!");
                fs.close();
            } else System.out.println("file khong ton tai.");

        kb.close();
        socket.close();
        } catch (Exception e) {
            System.out.println("[!] loi khoi tao socket: " + e);
        }
    }
}
