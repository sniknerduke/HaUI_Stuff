import java.io.*;
import java.net.*;

public class Bai1_TCPServer {
    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("=== TCP Server (Bai 1) ===");
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

                InputStream input = clientSocket.getInputStream();
                byte[] buffer = new byte[1024];
                int bytesRead = input.read(buffer);
                if (bytesRead > 0) {
                    String message = new String(buffer, 0, bytesRead);
                    System.out.println("Du lieu nhan duoc: " + message);
                }

                OutputStream output = clientSocket.getOutputStream();
                String response = "Server da nhan ket noi tu " + clientAddress.getHostAddress() + ":" + clientPort;
                output.write(response.getBytes());
                output.flush();

                clientSocket.close();
                System.out.println("Da dong ket noi voi Client.\n");
            }

        } catch (IOException e) {
            System.err.println("Loi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
