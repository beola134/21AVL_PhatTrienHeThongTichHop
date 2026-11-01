package bai9;

import java.io.Serializable;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private double currentPrice;
    private String highestBidder; // Người đang trả giá cao nhất

    public Item(String id, String name, double initialPrice) {
        this.id = id;
        this.name = name;
        this.currentPrice = initialPrice;
        this.highestBidder = "Chưa có";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(String highestBidder) {
        this.highestBidder = highestBidder;
    }

    @Override
    public String toString() {
        return "[Mã: " + id + "] " + name + " | Giá hiện tại: " + currentPrice + " VNĐ | Người giữ giá cao nhất: "
                + highestBidder;
    }
}