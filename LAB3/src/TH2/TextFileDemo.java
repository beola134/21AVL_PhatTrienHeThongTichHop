package TH2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextFileDemo {

    public static void main(String[] args) {
        //Tạo đường dẫn file
        Path file = Path.of("data", "ghi_chu.txt");
        try {
            //Tạo thư mục data
            Files.createDirectories(file.getParent());
            //mở file ghichu.txt để ghi với mã UTF_8
            try (BufferedWriter writer = Files.newBufferedWriter(
                    file, StandardCharsets.UTF_8)) {
                writer.write("Java I/O làm việc với các luồng dữ liệu.");
                writer.newLine();
                writer.write("BufferedWriter giúp ghi văn bản hiệu quả.");
                writer.newLine();
                writer.write("UTF-8 hỗ trợ tiếng Việt ổn định.");
            }
            //Mở file để đọc từng dòng sự dụng UTF-8 
            try (BufferedReader reader = Files.newBufferedReader(
                    file, StandardCharsets.UTF_8)) {
                String line;
                int number = 1;
                //đọc từng dòng  // readLine() trả về null khi đã hết file
                while ((line = reader.readLine()) != null) {
                    // In số thứ tự và nội dung dòng ra màn hình    // number++: in xong thì tăng số thứ tự lên 1
                    System.out.printf("%d. %s%n", number++, line);
                }
            }

        } catch (IOException e) {
            System.err.println("Lỗi xử lý tệp " + file + ": "
                    + e.getMessage());
        }
    }
}
