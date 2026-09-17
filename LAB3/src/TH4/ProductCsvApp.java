package TH4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductCsvApp {

    public static void main(String[] args) {

        // Đường dẫn đến file products.csv
        Path input = Path.of("data", "products.csv");
        // Đường dẫn đến file báo cáo cần tạo
        Path report = Path.of("data", "report.txt");
        // Tạo ArrayList để lưu danh sách sản phẩm đọc được từ CSV
        List<Product> products = new ArrayList<>();
        // Mở file CSV để đọc
        // try-with-resources: đọc xong sẽ tự động đóng file
        try (BufferedReader reader = Files.newBufferedReader(
                input, StandardCharsets.UTF_8)) {
            reader.readLine();  //Bỏ qua dòng tiêu đề
            String line;
            int lineNumber = 1; //Đánh số dòng torng file
            // Đọc từng dòng cho đến khi hết file
            while ((line = reader.readLine()) != null) {
                // Tăng số dòng lên 1 nếu dòng trống thì bỏ qua
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                // Tách dữ liệu trong dòng dựa vào dấu phẩy ","
                // Ví dụ: SP01,Chuột,150000,10
                // sẽ thành:parts[0] = SP01 parts[1] = Chuột parts[2] = 150000 parts[3] = 10
                String[] parts = line.split(",", -1);

                // Mỗi sản phẩm phải có đúng 4 thông tin nếu dòng trống thì bỏ qua
                if (parts.length != 4) {
                    System.err.println("Bỏ qua dòng " + lineNumber);
                    continue;
                }
                try {
                    // Tạo một đối tượng Product từ dữ liệu CSV
                    products.add(new Product(
                            //LÀ CHUỖI
                            parts[0].trim(),
                            parts[1].trim(),
                            Double.parseDouble(parts[2].trim()), //Chuyển sang double
                            Integer.parseInt(parts[3].trim()) //Chuyển sang int
                    ));

                } catch (IllegalArgumentException e) {
                    // Nếu dữ liệu không đúng kiểu
                    System.err.println("Dòng " + lineNumber + " không hợp lệ: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            // Nếu không mở/đọc được file CSV thì thông báo lỗi và kết thúc chương trình
            System.err.println("Không đọc được CSV: " + e.getMessage());
            return;
        }
        // Biến lưu tổng giá trị của toàn bộ hàng tồn kho
        double total = 0;
        // Duyệt qua từng sản phẩm trong danh sách
        for (Product product : products) {
            System.out.println(product);
            // Tính giá trị tồn kho của sản phẩm rồi cộng vào tổng
            total += product.inventoryValue();
        }

        // Mở file report.txt để ghi báo cáo
        try (BufferedWriter writer = Files.newBufferedWriter(
                report, StandardCharsets.UTF_8)) {
            writer.write("Số sản phẩm: " + products.size());
            writer.newLine(); //xuống dòng
            // Ghi tổng giá trị tồn kho 
            writer.write("Tổng giá trị tồn kho: %,.0f VND".formatted(total));
            writer.newLine();

        } catch (IOException e) {
            // Nếu không ghi được file báo cáo thì thông báo lỗi
            System.err.println(
                    "Không ghi được báo cáo: " + e.getMessage()
            );
        }
    }
}
