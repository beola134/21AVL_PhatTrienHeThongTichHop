package bai8;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;

public class Server {

    private static final int PORT = 5000;
    private static final String UPLOAD_FOLDER = "upload";

    public static void main(String[] args) {

        File folder = new File(UPLOAD_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server dang chay...");
            System.out.println("Port: " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client ket noi: "+ socket.getRemoteSocketAddress());
                handleClient(socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {

        try (
                Socket currentSocket = socket;
                DataInputStream input = new DataInputStream(new BufferedInputStream(currentSocket.getInputStream()));
                DataOutputStream output = new DataOutputStream( new BufferedOutputStream(currentSocket.getOutputStream()))) {
            // Nhận tên file
            String fileName = input.readUTF();
            // Nhận kích thước file
            long fileSize = input.readLong();
            // Nhận SHA-256
            String clientHash = input.readUTF();
            System.out.println("Ten file: " + fileName);
            System.out.println("Kich thuoc: " + fileSize);
            System.out.println("SHA-256: " + clientHash);

            // Không cho phép đường dẫn
            fileName = new File(fileName).getName();

            if (fileName.isEmpty()) {
                output.writeUTF("ERR INVALID_FILENAME");
                output.flush();
                return;
            }

            if (fileSize < 0) {
                output.writeUTF("ERR INVALID_SIZE");
                output.flush();
                return;
            }

            File outputFile = new File(UPLOAD_FOLDER + File.separator + fileName);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (
                    FileOutputStream fileOutput = new FileOutputStream(outputFile);
                    BufferedOutputStream fileWriter = new BufferedOutputStream(fileOutput)) {
                byte[] buffer = new byte[8192];
                long remaining = fileSize;
                while (remaining > 0) {
                    int bytesToRead = (int) Math.min( buffer.length,remaining);
                    int count = input.read( buffer, 0,bytesToRead);
                    if (count == -1) {
                        output.writeUTF("ERR INCOMPLETE_FILE");
                        output.flush();
                        return;
                    }

                    fileWriter.write(buffer, 0, count);
                    digest.update(buffer, 0, count);
                    remaining -= count;
                }
                fileWriter.flush();
            }

            String serverHash = bytesToHex(digest.digest());
            System.out.println("SHA-256 Server: " + serverHash);
            if (serverHash.equalsIgnoreCase(clientHash)) {
                output.writeUTF("OK");
                output.flush();
                System.out.println(
                        "Nhan file thanh cong.");

            } else {
                output.writeUTF("ERR HASH_MISMATCH");
                output.flush();
                System.out.println(
                        "SHA-256 khong trung khop.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String bytesToHex(byte[] bytes) {

        StringBuilder result = new StringBuilder();

        for (byte b : bytes) {

            result.append(
                    String.format("%02x", b));
        }

        return result.toString();
    }
}

// javac -d out src\bai8\Server.java src\bai8\Client.java
// java -cp out bai8.Server
// java -cp out bai8.Client