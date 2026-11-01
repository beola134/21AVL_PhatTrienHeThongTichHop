package bai9;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class AuctionClient {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            AuctionService service = (AuctionService) registry.lookup("AuctionService");

            System.out.print("Nhập tên định danh của bạn (Tên người dùng): ");
            String username = scanner.nextLine();

            int choice;
            do {
                System.out.println("\n--- HỆ THỐNG ĐẤU GIÁ TRỰC TUYẾN (Xin chào: " + username + ") ---");
                System.out.println("1. Xem danh sách sản phẩm đang đấu giá");
                System.out.println("2. Đặt giá thầu (Bid)");
                System.out.println("3. Thêm sản phẩm mới (Dành cho Seller)");
                System.out.println("0. Thoát");
                System.out.print("Chọn chức năng (0-3): ");

                choice = scanner.nextInt();
                scanner.nextLine(); // Đọc bỏ ký tự xuống dòng

                switch (choice) {
                    case 1:
                        List<Item> items = service.getItems();
                        System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
                        if (items.isEmpty()) {
                            System.out.println("Hiện không có sản phẩm nào đang đấu giá.");
                        } else {
                            for (Item it : items) {
                                System.out.println(it);
                            }
                        }
                        break;

                    case 2:
                        List<Item> currentItems = service.getItems();
                        System.out.println("\n--- DANH SÁCH SẢN PHẨM HIỆN TẠI ---");
                        for (Item it : currentItems) {
                            System.out.println(it);
                        }

                        System.out.print("\nNhập mã sản phẩm muốn đấu giá (ví dụ: SP01): ");
                        String itemId = scanner.nextLine();
                        System.out.print("Nhập mức giá thầu bạn muốn đưa ra (VNĐ): ");
                        double bidAmount = scanner.nextDouble();
                        scanner.nextLine();

                        boolean success = service.placeBid(itemId, bidAmount, username);
                        if (success) {
                            System.out.println("-> Đặt giá thầu thành công! Bạn đang giữ mức giá cao nhất.");
                        } else {
                            System.out.println(
                                    "-> Đặt giá thầu thất bại! Giá thầu phải cao hơn giá hiện tại của sản phẩm.");
                        }
                        break;

                    case 3:
                        System.out.print("Nhập mã sản phẩm mới (ví dụ: SP03): ");
                        String newId = scanner.nextLine();
                        System.out.print("Nhập tên sản phẩm: ");
                        String newName = scanner.nextLine();
                        System.out.print("Nhập giá khởi điểm (VNĐ): ");
                        double initPrice = scanner.nextDouble();
                        scanner.nextLine();

                        service.addItem(newId, newName, initPrice);
                        System.out.println("-> Đã thêm sản phẩm lên hệ thống đấu giá thành công!");
                        break;

                    case 0:
                        System.out.println("Thoát chương trình.");
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            } while (choice != 0);

        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}