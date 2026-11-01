package bai8;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class FlightClient {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            FlightService service = (FlightService) registry.lookup("FlightService");

            int choice;
            do {
                System.out.println("\n--- HỆ THỐNG ĐẶT VÉ MÁY BAY RMI ---");
                System.out.println("1. Kiểm tra số ghế trống");
                System.out.println("2. Đặt vé máy bay");
                System.out.println("3. Hủy vé / Hoàn vé");
                System.out.println("0. Thoát");
                System.out.print("Chọn chức năng (0-3): ");

                choice = scanner.nextInt();
                scanner.nextLine(); // Đọc bỏ ký tự xuống dòng

                switch (choice) {
                    case 1:
                        System.out.print("Nhập mã chuyến bay (ví dụ: VN123, VJ456, QH789): ");
                        String checkFlight = scanner.nextLine();
                        int seats = service.availableSeats(checkFlight);

                        if (seats != -1) {
                            System.out.println("-> Chuyến bay [" + checkFlight + "] hiện còn " + seats + " ghế trống.");
                        } else {
                            System.out.println("-> Không tồn tại chuyến bay có mã: " + checkFlight);
                        }
                        break;

                    case 2:
                        System.out.print("Nhập mã chuyến bay muốn đặt: ");
                        String bookFlight = scanner.nextLine();
                        System.out.print("Nhập số lượng ghế cần đặt: ");
                        int bookSeats = scanner.nextInt();

                        boolean booked = service.bookTicket(bookFlight, bookSeats);
                        if (booked) {
                            System.out.println("-> Đặt vé thành công! Chúc quý khách chuyến đi vui vẻ.");
                        } else {
                            System.out.println(
                                    "-> Đặt vé thất bại! (Kiểm tra lại mã chuyến bay hoặc số ghế trống không đủ)");
                        }
                        break;

                    case 3:
                        System.out.print("Nhập mã chuyến bay muốn hủy vé: ");
                        String cancelFlight = scanner.nextLine();
                        System.out.print("Nhập số lượng ghế muốn hủy: ");
                        int cancelSeats = scanner.nextInt();

                        boolean canceled = service.cancelBooking(cancelFlight, cancelSeats);
                        if (canceled) {
                            System.out.println("-> Hủy vé thành công!");
                        } else {
                            System.out.println("-> Hủy vé thất bại! Không tìm thấy mã chuyến bay.");
                        }
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