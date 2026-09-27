package bai4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        String host = "localhost";
        int port = 5000;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
                Scanner scanner = new Scanner(System.in)) {
            System.out.println("Da ket noi Server.");
            System.out.println("Cu phap: CALC <toan tu> <so1> <so2>");
            System.out.println("Nhap: CALC + 100 200");
            System.out.println("Nhap EXIT de thoat.");

            while (true) {
                System.out.print("Client: ");
                String request = scanner.nextLine();
                if (request.equalsIgnoreCase("EXIT")) {
                    break;
                }
                writer.write(request);
                writer.newLine();
                writer.flush();
                String response = reader.readLine();
                System.out.println("Server: " + response);
            }

        } catch (IOException e) {
            System.out.println("Khong the ket noi Server.");
            e.printStackTrace();
        }
    }
}
