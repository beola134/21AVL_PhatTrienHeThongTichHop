package bai3;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DateTimeTcpClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 9998;

        System.out.println("Dang ket noi TCP toi Server " + host + ":" + port + "...");
        try (
                Socket socket = new Socket(host, port);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
                Scanner scanner = new Scanner(System.in)) {
            System.out.println("Ket noi TCP thanh cong! Nhap lenh (DATE, TIME, DATETIME, QUIT):");
            while (true) {
                System.out.print("TCP Client nhap > ");
                String cmd = scanner.nextLine();

                writer.write(cmd + "\n");
                writer.flush();

                String res = reader.readLine();
                if (res == null) {
                    System.out.println("Server da ngat ket noi dot ngot!");
                    break;
                }
                System.out.println("Server phan hoi: " + res);

                if ("TAMBIET".equalsIgnoreCase(res) || "QUIT".equalsIgnoreCase(cmd)) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Loi TCP Client: " + e.getMessage());
        }
    }
}