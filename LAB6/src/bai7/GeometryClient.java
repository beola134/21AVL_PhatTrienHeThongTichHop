package bai7;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class GeometryClient {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            GeometryService service = (GeometryService) registry.lookup("GeometryService");

            int choice;
            do {
                System.out.println("\n--- TÍNH DIỆN TÍCH HÌNH HỌC RMI ---");
                System.out.println("1. Tính diện tích hình chữ nhật");
                System.out.println("2. Tính diện tích hình tròn");
                System.out.println("3. Tính diện tích hình tam giác");
                System.out.println("0. Thoát");
                System.out.print("Chọn hình cần tính (0-3): ");

                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Nhập chiều dài: ");
                        double width = scanner.nextDouble();
                        System.out.print("Nhập chiều rộng: ");
                        double height = scanner.nextDouble();

                        double rectArea = service.rectangleArea(width, height);
                        System.out.println("-> Diện tích hình chữ nhật là: " + rectArea);
                        break;

                    case 2:
                        System.out.print("Nhập bán kính hình tròn: ");
                        double radius = scanner.nextDouble();

                        double circleArea = service.circleArea(radius);
                        System.out.println("-> Diện tích hình tròn là: " + circleArea);
                        break;

                    case 3:
                        System.out.print("Nhập cạnh đáy tam giác: ");
                        double base = scanner.nextDouble();
                        System.out.print("Nhập chiều cao tam giác: ");
                        double triHeight = scanner.nextDouble();

                        double triangleArea = service.triangleArea(base, triHeight);
                        System.out.println("-> Diện tích hình tam giác là: " + triangleArea);
                        break;

                    case 0:
                        System.out.println("Thoát chương trình.");
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn từ 0 đến 3.");
                }
            } while (choice != 0);

        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}