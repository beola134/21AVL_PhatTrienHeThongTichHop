package bai2;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class NumberServer {
    private static final int PORT = 9999;

    public static void main(String[] args) {
        System.out.println("Dang khoi chay TCP Server tren cong " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Da ket noi voi client: " + clientSocket.getRemoteSocketAddress());
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Loi Server: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket) {
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                inputLine = inputLine.trim(); // Loai bo khoang trang thua neu co

                if ("QUIT".equalsIgnoreCase(inputLine)) {
                    writer.write("TAMBIET\n");
                    writer.flush();
                    break;
                }

                String response = translateDigit(inputLine);
                writer.write(response + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println("Loi ket noi client: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Ngat ket noi client.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String translateDigit(String input) {
        // Kiem tra du lieu co phai dung mot chu so tu 0 den 9 khong
        if (input.length() != 1) {
            return "ERR INVALID_DIGIT";
        }

        char c = input.charAt(0);
        switch (c) {
            case '0':
                return "Khong";
            case '1':
                return "Mot";
            case '2':
                return "Hai";
            case '3':
                return "Ba";
            case '4':
                return "Bon";
            case '5':
                return "Nam";
            case '6':
                return "Sau";
            case '7':
                return "Bay";
            case '8':
                return "Tam";
            case '9':
                return "Chin";
            default:
                return "ERR INVALID_DIGIT";
        }
    }
}