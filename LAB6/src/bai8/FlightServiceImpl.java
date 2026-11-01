package bai8;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class FlightServiceImpl extends UnicastRemoteObject implements FlightService {
    // Lưu trữ danh sách chuyến bay: Key = Mã chuyến bay, Value = Số ghế trống
    private Map<String, Integer> flightMap;

    public FlightServiceImpl() throws RemoteException {
        super();
        flightMap = new HashMap<>();
        // Khởi tạo một số chuyến bay mẫu
        flightMap.put("VN123", 50); // Chuyến bay VN123 còn 50 ghế
        flightMap.put("VJ456", 20); // Chuyến bay VJ456 còn 20 ghế
        flightMap.put("QH789", 5); // Chuyến bay QH789 còn 5 ghế
    }

    @Override
    public synchronized int availableSeats(String flightNumber) throws RemoteException {
        System.out.println("Yêu cầu kiểm tra ghế trống cho chuyến bay: " + flightNumber);
        if (flightMap.containsKey(flightNumber)) {
            return flightMap.get(flightNumber);
        }
        return -1; // Trả về -1 nếu không tìm thấy chuyến bay
    }

    @Override
    public synchronized boolean bookTicket(String flightNumber, int seats) throws RemoteException {
        System.out.println("Yêu cầu đặt " + seats + " vé cho chuyến bay: " + flightNumber);
        if (!flightMap.containsKey(flightNumber)) {
            System.out.println("-> Thất bại: Không tìm thấy chuyến bay " + flightNumber);
            return false;
        }

        int currentSeats = flightMap.get(flightNumber);
        if (seats > 0 && currentSeats >= seats) {
            flightMap.put(flightNumber, currentSeats - seats);
            System.out.println(
                    "-> Đặt vé thành công! Ghế trống còn lại của " + flightNumber + ": " + (currentSeats - seats));
            return true;
        }

        System.out.println("-> Thất bại: Số lượng ghế trống không đủ hoặc số ghế không hợp lệ.");
        return false;
    }

    @Override
    public synchronized boolean cancelBooking(String flightNumber, int seats) throws RemoteException {
        System.out.println("Yêu cầu hủy " + seats + " vé cho chuyến bay: " + flightNumber);
        if (!flightMap.containsKey(flightNumber)) {
            System.out.println("-> Thất bại: Không tìm thấy chuyến bay " + flightNumber);
            return false;
        }

        int currentSeats = flightMap.get(flightNumber);
        // Giả sử tổng sức chứa ban đầu của mỗi chuyến bay tối đa là 100 ghế (hoặc quy
        // ước giới hạn)
        if (seats > 0) {
            flightMap.put(flightNumber, currentSeats + seats);
            System.out.println(
                    "-> Hủy vé thành công! Ghế trống hiện tại của " + flightNumber + ": " + (currentSeats + seats));
            return true;
        }

        return false;
    }
}