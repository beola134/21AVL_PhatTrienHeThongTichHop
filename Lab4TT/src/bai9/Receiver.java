package bai9;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;

public class Receiver {

    private static final String GROUP = "239.255.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) {

        try {
            InetAddress group = InetAddress.getByName(GROUP);
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            System.out.println("NetworkInterface: "+ networkInterface.getName());
            MulticastSocket socket = new MulticastSocket(PORT);
            InetSocketAddress groupAddress = new InetSocketAddress(group, PORT);
            socket.joinGroup(groupAddress, networkInterface);
            System.out.println("Receiver dang cho thong bao...");
            System.out.println("Group: " + GROUP);
            System.out.println("Port: " + PORT);

            byte[] buffer = new byte[1024];
            try {
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), packet.getOffset(), packet.getLength(),StandardCharsets.UTF_8);
                    System.out.println("Nhan tu "+ packet.getAddress()+ ": " + message);
                    if ("QUIT".equalsIgnoreCase(message)) {
                        break;
                    }
                }

            } finally {
                // Roi multicast group truoc khi dong socket
                socket.leaveGroup(groupAddress, networkInterface);
                socket.close();
                System.out.println("Da roi multicast group.");
            }

        } catch (IOException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
}

// javac -d out src\bai9\Sender.java src\bai9\Receiver.java
// java -cp out bai9.Receiver
// java -cp out bai9.Receiver
// java -cp out bai9.Sender