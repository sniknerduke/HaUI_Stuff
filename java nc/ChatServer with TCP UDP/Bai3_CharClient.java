import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Bai3_CharClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 5001;

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== TCP Client - Character Stream (Bai 3) ===");
        System.out.print("Nhap chuoi ky tu muon gui toi Server: ");
        String message = scanner.nextLine();

        try (Socket socket = new Socket(serverAddress, serverPort)) {
            System.out.println("Da ket noi toi Server " + serverAddress + ":" + serverPort);

            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

            out.println(message);
            System.out.println("Da gui: \"" + message + "\"");

            String response = in.readLine();
            if (response != null) {
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
