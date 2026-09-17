package TH4;

public class Product {

    // Khai báo các thuộc tính 
    private final String code;
    private final String name;
    private final double unitPrice;
    private final int quantity;

    //   Phương thức khởi tạo (Constructor) có tham số Dùng để tạo một đối tượng
    //   Product mới và kiểm tra tính hợp lệ của dữ liệu đầu vào
    public Product(String code, String name, double unitPrice, int quantity) {
        // Kiểm tra sản phẩm: Nếu null hoặc chỉ toàn khoảng trắng thì báo lỗi
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Mã không được rỗng");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tên không được rỗng");
        }
        if (unitPrice <= 0 || quantity < 0) {
            throw new IllegalArgumentException("Giá hoặc số lượng không hợp lệ");
        }
        //Gán giá trị
        this.code = code;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    //tính Tổng tiền = Đơn giá * Số lượng
    public double inventoryValue() {
        return unitPrice * quantity;
    }
    //   hiển thị thông tin sản phẩm
    @Override
    public String toString() {
        // %.0f: Định dạng số thực không lấy phần thập phân, có dấu phân cách hàng nghìn (ví dụ: 3,500,000)
        return "%s - %s: %,.0f VND".formatted(
                code, name, inventoryValue());
    }
}
