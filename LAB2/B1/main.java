package B1;

public class main {

    public static void main(String[] args) {

        // Tạo 2 sản phẩm
        sanpham sp1 = new sanpham("SP001", "San Pham 1", 100.0, 10);
        sanpham sp2 = new sanpham("SP002", "San Pham2", 200.0, 5);

        // Hiển thị thông tin ban đầu
        System.out.println("===== THONG TIN BAN DAU =====");
        sp1.hienThiThongTin();
        sp2.hienThiThongTin();

        // Nhập thêm hàng cho sản phẩm 1
        System.out.println("===== NHAP THEM HANG =====");
        sp1.nhapHang(5);
        sp1.hienThiThongTin();

        // Bán hàng thành công
        System.out.println("===== BAN HANG =====");
        sp1.banHang(8);
        sp1.hienThiThongTin();

        // Thử bán quá số lượng tồn kho
        System.out.println("===== THU BAN QUA TON KHO =====");
        sp1.banHang(20);
        sp1.hienThiThongTin();
    }
}
