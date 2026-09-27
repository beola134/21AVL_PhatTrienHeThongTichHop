package bai4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {

    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server dang chay tai port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client da ket noi: " + socket.getInetAddress());
                xuLyClient(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void xuLyClient(Socket socket) {
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            String request;
            while ((request = reader.readLine()) != null) {
                System.out.println("Client: " + request);
                String response = tinhToan(request);
                writer.write(response);
                writer.newLine();
                writer.flush();
                System.out.println("Server: " + response);
            }
        } catch (IOException e) {
            System.out.println("Client da ngat ket noi.");
        }
    }

    public static String tinhToan(String request) {
        String[] parts = request.trim().split("\\s+");
        if (parts.length != 4) {
            return "ERR INVALID_FORMAT";
        }
        if (!parts[0].equalsIgnoreCase("CALC")) {
            return "ERR INVALID_FORMAT";
        }
        String operator = parts[1];
        if (!operator.equals("+")
                && !operator.equals("-")
                && !operator.equals("*")
                && !operator.equals("/")) {
            return "ERR UNSUPPORTED_OPERATOR";
        }

        double a;
        double b;

        try {
            a = Double.parseDouble(parts[2]);
            b = Double.parseDouble(parts[3]);
        } catch (NumberFormatException e) {
            return "ERR INVALID_NUMBER";
        }

        if (operator.equals("/") && b == 0) {
            return "ERR DIVIDE_BY_ZERO";
        }

        double result;

        switch (operator) {

            case "+":
                result = a + b;
                break;

            case "-":
                result = a - b;
                break;

            case "*":
                result = a * b;
                break;

            case "/":
                result = a / b;
                break;

            default:
                return "ERR UNSUPPORTED_OPERATOR";
        }

        if (result == (long) result) {
            return "OK " + (long) result;
        }

        return "OK " + result;
    }
}
// javac -d out src/bai4/Server.java src/bai4/Client.java
// java -cp out bai4/Server
// java -cp out bai4.Client