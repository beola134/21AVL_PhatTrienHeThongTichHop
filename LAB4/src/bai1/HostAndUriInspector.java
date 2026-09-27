package bai1;

import java.net.*;

public class HostAndUriInspector {
    public static void main(String[] args) {
        // Kiểm tra xem người dùng có truyền đủ 2 tham số (hostname và URI) không
        if (args.length < 2) {
            System.err.println("Loi: Thieu tham so! Vui long truyen 2 tham so rieng biet: <hostname> <URI>");
            System.err.println(
                    "Vi du: java -cp out bai1.HostAndUriInspector google.com https://www.google.com/search?q=java#top");
            return; // Dừng chương trình nếu thiếu tham số
        }
        // Lấy tham số người dùng nhập
        String hostName = args[0]; // tên miền
        String uriStr = args[1]; // urI

        // Gọi phương thức phan tích thông tin
        System.out.println("=== THONG TIN HOSTNAME: " + hostName + " ===");
        inspectHost(hostName);

        System.out.println("\n=== THONG TIN URI: " + uriStr + " ===");
        inspectUri(uriStr);
    }

    // HÀM PHÂN TÍCH VÀ PHÂN GIẢI IP TỪ HOSTNAME
    private static void inspectHost(String hostName) {
        try {
            // Phân giải tên miền thành danh sách các địa chỉ IP tương ứng
            InetAddress[] addresses = InetAddress.getAllByName(hostName);
            for (InetAddress addr : addresses) {
                // KT thuộc ipv4 hay ipv6,có phải là IP nội bộ máy (127.0.0.1 - Loopback)
                // IP mạng nội bộ/LAN (192.168.x.x, 10.x.x.x...)
                String ipType = (addr instanceof Inet4Address) ? "IPv4" : "IPv6";
                boolean isLoopback = addr.isLoopbackAddress();
                boolean isSiteLocal = addr.isSiteLocalAddress();
                // In địa chỉ và thông số
                System.out.printf("  - IP: %s (%s)%n", addr.getHostAddress(), ipType);
                System.out.printf("    + Loopback: %b%n", isLoopback);
                System.out.printf("    + Site Local: %b%n", isSiteLocal);
            }
        } catch (UnknownHostException e) {
            // ten miền không tồn tại
            System.err.println("  - Loi: Khong the phai giai hostname '" + hostName + "' (" + e.getMessage() + ")");
        } catch (SecurityException e) {
            // Lỗi bảo mật
            System.err.println("  - Loi bao mat khi phan giai hostname: " + e.getMessage());
        }
    }

    // PHÂN TÍCH CÁC THÀNH PHẦN CỦA URI
    private static void inspectUri(String uriStr) {
        // Mỗi dòng lệnh sử dụng Toán tử 3 ngôi (dieu_kien ? giatri_1 : giatri_2) để
        // kiểm tra: nếu thành phần đó tồn tại thì in ra, ngược lại sẽ in thông
        // báo"Khong co".
        try {
            URI uri = new URI(uriStr);
            System.out.println("  - Scheme   : " + (uri.getScheme() != null ? uri.getScheme() : "Khong co"));
            System.out.println("  - Host     : " + (uri.getHost() != null ? uri.getHost() : "Khong co"));
            System.out.println("  - Port     : " + (uri.getPort() != -1 ? uri.getPort() : "Mac dinh / Khong co"));
            System.out.println("  - Path     : "
                    + (uri.getPath() != null && !uri.getPath().isEmpty() ? uri.getPath() : "Khong co"));
            System.out.println("  - Query    : " + (uri.getQuery() != null ? uri.getQuery() : "Khong co"));
            System.out.println("  - Fragment : " + (uri.getFragment() != null ? uri.getFragment() : "Khong co"));
        } catch (URISyntaxException e) {
            System.err.println("  - Loi: URI khong hop le. Chi tiet: " + e.getMessage());
        }
    }
}
// javac -d out src/bai1/HostAndUriInspector.java
// java -cp out bai1.HostAndUriInspector google.com https://www.google.com/search?q=java#top//