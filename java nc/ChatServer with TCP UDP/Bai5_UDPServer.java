import java.io.*;
import java.net.*;

public class Bai5_UDPServer {
    public static void main(String[] args) {
        int port = 6000;
        byte[] receiveBuffer = new byte[1024];
        
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("=== UDP Server (Bai 5) ===");
            System.out.println("Server dang lang nghe tren cong " + port + "...");

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(
                    receiveBuffer, receiveBuffer.length);

                serverSocket.receive(receivePacket);

                String message = new String(
                    receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                System.out.println("---------------------------------------");
                System.out.println("Nhan goi tin tu Client:");
                System.out.println("  Dia chi IP : " + clientAddress.getHostAddress());
                System.out.println("  Cong (Port): " + clientPort);
                System.out.println("  Noi dung   : " + message);
                System.out.println("---------------------------------------");

                String response = "Server da nhan: \"" + message + "\"";
                byte[] sendBuffer = response.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer, sendBuffer.length,
                    clientAddress, clientPort);

                serverSocket.send(sendPacket);
                System.out.println("Da gui phan hoi cho Client.\n");
            }

        } catch (IOException e) {
            System.err.println("Loi UDP Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
