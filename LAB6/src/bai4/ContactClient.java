package bai4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ContactClient {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ContactService service = (ContactService) registry.lookup("ContactService");

            int choice;
            do {
                System.out.println("\n--- QUẢN LÝ DANH BẠ RMI ---");
                System.out.println("1. Thêm liên hệ mới");
                System.out.println("2. Sửa (Cập nhật) số điện thoại liên hệ");
                System.out.println("3. Tìm kiếm liên hệ");
                System.out.println("4. Xóa liên hệ");
                System.out.println("0. Thoát");
                System.out.print("Chọn chức năng (0-4): ");

                choice = scanner.nextInt();
                scanner.nextLine(); // Đọc bỏ ký tự xuống dòng

                switch (choice) {
                    case 1:
                        System.out.print("Nhập tên liên hệ: ");
                        String addName = scanner.nextLine();
                        System.out.print("Nhập số điện thoại: ");
                        String addPhone = scanner.nextLine();
                        service.addContact(addName, addPhone);
                        System.out.println("-> Thêm liên hệ thành công!");
                        break;

                    case 2:
                        System.out.print("Nhập tên liên hệ cần sửa: ");
                        String updateName = scanner.nextLine();
                        System.out.print("Nhập số điện thoại mới: ");
                        String newPhone = scanner.nextLine();

                        boolean updated = service.updateContact(updateName, newPhone);
                        if (updated) {
                            System.out.println("-> Cập nhật liên hệ thành công!");
                        } else {
                            System.out.println("-> Không tìm thấy liên hệ có tên: " + updateName);
                        }
                        break;

                    case 3:
                        System.out.print("Nhập tên cần tìm: ");
                        String findName = scanner.nextLine();
                        String phone = service.findContact(findName);
                        if (phone != null) {
                            System.out.println("-> Tìm thấy! Số điện thoại của [" + findName + "] là: " + phone);
                        } else {
                            System.out.println("-> Không tìm thấy liên hệ có tên: " + findName);
                        }
                        break;

                    case 4:
                        System.out.print("Nhập tên cần xóa: ");
                        String deleteName = scanner.nextLine();
                        boolean deleted = service.deleteContact(deleteName);
                        if (deleted) {
                            System.out.println("-> Xóa thành công liên hệ: " + deleteName);
                        } else {
                            System.out.println("-> Không tìm thấy liên hệ [" + deleteName + "] để xóa.");
                        }
                        break;

                    case 0:
                        System.out.println("Thoát chương trình.");
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn từ 0 đến 4.");
                }
            } while (choice != 0);

        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}