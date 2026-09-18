package TH5;

public class Product {
    //khai báo private để đảm bảo tính đóng gói
    private String id;
    private String name;       
    private double price;       
    private int quantity;   
    // Constructor: dùng để khởi tạo một đối tượng Product
    public Product(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter lấy mã, tên,giá, số lượng
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

    // Tính tổng giá trị tồn kho của sản phẩm đơn giá × số lượng
    public double getTotalValue() {
        return price * quantity;
    }
    // Ghi đè phương thức toString()
    // Dùng để hiển thị thông tin sản phẩm dưới dạng chuỗi
    @Override
    public String toString() {
        return String.format(
                "Product{id='%s', name='%s', price=%.2f, quantity=%d, totalValue=%.2f}",
                id, 
                name,
                price,
                quantity,
                getTotalValue()
        );
    }
}
