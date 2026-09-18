package TH5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryManager {

    // Danh sách lưu các đối tượng Product
    private List<Product> products = new ArrayList<>();

    //Nhập danh sách sản phẩm
    public void inputProducts(Scanner sc) {
        int n;
        // Nhập số lượng sản phẩm
        while (true) {
            try {
                System.out.print("Nhập số lượng sản phẩm: ");
                n = Integer.parseInt(sc.nextLine());
                // Kiểm tra số lượng không được âm
                if (n < 0) {
                    System.out.println("Lỗi: Số lượng sản phẩm không được âm!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số nguyên!");
            }
        }

        // Nhập danh sách sản phẩm từ bàn phím; mã và tên không rỗng, đơn giá lớn hơn 0, số lượng khôngâm.
        for (int i = 1; i <= n; i++) {
            System.out.println("\n----- Sản phẩm " + i + " -----");
            while (true) {
                try {
                    System.out.print("Nhập mã: ");
                    String id = sc.nextLine().trim();
                    System.out.print("Nhập tên: ");
                    String name = sc.nextLine().trim();
                    System.out.print("Nhập đơn giá: ");
                    double price = Double.parseDouble(sc.nextLine());
                    System.out.print("Nhập số lượng: ");
                    int quantity = Integer.parseInt(sc.nextLine());
                    if (id.isEmpty()) {
                        throw new IllegalArgumentException("Mã sản phẩm không được rỗng!");
                    }
                    if (name.isEmpty()) {
                        throw new IllegalArgumentException("Tên sản phẩm không được rỗng!");
                    }
                    if (price <= 0) {
                        throw new IllegalArgumentException("Đơn giá phải lớn hơn 0!");
                    }
                    if (quantity < 0) {
                        throw new IllegalArgumentException("Số lượng không được âm!");
                    }
                    products.add(new Product(id, name, price, quantity));

                    System.out.println("Thêm sản phẩm thành công!");
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Dữ liệu số không hợp lệ!");

                } catch (IllegalArgumentException e) {
                    System.out.println("Lỗi: " + e.getMessage());
                }
            }
        }
    }

    // Lưu danh sách vào data/inventory.csv bằng UTF-8.
    public void saveToCSV(String fileName) {
        Path path = Paths.get(fileName);
        try {
            // Tạo thư mục data nếu chưa tồn tại
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            // Mở file để ghi bằng UTF-8 try-with-resources tự động đóng file
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                // Ghi dòng tiêu đề
                writer.write("id,name,price,quantity");
                writer.newLine();
                // Ghi từng sản phẩm
                for (Product p : products) {
                    writer.write(p.getId() + "," + p.getName() + "," + p.getPrice() + "," + p.getQuantity());
                    writer.newLine();
                }
            }
            System.out.println("Đã lưu danh sách vào: " + fileName);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi tệp " + fileName + ": " + e.getMessage());
        }
    }

    // Đọc lại tệp CSV và tái tạo danh sách đối tượng Product.
    public void loadFromCSV(String fileName) {
        // Xóa danh sách cũ 
        // products.clear();
        Path path = Paths.get(fileName);
        try {
            // Mở file đọc bằng UTF-8
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String line;
                int lineNumber = 0;
                // Đọc dòng tiêu đề
                line = reader.readLine();
                lineNumber++;
                // Kiểm tra file rỗng
                if (line == null) {
                    System.out.println("Tệp " + fileName + " đang rỗng!");
                    return;
                }
                // Đọc từng dòng cho đến hết file
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    // Bỏ qua dòng trống
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    // Tách dòng CSV thành các cột
                    String[] data = line.split(",", -1);
                    // CSV phải có đúng 4 cột //Xử lý dòng CSV thiếu cột
                    if (data.length != 4) {
                        System.out.println(
                                "Lỗi tệp " + fileName
                                + ", dòng " + lineNumber
                                + ": phải có 4 cột. Bỏ qua dòng."
                        );
                        continue;
                    }
                    try {
                        // Lấy dữ liệu từ CSV
                        String id = data[0].trim();
                        String name = data[1].trim();
                        double price = Double.parseDouble(data[2].trim());
                        int quantity = Integer.parseInt(data[3].trim());
                        // Kiểm tra mã tên giá so lượng
                        if (id.isEmpty()) {
                            throw new IllegalArgumentException("Mã sản phẩm rỗng");
                        }
                        if (name.isEmpty()) {
                            throw new IllegalArgumentException("Tên sản phẩm rỗng");
                        }
                        if (price <= 0) {
                            throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
                        }
                        if (quantity < 0) {
                            throw new IllegalArgumentException("Số lượng không được âm");
                        }
                        // Tạo  Product
                        Product product = new Product(id, name, price, quantity);
                        // Thêm vào danh sách
                        products.add(product);
                    } catch (NumberFormatException e) {
                        // Dữ liệu số trong CSV không hợp lệ //Xử lý dữ liệu số không hợp lệ
                        System.out.println(
                                "Lỗi tệp " + fileName
                                + ", dòng " + lineNumber
                                + ": dữ liệu số không hợp lệ. Bỏ qua dòng."
                        );

                    } catch (IllegalArgumentException e) {
                        // Dữ liệu không đúng điều kiện
                        System.out.println(
                                "Lỗi tệp " + fileName
                                + ", dòng " + lineNumber
                                + ": " + e.getMessage()
                                + ". Bỏ qua dòng."
                        );
                    }
                }
            }

            System.out.println("Đã đọc dữ liệu từ: " + fileName);
            //Xử lý tệp không tồn tại
        } catch (NoSuchFileException e) {
            System.out.println("Không tìm thấy tệp: " + fileName);
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc tệp " + fileName + ": " + e.getMessage());
        }
    }

    // Hiển thị toàn bộ sản phẩm và tổng giá trị tồn kho
    public void displayProducts() {
        System.out.println("\n========== DANH SÁCH SẢN PHẨM ==========");
        // Kiểm tra danh sách rỗng
        if (products.isEmpty()) {
            System.out.println("Danh sách sản phẩm đang rỗng!");
            return;
        }
        // Hiển thị từng sản phẩm
        for (Product p : products) {
            System.out.println(p);
        }
        // Hiển thị tổng giá trị tồn kho
        System.out.printf("\nTổng giá trị tồn kho: %.2f%n", getTotalInventoryValue());
    }

    // Tính tổng giá trị tồn kho
    public double getTotalInventoryValue() {
        double total = 0;
        // Cộng giá trị tồn kho của từng sản phẩm
        for (Product p : products) {
            total += p.getTotalValue();
        }
        return total;
    }

    // Tìm sản phẩm có giá trị tồn kho cao nhất
    public Product getHighestValueProduct() {
        // Nếu danh sách rỗng thì trả về null
        if (products.isEmpty()) {
            return null;
        }
        //chọn sản phẩm đầu tiên
        Product max = products.get(0);
        // So sánh với các sản phẩm còn lại
        for (Product p : products) {
            if (p.getTotalValue() > max.getTotalValue()) {
                max = p;
            }
        }

        return max;
    }

    // Ghi báo cáo tổng hợp vào data/inventory-report.txt.
    public void writeReport(String fileName) {
        Path path = Paths.get(fileName);
        try {
            // Tạo thư mục data nếu chưa có
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            // Mở file để ghi bằng UTF-8
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                // Ghi tiêu đề
                writer.write("========== BÁO CÁO TỒN KHO ==========");
                writer.newLine();
                writer.newLine();
                // Ghi danh sách sản phẩm
                writer.write("Danh sách sản phẩm:");
                writer.newLine();
                for (Product p : products) {
                    writer.write(p.toString());
                    writer.newLine();
                }
                writer.newLine();
                // Ghi tổng giá trị tồn kho
                writer.write(
                        String.format(
                                "Tổng giá trị tồn kho: %.2f",
                                getTotalInventoryValue()
                        )
                );
                writer.newLine();
                writer.newLine();
                // Tìm sản phẩm có giá trị cao nhất
                Product max = getHighestValueProduct();
                if (max != null) {
                    writer.write("Sản phẩm có giá trị tồn kho cao nhất:");
                    writer.newLine();
                    writer.write(max.toString());
                    writer.newLine();
                } else {
                    writer.write("Không có sản phẩm trong kho.");
                    writer.newLine();
                }
            }
            System.out.println("Đã ghi báo cáo vào: " + fileName);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi tệp " + fileName + ": " + e.getMessage());
        }
    }
}
