package bai1;

import java.net.*;

public class HostAndUriInspector {
    public static void main(String[] args) {
        // Xu ly thieu tham so (can truyen dung 2 tham so: hostname va URI)
        if (args.length < 2) {
            System.err.println("Loi: Thieu tham so! Vui long truyen 2 tham so rieng biet: <hostname> <URI>");
            System.err.println(
                    "Vi du: java -cp out bai1.HostAndUriInspector google.com https://www.google.com/search?q=java#top");
            return;
        }

        String hostName = args[0];
        String uriStr = args[1];

        System.out.println("=== THONG TIN HOSTNAME: " + hostName + " ===");
        inspectHost(hostName);

        System.out.println("\n=== THONG TIN URI: " + uriStr + " ===");
        inspectUri(uriStr);
    }

    private static void inspectHost(String hostName) {
        try {
            InetAddress[] addresses = InetAddress.getAllByName(hostName);
            for (InetAddress addr : addresses) {
                String ipType = (addr instanceof Inet4Address) ? "IPv4" : "IPv6";
                boolean isLoopback = addr.isLoopbackAddress();
                boolean isSiteLocal = addr.isSiteLocalAddress();

                System.out.printf("  - IP: %s (%s)%n", addr.getHostAddress(), ipType);
                System.out.printf("    + Loopback: %b%n", isLoopback);
                System.out.printf("    + Site Local: %b%n", isSiteLocal);
            }
        } catch (UnknownHostException e) {
            System.err.println("  - Loi: Khong the phai giai hostname '" + hostName + "' (" + e.getMessage() + ")");
        } catch (SecurityException e) {
            System.err.println("  - Loi bao mat khi phan giai hostname: " + e.getMessage());
        }
    }

    private static void inspectUri(String uriStr) {
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