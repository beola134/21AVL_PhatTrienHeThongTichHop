package bai9;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Sender {

    private static final String GROUP = "239.255.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            InetAddress group = InetAddress.getByName(GROUP);

            System.out.println("Multicast Sender");
            System.out.println("Group: " + GROUP);
            System.out.println("Port: " + PORT);
            System.out.println("Nhap QUIT de thoat.");

            while (true) {
                System.out.print("Nhap thong bao: ");
                String message = scanner.nextLine();
                byte[] data = message.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(data, data.length, group, PORT);
                socket.send(packet);
                System.out.println("Da gui: " + message);
                if ("QUIT".equalsIgnoreCase(message)) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
}