import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Bai4_ChatClient extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JLabel statusLabel;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5002;

    public Bai4_ChatClient() {
        setTitle("Chat Client - Bai 4");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainPanel.setBackground(new Color(30, 30, 46));

        statusLabel = new JLabel("Dang ket noi toi Server...");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        statusLabel.setForeground(new Color(255, 200, 50));
        statusLabel.setBorder(new EmptyBorder(0, 4, 8, 0));
        mainPanel.add(statusLabel, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        chatArea.setBackground(new Color(40, 42, 58));
        chatArea.setForeground(new Color(205, 214, 244));
        chatArea.setCaretColor(Color.WHITE);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setMargin(new Insets(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(69, 71, 90), 1));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout(6, 0));
        inputPanel.setBackground(new Color(30, 30, 46));
        inputPanel.setBorder(new EmptyBorder(8, 0, 0, 0));

        messageField = new JTextField();
        messageField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageField.setBackground(new Color(49, 50, 68));
        messageField.setForeground(new Color(205, 214, 244));
        messageField.setCaretColor(Color.WHITE);
        messageField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(69, 71, 90), 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        messageField.setEnabled(false);

        sendButton = new JButton("Gui");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sendButton.setBackground(new Color(166, 227, 161));
        sendButton.setForeground(new Color(30, 30, 46));
        sendButton.setFocusPainted(false);
        sendButton.setBorder(new EmptyBorder(8, 20, 8, 20));
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendButton.setEnabled(false);

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);

        ActionListener sendAction = e -> sendMessage();
        sendButton.addActionListener(sendAction);
        messageField.addActionListener(sendAction);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeConnection();
            }
        });

        setVisible(true);

        new Thread(this::connectToServer).start();
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            String serverInfo = SERVER_ADDRESS + ":" + SERVER_PORT;

            SwingUtilities.invokeLater(() -> {
                statusLabel.setText("Da ket noi toi Server: " + serverInfo);
                statusLabel.setForeground(new Color(166, 227, 161));
                messageField.setEnabled(true);
                sendButton.setEnabled(true);
                messageField.requestFocusInWindow();
            });

            appendChat("[SYSTEM] Da ket noi toi Server: " + serverInfo);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                appendChat("Server: " + message);
            }

            appendChat("[SYSTEM] Server da ngat ket noi.");
            SwingUtilities.invokeLater(() -> {
                statusLabel.setText("Server da ngat ket noi");
                statusLabel.setForeground(new Color(243, 139, 168));
                messageField.setEnabled(false);
                sendButton.setEnabled(false);
            });

        } catch (ConnectException e) {
            appendChat("[LOI] Khong the ket noi toi Server. Hay chac chan Server dang chay!");
            SwingUtilities.invokeLater(() -> {
                statusLabel.setText("Khong the ket noi toi Server");
                statusLabel.setForeground(new Color(243, 139, 168));
            });
        } catch (IOException e) {
            if (!e.getMessage().contains("Socket closed")) {
                appendChat("[LOI] " + e.getMessage());
            }
        }
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty() && out != null) {
            out.println(message);
            appendChat("Client: " + message);
            messageField.setText("");
        }
    }

    private void appendChat(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    private void closeConnection() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(Bai4_ChatClient::new);
    }
}
