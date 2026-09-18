package TH5;

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        // Tạo đối tượng quản lý kho
        InventoryManager manager = new InventoryManager();
        Scanner sc = new Scanner(System.in);
        int choice;
        // MENU CHÍNH
        do {
            System.out.println();
            System.out.println("==========================================");
            System.out.println("          CHƯƠNG TRÌNH QUẢN LÝ KHO");
            System.out.println("==========================================");
            System.out.println("1. Nhập danh sách sản phẩm");
            System.out.println("2. Lưu danh sách vào inventory.csv");
            System.out.println("3. Đọc danh sách từ inventory.csv");
            System.out.println("4. Hiển thị toàn bộ sản phẩm");
            System.out.println("5. Tính tổng giá trị tồn kho");
            System.out.println("6. Tìm sản phẩm có giá trị cao nhất");
            System.out.println("7. Ghi báo cáo inventory-report.txt");
            System.out.println("0. Thoát");
            System.out.println("==========================================");
            System.out.print("Nhập lựa chọn: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    // CASE 1: Nhập danh sách sản phẩm từ bàn phím; mã và tên không rỗng, đơn giá lớn hơn 0, số lượng không âm.
                    case 1:
                        manager.inputProducts(sc);
                        break;
                    // CASE 2:Lưu danh sách vào data/inventory.csv bằng UTF-8
                    case 2:
                        manager.saveToCSV("data/inventory.csv");
                        break;
                    // CASE 3: Đọc lại tệp CSV và tái tạo danh sách đối tượng Product.
                    case 3:
                        manager.loadFromCSV("data/inventory.csv");
                        break;
                    // CASE 4: Hiển thị toàn bộ sản phẩm và tổng giá trị tồn kho
                    case 4:
                        manager.displayProducts();
                        break;
                    // CASE 5: TỔNG GIÁ TRỊ
                    case 5:
                        System.out.printf("Tổng giá trị tồn kho: %.2f%n", manager.getTotalInventoryValue());
                        break;
                    // CASE 6: Tìm sản phẩm có giá trị tồn kho cao nhất
                    case 6:
                        Product max = manager.getHighestValueProduct();
                        if (max == null) {
                            System.out.println("Danh sách sản phẩm đang rỗng!");
                        } else {
                            System.out.println("Sản phẩm có giá trị tồn kho cao nhất:");
                            System.out.println(max);
                        }
                        break;
                    // CASE 7:  Ghi báo cáo tổng hợp vào data/inventory-report.txt
                    case 7:
                        manager.writeReport("data/inventory-report.txt");
                        break;
                    // CASE 0: THOÁT
                    case 0:
                        System.out.println("Đã thoát chương trình!");
                        break;
                    // LỰA CHỌN KHÔNG HỢP LỆ
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }

            } catch (NumberFormatException e) {
                // Nếu nhập chữ thay vì số
                System.out.println("Lỗi: Vui lòng nhập số!");
                choice = -1; 
            }

        } while (choice != 0);
        // Đóng Scanner
        sc.close();
    }
}
