package bai7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class TCPServer {

    private static final int PORT = 5000;

    private static final Pattern CLIENT_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        File folder = new File("data/logs");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("TCP Server dang chay...");
            System.out.println("Port: " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client ket noi: " + socket.getRemoteSocketAddress());
                handleClient(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        String clientId = null;
        try (
                Socket currentSocket = socket;
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(currentSocket.getInputStream(), StandardCharsets.UTF_8))) {

            // Client phải gửi HELLO clientId trước
            String hello = reader.readLine();
            if (hello == null || !hello.startsWith("HELLO ")) {
                System.out.println("Khong nhan duoc HELLO.");
                return;
            }

            clientId = hello.substring(6).trim();
            // Kiểm tra clientId
            if (!CLIENT_ID_PATTERN.matcher(clientId).matches()) {
                System.out.println("ClientId khong hop le: " + clientId);
                return;
            }

            System.out.println("ClientId: " + clientId);
            String message;
            while ((message = reader.readLine()) != null) {
                // Chỉ QUIT mới kết thúc
                if (message.equals("QUIT")) {
                    System.out.println("Client " + clientId + " da thoat.");
                    break;
                }
                saveLog(clientId, currentSocket.getRemoteSocketAddress().toString(), message);
                System.out.println("[" + clientId + "] " + message);
            }
        } catch (IOException e) {
            System.out.println("Client " + clientId + " ngat ket noi.");
        }
    }

    private static void saveLog(
            String clientId,
            String remoteAddress,
            String message) throws IOException {
        File file = new File("data/logs/" + clientId + ".txt");
        String timestamp = LocalDateTime.now().format(FORMATTER);
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file, StandardCharsets.UTF_8, true))) {
            writer.write(timestamp + " | " + remoteAddress + " | " + message);
            writer.newLine();
        }
    }
}

// javac -d out src\bai7\TCPServer.java src\bai7\TCPClient.java
// java -cp out bai7.TCPServer
// java -cp out bai7.TCPClient