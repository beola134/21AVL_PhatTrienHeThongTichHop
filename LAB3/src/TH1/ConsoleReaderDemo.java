package TH1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConsoleReaderDemo {

    public static void main(String[] args) {
        // Tạo đối tượng reader để đọc dữ liệu từ bàn phím
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));
        int count = 0;
        System.out.println("Nhập văn bản; nhập q để kết thúc: ");
        try {
            while (true) {
                //đọc một dòng từ bàn phím
                String line = reader.readLine();
                //nếu nhập q thì dùng chương  trình
                if (line == null || line.equalsIgnoreCase("q")) {
                    break;
                }
                count++;
                System.out.printf("Dòng %d: %s%n", count, line);
            }
        } catch (IOException e) {
            System.err.println("Không thể đọc dữ liệu: " + e.getMessage());
        }
        System.out.println("Tổng số dòng đã nhập: " + count);
    }

}
