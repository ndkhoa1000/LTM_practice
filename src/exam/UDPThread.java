package exam;

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
            String LocalAddr = new String (rPacket.getData(),0, rPacket.getLength(), "UTF-8");
            System.out.println("[UDP] Client ip: " + LocalAddr);
            
            buffer = Integer.toString(TCPport).getBytes();
            DatagramPacket sPacket = new DatagramPacket(buffer, buffer.length, clientAddress);
            socket.send(sPacket);
            System.out.println("[UDP] da gui TCP port.");

            buffer = password.getBytes();
            sPacket = new DatagramPacket(buffer,buffer.length, clientAddress);
            socket.send(sPacket);
            System.out.println("[UDP] da gui password.");
            socket.close();
        } catch (Exception e) {
            System.out.println("[!UDP] loi khoi tao socket: " + e);
        }
    }
}
