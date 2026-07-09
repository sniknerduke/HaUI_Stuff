import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Bai2_TCPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 5000;

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== TCP Client (Bai 2) ===");
        System.out.print("Nhap chuoi ky tu muon gui toi Server: ");
        String message = scanner.nextLine();

        try (Socket socket = new Socket(serverAddress, serverPort)) {
            System.out.println("Da ket noi toi Server " + serverAddress + ":" + serverPort);

            OutputStream output = socket.getOutputStream();
            output.write(message.getBytes());
            output.flush();
            System.out.println("Da gui: \"" + message + "\"");

            InputStream input = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);
            if (bytesRead > 0) {
                String response = new String(buffer, 0, bytesRead);
                System.out.println("Phan hoi tu Server: " + response);
            }

        } catch (IOException e) {
            System.err.println("Loi Client: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
        System.out.println("Ket noi da dong.");
    }
}
