package bai9;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuctionServiceImpl extends UnicastRemoteObject implements AuctionService {
    private Map<String, Item> itemMap;

    public AuctionServiceImpl() throws RemoteException {
        super();
        itemMap = new HashMap<>();
        // Thêm sản phẩm mẫu ban đầu
        itemMap.put("SP01", new Item("SP01", "Laptop Gaming ASUS", 15000000.0));
        itemMap.put("SP02", new Item("SP02", "Tai nghe chống ồn Sony", 2000000.0));
    }

    @Override
    public synchronized void addItem(String id, String name, double initialPrice) throws RemoteException {
        if (!itemMap.containsKey(id)) {
            itemMap.put(id, new Item(id, name, initialPrice));
            System.out.println("[SELLER THÊM SẢN PHẨM] " + name + " (Giá khởi điểm: " + initialPrice + " VNĐ)");
        } else {
            System.out.println("Thêm sản phẩm thất bại: Mã sản phẩm " + id + " đã tồn tại.");
        }
    }

    @Override
    public synchronized List<Item> getItems() throws RemoteException {
        return new ArrayList<>(itemMap.values());
    }

    @Override
    public synchronized boolean placeBid(String itemId, double bidAmount, String bidderName) throws RemoteException {
        Item item = itemMap.get(itemId);
        if (item == null) {
            System.out.println("[ĐẤU GIÁ LỖI] Không tìm thấy sản phẩm mã: " + itemId);
            return false;
        }

        // Kiểm tra xem giá thầu mới có cao hơn giá hiện tại không
        if (bidAmount > item.getCurrentPrice()) {
            item.setCurrentPrice(bidAmount);
            item.setHighestBidder(bidderName);

            System.out.println("\n[THÔNG BÁO CẬP NHẬT GIÁ THẦU]");
            System.out.println("-> Sản phẩm: " + item.getName());
            System.out.println("-> Người đặt mới: " + bidderName + " với giá: " + bidAmount + " VNĐ");
            return true;
        }

        System.out.println("[ĐẤU GIÁ THẤT BẠI] " + bidderName + " đặt giá " + bidAmount
                + " VNĐ thấp hơn hoặc bằng giá hiện tại của " + item.getName());
        return false;
    }
}