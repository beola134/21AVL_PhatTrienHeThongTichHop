package TH2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class morong {

    public static void main(String[] args) {
        // Tạo đường dẫn: data/ghi_chu.txt
        Path file = Path.of("data", "ghi_chu.txt");
        try {
            // Tạo thư mục data
            Files.createDirectories(file.getParent());
            // Mở file để ghi thêm nội dung// CREATE: tạo file nếu file chưa tồn tại// APPEND: ghi thêm vào cuối file, không xóa nội dung cũ
            try (BufferedWriter writer = Files.newBufferedWriter(
                    file, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                writer.write("Java I/O làm việc với các luồng dữ liệu.");
                writer.newLine();
                writer.write("BufferedWriter giúp ghi văn bản hiệu quả.");
                writer.newLine();
                writer.write("UTF-8 hỗ trợ tiếng Việt ổn định.");
                writer.newLine();
            }
            // In đường dẫn tuyệt đối của file
            System.out.println("Đường dẫn tuyệt đối: "
                    + file.toAbsolutePath());
            // Mở file để đọc bằng UTF-8
            try (BufferedReader reader = Files.newBufferedReader(
                    file, StandardCharsets.UTF_8)) {
                String line;
                int number = 1;
                // Đọc từng dòng cho đến khi hết file
                while ((line = reader.readLine()) != null) {
                    // In số thứ tự và nội dung dòng
                    System.out.printf("%d. %s%n", number++, line);
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi xử lý tệp " + file + ": "
                    + e.getMessage());
        }
    }
}
