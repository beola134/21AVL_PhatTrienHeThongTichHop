package bai2;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NumberClient {
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int PORT = 9999;

    public static void main(String[] args) {
        System.out.println("Ket noi toi Server " + SERVER_HOST + ":" + PORT + "...");
        try (
                Socket socket = new Socket(SERVER_HOST, PORT);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
                Scanner scanner = new Scanner(System.in)) {
            System.out.println("Da ket noi thanh cong! Nhap ky tu tu 0-9 hoac QUIT de thoat.");

            while (true) {
                System.out.print("Client nhap: ");
                String input = scanner.nextLine();

                // Gui lenh toi server
                writer.write(input + "\n");
                writer.flush();

                // Nhan phan hoi tu server
                String response = reader.readLine();
                if (response == null) {
                    System.out.println("Server da ngat ket noi.");
                    break;
                }

                System.out.println("Server ph hoi: " + response);

                if ("TAMBIET".equalsIgnoreCase(response) || "QUIT".equalsIgnoreCase(input)) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Loi Client: " + e.getMessage());
        }
    }
}