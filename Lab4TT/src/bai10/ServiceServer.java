package bai10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServiceServer {
    public static final int UDP_DISCOVERY_PORT = 8888;
    public static final String SERVICE_NAME = "FileTransferService";
    public static final String SERVICE_VERSION = "v1.0.0";

    private final int tcpPort;

    public ServiceServer(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public void start() {
        // 1. Khởi chạy TCP Server trong một Thread riêng biệt
        new Thread(this::startTcpServer).start();

        // 2. Khởi chạy UDP Discovery Listener để trả lời tin nhắn Broadcast
        startUdpDiscoveryListener();
    }

    private void startTcpServer() {
        try (ServerSocket serverSocket = new ServerSocket(tcpPort)) {
            System.out.println("[TCP Server] Đang lắng nghe dịch vụ tại cổng TCP " + tcpPort + "...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleTcpClient(socket)).start();
            }
        } catch (IOException e) {
            System.err.println("[TCP Server] Lỗi: " + e.getMessage());
        }
    }

    private void handleTcpClient(Socket socket) {
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            String clientMsg = reader.readLine();
            System.out.println("[TCP Server - Port " + tcpPort + "] Nhận từ client: " + clientMsg);

            // Phản hồi lại client qua kết nối TCP
            writer.write("HELLOTCP Response from " + SERVICE_NAME + " (Port " + tcpPort + "): Welcome!");
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            // Client ngắt kết nối
        }
    }

    private void startUdpDiscoveryListener() {
        try (DatagramSocket socket = new DatagramSocket(UDP_DISCOVERY_PORT)) {
            socket.setReuseAddress(true);
            System.out.println("[UDP Discovery] Đang lắng nghe Broadcast tại cổng UDP " + UDP_DISCOVERY_PORT + "...");

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String request = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8).trim();

                // Kiểm tra cú pháp yêu cầu DISCOVER_SERVICE
                if ("DISCOVER_SERVICE".equals(request)) {
                    // Cấu trúc phản hồi: SERVICE <tên_dịch_vụ> <tcp_port> <phiên_bản>
                    String responseStr = String.format("SERVICE %s %d %s", SERVICE_NAME, tcpPort, SERVICE_VERSION);
                    byte[] responseData = responseStr.getBytes(StandardCharsets.UTF_8);

                    DatagramPacket responsePacket = new DatagramPacket(
                            responseData,
                            responseData.length,
                            packet.getAddress(),
                            packet.getPort());

                    socket.send(responsePacket);
                    System.out.println("[UDP Discovery] Đã trả lời tới " + packet.getAddress().getHostAddress()
                            + ":" + packet.getPort() + " -> " + responseStr);
                }
            }
        } catch (IOException e) {
            System.err.println("[UDP Discovery] Lỗi: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Có thể truyền cổng TCP qua tham số dòng lệnh (mặc định là 9090)
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 9090;
        ServiceServer server = new ServiceServer(port);
        server.start();
    }
}