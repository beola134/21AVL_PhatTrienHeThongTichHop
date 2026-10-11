package bai8;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;

public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) throws Exception {

        String filePath = "data\\upload\\test.txt";
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Khong tim thay file: " + filePath);
            return;
        }

        try (Socket socket = new Socket(HOST, PORT)) {
            DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            // Tên file
            String fileName = file.getName();
            // Kích thước file
            long fileSize = file.length();
            // Tính SHA-256
            String hash = calculateSHA256(file);

            System.out.println("File: " + fileName);
            System.out.println("Size: " + fileSize);
            System.out.println("SHA-256: " + hash);

            // Gửi thông tin file
            output.writeUTF(fileName);
            output.writeLong(fileSize);
            output.writeUTF(hash);

            // Gửi dữ liệu file
            try (
                    FileInputStream fileInput = new FileInputStream(file);

                    BufferedInputStream fileReader = new BufferedInputStream(fileInput)) {

                byte[] buffer = new byte[8192];

                int count;

                while ((count = fileReader.read(buffer)) != -1) {

                    output.write(buffer, 0, count);
                }
            }

            output.flush();

            // Nhận kết quả từ Server
            String response = input.readUTF();

            System.out.println("Server: " + response);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private static String calculateSHA256(File file)
            throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (
                FileInputStream input = new FileInputStream(file);
                BufferedInputStream reader = new BufferedInputStream(input)) {
            byte[] buffer = new byte[8192];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                digest.update(buffer, 0, count);
            }
        }

        byte[] hash = digest.digest();
        StringBuilder result = new StringBuilder();
        for (byte b : hash) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}