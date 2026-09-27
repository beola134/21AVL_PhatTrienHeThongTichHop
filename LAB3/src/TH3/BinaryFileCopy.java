package TH3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class BinaryFileCopy {
    private static final int BUFFER_SIZE = 8192; // Kích thước Bộ đệm

    public static void main(String[] args) {
        // KT 2 tham số truyền vào gồm file nguồn cần chép và file cần tạo ra
        if (args.length != 2) {
            System.out.println("Cach dung: java BinaryFileCopy <nguon> <dich>");
            return;
        }
        // Tạo đường dẫn file và tham số truyền vào gồm file gốc và file sao chép
        Path source = Path.of(args[0]);
        Path target = Path.of(args[1]);
        long totalBytes = 0;
        // MỞ LUỒNG ĐỌC/GHI FILE (TỰ ĐỘNG ĐÓNG LUỒNG KHI XONG)
        try (InputStream input = new BufferedInputStream(
                Files.newInputStream(source));
                OutputStream output = new BufferedOutputStream(
                        Files.newOutputStream(target))) {
            byte[] buffer = new byte[BUFFER_SIZE]; // khay chứa 8kb dữ liệu
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead); // Ghi đúng số byte vừa đọc
                totalBytes += bytesRead; // Cộng dồn dung lượng
            }
            System.out.println("Da sao chep " + totalBytes + " byte.");
        } catch (IOException e) {
            System.err.println("Sao chep that bai " + e.getMessage());
        }
    }
}
// Lệnh chạy
// javac -d out src/TH3/BinaryFileCopy.java
// java -cp out TH3.BinaryFileCopy data\source.jpg data\copy.jpg