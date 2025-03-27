package exam2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class UDPThread extends Thread{
    int UDPport;
    int TCPport;
    String password;
    public UDPThread (int UDPport,int TCPport, String password){
        this.UDPport = UDPport;
        this.TCPport = TCPport;
        this.password = password;
    }
    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(UDPport)) {
            System.out.println("[UDP] cho client ket noi...");
            byte[] buffer = new byte[60000];
        
            DatagramPacket rPacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(rPacket);
            SocketAddress clientAddress = rPacket.getSocketAddress();
            int length = rPacket.getLength();
            System.out.println("[UDP] client's packet length: " + length);

            buffer = password.getBytes();
            DatagramPacket sPacket = new DatagramPacket(buffer,buffer.length, clientAddress);
            socket.send(sPacket);
            System.out.println("[UDP] da gui password.");
            socket.close();
        } catch (Exception e) {
            System.out.println("[!UDP] loi khoi tao socket: " + e);
        }
    }
}
