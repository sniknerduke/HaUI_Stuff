package server;

import dao.TaiKhoanDAO;
import entity.TaiKhoan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    private static final int PORT = 9999;
    private static TaiKhoanDAO dao;

    public static void main(String[] args) {
        // Khởi tạo DAO
        dao = new TaiKhoanDAO();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT + "...");
            System.out.println("Waiting for client connections...");
            
            // Vòng lặp chấp nhận kết nối từ Client
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress().getHostAddress());
                
                // Xây dựng luồng xử lý (Thread)
                new Thread(new ClientHandler(socket, dao)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private TaiKhoanDAO dao;

    public ClientHandler(Socket socket, TaiKhoanDAO dao) {
        this.socket = socket;
        this.dao = dao;
    }

    @Override
    public void run() {
        try (
            // Đọc dữ liệu từ InputStream và ghi dữ liệu ra OutputStream
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String request;
            while ((request = in.readLine()) != null) {
                System.out.println("Received from client: " + request);
                
                // Bộ phân giải thông điệp (Message Parser)
                // Cấu trúc gói tin: COMMAND|param1|param2...
                String[] parts = request.split("\\|");
                String command = parts[0];

                switch (command) {
                    case "LOGIN":
                        if (parts.length >= 3) {
                            boolean loginSuccess = dao.login(parts[1], parts[2]);
                            out.println(loginSuccess ? "SUCCESS" : "FAILED");
                        } else {
                            out.println("INVALID_FORMAT");
                        }
                        break;

                    case "CREATE":
                        if (parts.length >= 3) {
                            boolean createSuccess = dao.createAccount(new TaiKhoan(parts[1], parts[2]));
                            out.println(createSuccess ? "SUCCESS" : "FAILED");
                        } else {
                            out.println("INVALID_FORMAT");
                        }
                        break;

                    case "READ":
                        if (parts.length >= 2) {
                            TaiKhoan tk = dao.readAccount(parts[1]);
                            out.println(tk != null ? "EXISTS|" + tk.getPassword() : "NOT_FOUND");
                        } else {
                            out.println("INVALID_FORMAT");
                        }
                        break;

                    case "UPDATE":
                        if (parts.length >= 3) {
                            boolean updateSuccess = dao.updateAccount(new TaiKhoan(parts[1], parts[2]));
                            out.println(updateSuccess ? "SUCCESS" : "FAILED");
                        } else {
                            out.println("INVALID_FORMAT");
                        }
                        break;

                    case "DELETE":
                        if (parts.length >= 2) {
                            boolean deleteSuccess = dao.deleteAccount(parts[1]);
                            out.println(deleteSuccess ? "SUCCESS" : "FAILED");
                        } else {
                            out.println("INVALID_FORMAT");
                        }
                        break;

                    case "EXIT":
                        System.out.println("Exit command received. Shutting down server...");
                        out.println("BYE");
                        // Thiết lập lệnh ngắt kết nối: đóng Server và ứng dụng
                        System.exit(0);
                        break;

                    default:
                        out.println("UNKNOWN_COMMAND");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected or error occurred: " + e.getMessage());
        }
    }
}