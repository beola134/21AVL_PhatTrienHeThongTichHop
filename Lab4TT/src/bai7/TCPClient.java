package bai7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) {

        try (
                Socket socket = new Socket(HOST, PORT);
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in,StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8))) {
            System.out.print("Nhap clientId: ");
            String clientId = keyboard.readLine();
            // Gửi HELLO trước
            writer.write("HELLO " + clientId);
            writer.newLine();
            writer.flush();
            System.out.println("Nhap tin nhan. Nhap QUIT de thoat.");
            while (true) {
                System.out.print("> ");
                String message = keyboard.readLine();
                if (message == null) {
                    break;
                }

                writer.write(message);
                writer.newLine();
                writer.flush();

                if (message.equals("QUIT")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}