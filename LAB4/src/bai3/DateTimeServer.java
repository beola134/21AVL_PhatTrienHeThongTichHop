package bai3;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeServer {
    private static final int TCP_PORT = 9998;
    private static final int UDP_PORT = 9997;

    public static void main(String[] args) {
        new Thread(DateTimeServer::startUdpServer).start();
        startTcpServer();
    }

    private static void startTcpServer() {
        System.out.println("TCP Server dang chay tren cong " + TCP_PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[TCP] Da ket noi voi client: " + clientSocket.getRemoteSocketAddress());
                new Thread(() -> handleTcpClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Loi TCP Server: " + e.getMessage());
        }
    }

    private static void handleTcpClient(Socket socket) {
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            String request;
            while ((request = reader.readLine()) != null) {
                request = request.trim().toUpperCase();
                if ("QUIT".equals(request)) {
                    writer.write("TAMBIET\n");
                    writer.flush();
                    break;
                }
                String response = processRequest(request);
                writer.write(response + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println("[TCP] Mat ket noi client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void startUdpServer() {
        System.out.println("UDP Server dang chay tren cong " + UDP_PORT + "...");
        try (DatagramSocket socket = new DatagramSocket(UDP_PORT)) {
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String request = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8).trim()
                        .toUpperCase();
                System.out.println("[UDP] Nhan yeu cau '" + request + "' tu " + packet.getSocketAddress());

                String response = processRequest(request);
                byte[] responseData = response.getBytes(StandardCharsets.UTF_8);

                DatagramPacket responsePacket = new DatagramPacket(
                        responseData, responseData.length,
                        packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
            }
        } catch (IOException e) {
            System.err.println("Loi UDP Server: " + e.getMessage());
        }
    }

    private static String processRequest(String cmd) {
        LocalDateTime now = LocalDateTime.now();
        switch (cmd) {
            case "DATE":
                return now.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
            case "TIME":
                return now.format(DateTimeFormatter.ofPattern("HH mm ss"));
            case "DATETIME":
                return now.format(DateTimeFormatter.ofPattern("dd MM yyyy HH mm ss"));
            default:
                return "ERR INVALID_COMMAND";
        }
    }
}