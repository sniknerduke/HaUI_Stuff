import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Bai5_UDPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 6000;

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== UDP Client (Bai 5) ===");
        System.out.println("(Nhap 'exit' de thoat)\n");

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(serverAddress);

            while (true) {
                System.out.print("Nhap tin nhan gui toi Server: ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Dang thoat...");
                    break;
                }

                byte[] sendBuffer = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer, sendBuffer.length,
                    address, serverPort);

                clientSocket.send(sendPacket);
                System.out.println("Da gui: \"" + message + "\"");

                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(
                    receiveBuffer, receiveBuffer.length);

                clientSocket.receive(receivePacket);

                String response = new String(
                    receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Phan hoi tu Server: " + response);
                System.out.println();
            }

        } catch (IOException e) {
            System.err.println("Loi UDP Client: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();

        System.out.println("Da dong ket noi. Tam biet!");
    }
}
