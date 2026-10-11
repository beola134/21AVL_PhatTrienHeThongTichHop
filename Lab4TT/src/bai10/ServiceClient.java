package bai10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceClient {
    private static final int UDP_DISCOVERY_PORT = 8888;
    private static final int DISCOVERY_TIMEOUT_MS = 2000; // Timeout 2 giây để lắng nghe các server

    // Lớp lưu trữ thông tin Service nhận được
    static class DiscoveredServer {
        String ipAddress;
        String serviceName;
        int tcpPort;
        String version;

        DiscoveredServer(String ipAddress, String serviceName, int tcpPort, String version) {
            this.ipAddress = ipAddress;
            this.serviceName = serviceName;
            this.tcpPort = tcpPort;
            this.version = version;
        }

        @Override
        public String toString() {
            return String.format("Service: %s | Host: %s | TCP Port: %d | Version: %s",
                    serviceName, ipAddress, tcpPort, version);
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" BẮT ĐẦU UDP DISCOVERY - TÌM KIẾM DỊCH VỤ ");
        System.out.println("==================================================");

        List<DiscoveredServer> serverList = discoverServices();

        if (serverList.isEmpty()) {
            System.out.println("\n[!] Không tìm thấy server nào phản hồi trong khoảng thời gian timeout.");
            return;
        }

        // Hiển thị danh sách các server đã phát hiện
        System.out.println("\n--- DANH SÁCH TẤT CẢ SERVER PHÁT HIỆN ĐƯỢC ---");
        for (int i = 0; i < serverList.size(); i++) {
            System.out.printf("[%d] %s\n", (i + 1), serverList.get(i));
        }

        // Thử kết nối TCP tới Server đầu tiên trong danh sách
        DiscoveredServer targetServer = serverList.get(0);
        System.out.println("\n==================================================");
        System.out.println(" KẾT NỐI TCP TỚI SERVER: " + targetServer.ipAddress + ":" + targetServer.tcpPort);
        System.out.println("==================================================");

        connectAndCommunicateTcp(targetServer);
    }

    private static List<DiscoveredServer> discoverServices() {
        List<DiscoveredServer> servers = new ArrayList<>();
        Set<String> uniqueKeys = new HashSet<>(); // Set dùng để loại bỏ phản hồi trùng lặp (IP:Port)

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            socket.setSoTimeout(DISCOVERY_TIMEOUT_MS);

            // Gửi thông điệp DISCOVER_SERVICE tới địa chỉ Broadcast
            byte[] sendData = "DISCOVER_SERVICE".getBytes(StandardCharsets.UTF_8);
            InetAddress broadcastAddr = InetAddress.getByName("255.255.255.255");
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcastAddr,
                    UDP_DISCOVERY_PORT);

            socket.send(sendPacket);
            System.out.println("[Client] Đã gửi broadcast: DISCOVER_SERVICE...");
            System.out.println("[Client] Đang chờ các phản hồi trong " + DISCOVERY_TIMEOUT_MS + " ms...");

            byte[] receiveBuffer = new byte[1024];

            // Vòng lặp thu thập phản hồi cho tới khi SocketTimeoutException xảy ra
            while (true) {
                try {
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    socket.receive(receivePacket);

                    String sourceIp = receivePacket.getAddress().getHostAddress();
                    String responseMsg = new String(receivePacket.getData(), 0, receivePacket.getLength(),
                            StandardCharsets.UTF_8).trim();

                    // Cú pháp phản hồi dự kiến: SERVICE <tên_dịch_vụ> <tcp_port> <phiên_bản>
                    String[] parts = responseMsg.split(" ");
                    if (parts.length >= 4 && "SERVICE".equals(parts[0])) {
                        String serviceName = parts[1];
                        int tcpPort = Integer.parseInt(parts[2]);
                        String version = parts[3];

                        // Khóa định danh duy nhất để tránh nhận lại phản hồi trùng từ cùng 1
                        // IP:TCP_Port
                        String uniqueKey = sourceIp + ":" + tcpPort;

                        if (!uniqueKeys.contains(uniqueKey)) {
                            uniqueKeys.add(uniqueKey);
                            servers.add(new DiscoveredServer(sourceIp, serviceName, tcpPort, version));
                        }
                    }
                } catch (SocketTimeoutException e) {
                    // Hết thời gian chờ thu thập phản hồi
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("[Client] Lỗi UDP Discovery: " + e.getMessage());
        }

        return servers;
    }

    private static void connectAndCommunicateTcp(DiscoveredServer target) {
        try (
                Socket socket = new Socket(target.ipAddress, target.tcpPort);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            // Gửi tin nhắn yêu cầu qua TCP
            writer.write("Hello from Discovery Client!");
            writer.newLine();
            writer.flush();

            // Nhận kết quả phản hồi từ TCP Server
            String response = reader.readLine();
            System.out.println("[TCP Response] " + response);

        } catch (IOException e) {
            System.err.println("[TCP Error] Không thể kết nối tới " + target.ipAddress + ":" + target.tcpPort
                    + " -> " + e.getMessage());
        }
    }
}
// javac -d out src/bai10/ServiceServer.java src/bai10/ServiceClient.java
// java -cp out bai10.ServiceServer 9090
// java -cp out bai10.ServiceServer 9091
// java -cp out bai10.ServiceClient