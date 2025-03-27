package buoi3.bai1;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class ex31Server {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(4000)) {
            byte[] buffer = new byte[60000];
        
            DatagramPacket rPacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(rPacket);
            SocketAddress clientAddress = rPacket.getSocketAddress();
            String filepath = new String (rPacket.getData(),0, rPacket.getLength(), "UTF-8");

            File f = new File(filepath);
            if(f.isFile()){
                try {
                    byte[] large_buffer = new byte[4000000];
                    FileInputStream fs = new FileInputStream(f);
                    DataInputStream dis = new DataInputStream(fs);
                    int byteRead = dis.read(large_buffer);
                    DatagramPacket sPacket = new DatagramPacket(large_buffer, byteRead, clientAddress);
                    socket.send(sPacket);
                    System.out.println("truyen file thanh cong! so byte: "+ byteRead);
                    dis.close();
                } catch (Exception e) {
                    System.out.println("[!] loi truyen file: "+e);
                }
            } else{
                DatagramPacket sPacket = new DatagramPacket(buffer, 0, clientAddress);
                socket.send(sPacket);
            }
        } catch (Exception e) {
            System.out.println("[!] loi khoi tao socket: " + e);
        }
    }
}
