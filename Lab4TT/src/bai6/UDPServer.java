package bai6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class UDPServer {
    public static final int PORT = 9877;
    public static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("[UDP Server] Đang lắng nghe tại cổng " + PORT + "...");
            byte[] buffer = new byte[BUFFER_SIZE];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);

                // Chuyển dữ liệu mảng byte sang chuỗi ký tự UTF-8
                String receivedMsg = new String(request.getData(), 0, request.getLength(), StandardCharsets.UTF_8);
                byte[] sendData = receivedMsg.getBytes(StandardCharsets.UTF_8);

                // Echo gói tin phản hồi lại client
                DatagramPacket response = new DatagramPacket(
                        sendData,
                        sendData.length,
                        request.getAddress(),
                        request.getPort());
                socket.send(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}