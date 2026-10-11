package bai6;

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

public class BenchmarkRunner {
    private static final String HOST = "127.0.0.1";
    private static final int MSG_COUNT = 1000; // 1000 thông điệp
    private static final int TIMEOUT_MS = 1000; // Timeout 1 giây
    private static final int NUM_RUNS = 5; // Lặp lại ít nhất 5 lần

    // Tạo thông điệp mẫu có kích thước cố định (256 ký tự 'A')
    private static final String PAYLOAD = "A".repeat(256);

    public static void main(String[] args) throws Exception {
        int payloadBytes = PAYLOAD.getBytes(StandardCharsets.UTF_8).length;

        System.out.println("==================================================");
        System.out.println(" BẮT ĐẦU THỰC NGHIỆM SO SÁNH TCP VÀ UDP ");
        System.out.println(" Môi trường: Localhost (127.0.0.1)");
        System.out.println(" Kích thước thông điệp: " + payloadBytes + " bytes");
        System.out.println(" Socket Timeout: " + TIMEOUT_MS + " ms");
        System.out.println(" Số lượng thông điệp / Lần chạy: " + MSG_COUNT);
        System.out.println("==================================================\n");

        // 1. Thực nghiệm TCP
        System.out.println("--- KẾT QUẢ THỰC NGHIỆM TCP ---");
        long totalTcpTime = 0;
        int totalTcpReceived = 0;

        for (int run = 1; run <= NUM_RUNS; run++) {
            TestResult result = runTcpTest(PAYLOAD);
            System.out.printf("Lần %d: Thời gian = %4d ms | Số phản hồi = %d/%d\n",
                    run, result.timeMs, result.receivedCount, MSG_COUNT);
            totalTcpTime += result.timeMs;
            totalTcpReceived += result.receivedCount;
            Thread.sleep(200); // Tạm dừng ngắn giữa các lần đo
        }

        // 2. Thực nghiệm UDP
        System.out.println("\n--- KẾT QUẢ THỰC NGHIỆM UDP ---");
        long totalUdpTime = 0;
        int totalUdpReceived = 0;

        for (int run = 1; run <= NUM_RUNS; run++) {
            TestResult result = runUdpTest(PAYLOAD);
            System.out.printf("Lần %d: Thời gian = %4d ms | Số phản hồi = %d/%d\n",
                    run, result.timeMs, result.receivedCount, MSG_COUNT);
            totalUdpTime += result.timeMs;
            totalUdpReceived += result.receivedCount;
            Thread.sleep(200);
        }

        // 3. Tổng kết trung bình
        System.out.println("\n==================================================");
        System.out.println(" TỔNG KẾT TRUNG BÌNH TRÊN " + NUM_RUNS + " LẦN CHẠY");
        System.out.printf(" TCP -> Thời gian TB: %.2f ms | Tỷ lệ nhận TB: %.2f%%\n",
                (double) totalTcpTime / NUM_RUNS, ((double) totalTcpReceived / (MSG_COUNT * NUM_RUNS)) * 100);
        System.out.printf(" UDP -> Thời gian TB: %.2f ms | Tỷ lệ nhận TB: %.2f%%\n",
                (double) totalUdpTime / NUM_RUNS, ((double) totalUdpReceived / (MSG_COUNT * NUM_RUNS)) * 100);
        System.out.println("==================================================");
    }

    // Phương pháp đo TCP
    private static TestResult runTcpTest(String message) {
        int receivedCount = 0;
        long startTime = System.currentTimeMillis();

        try (
                Socket socket = new Socket(HOST, TCPServer.PORT);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            socket.setSoTimeout(TIMEOUT_MS);

            for (int i = 0; i < MSG_COUNT; i++) {
                writer.write(message);
                writer.newLine();
                writer.flush();

                String response = reader.readLine();
                if (response != null) {
                    receivedCount++;
                }
            }
        } catch (IOException e) {
            // Xử lý khi xảy ra ngắt kết nối hoặc timeout
        }

        long endTime = System.currentTimeMillis();
        return new TestResult(endTime - startTime, receivedCount);
    }

    // Phương pháp đo UDP
    private static TestResult runUdpTest(String message) {
        int receivedCount = 0;
        long startTime = System.currentTimeMillis();
        byte[] sendData = message.getBytes(StandardCharsets.UTF_8);

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT_MS);
            InetAddress serverAddr = InetAddress.getByName(HOST);
            byte[] receiveBuffer = new byte[1024];

            for (int i = 0; i < MSG_COUNT; i++) {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddr, UDPServer.PORT);
                socket.send(sendPacket);

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                try {
                    socket.receive(receivePacket);
                    receivedCount++;
                } catch (SocketTimeoutException e) {
                    // Xử lý hiện tượng mất gói tin (Packet Loss)
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        return new TestResult(endTime - startTime, receivedCount);
    }

    static class TestResult {
        long timeMs;
        int receivedCount;

        TestResult(long timeMs, int receivedCount) {
            this.timeMs = timeMs;
            this.receivedCount = receivedCount;
        }
    }
}

//javac -d out src/bai6/TCPServer.java src/bai6/UDPServer.java src/bai6/BenchmarkRunner.java
//java -cp out bai6.TCPServer
// java -cp out bai6.UDPServer
// java -cp out bai6.BenchmarkRunner