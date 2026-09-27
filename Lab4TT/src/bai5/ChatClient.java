package bai5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

                Scanner scanner = new Scanner(System.in)) {

            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println("\n" + message);
                        System.out.print("> ");
                    }
                } catch (IOException e) {
                    System.out.println("\nMat ket noi voi server.");
                }
            });

            receiveThread.start();

            while (scanner.hasNextLine()) {

                String message = scanner.nextLine();
                writer.write(message);
                writer.newLine();
                writer.flush();
                if (message.equalsIgnoreCase("QUIT")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Khong the ket noi server: " + e.getMessage());
        }
    }
}