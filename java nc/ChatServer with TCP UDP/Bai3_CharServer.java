import java.io.*;
import java.net.*;

public class Bai3_CharServer {
    public static void main(String[] args) {
        int port = 5001;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("=== TCP Server - Character Stream (Bai 3) ===");
            System.out.println("Server dang lang nghe tren cong " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();

                InetAddress clientAddress = clientSocket.getInetAddress();
                int clientPort = clientSocket.getPort();
                System.out.println("---------------------------------------");
                System.out.println("Client da ket noi!");
                System.out.println("  Dia chi IP : " + clientAddress.getHostAddress());
                System.out.println("  Cong (Port): " + clientPort);
                System.out.println("---------------------------------------");

                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);

                String message = in.readLine();
                if (message != null) {
                    System.out.println("Du lieu nhan duoc: " + message);

                    String response = "Server da nhan: \"" + message + "\" tu "
                        + clientAddress.getHostAddress() + ":" + clientPort;
                    out.println(response);
                    System.out.println("Da gui phan hoi cho Client.");
                }

                clientSocket.close();
                System.out.println("Da dong ket noi voi Client.\n");
            }

        } catch (IOException e) {
            System.err.println("Loi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
