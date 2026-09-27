package network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostInspector {
    public static void main(String[] args) {
        // KIỂM TRA THAM SỐ TRUYỀN VÀO TỪ DÒNG LỆNH
        if (args.length != 1) {
            System.out.println("Usage: java network.HostInspector <hostname>");
            return;// Dừng nếu người dùng không truyền đúng 1 hostname (vd: google.com)
        }
        try {
            // PHÂN GIẢI TÊN MIỀN SANG DẠNG ĐỊA CHỈ IP
            // Trả về danh sách tất cả IP cho hostname đó (1 tên miền có thể có nhiều IP)
            InetAddress[] addresses = InetAddress.getAllByName(args[0]);
            System.out.println("Host: " + args[0]);
            // VÒNG LẶP DUYỆT VÀ KIỂM TRA TÍNH CHẤT TỪNG IP
            for (InetAddress address : addresses) {
                System.out.println("- IP: " + address.getHostAddress());// Lấy chuỗi IP đại diện
                System.out.println(" Canonical: " + address.getCanonicalHostName());// Lấy tên miền đầy đủ qua DNS
                System.out.println(" Loopback: " + address.isLoopbackAddress());// Kiểm tra IP vòng lặp máy chủ
                System.out.println(" Site local: " + address.isSiteLocalAddress());// Kiểm tra IP thuộc mạng nội bộ LAN.
            }
        } catch (UnknownHostException e) {
            System.err.println("Khong phan giai duoc host " + args[0]);
        }
    }
}
// javac -d out src/network/HostInspector.java
// java -cp out network.HostInspector localhost
// java -cp out network.HostInspector example.com
// java -cp out network.HostInspector host-khong-ton-tai.invalid