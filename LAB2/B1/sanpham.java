package B1;

public class sanpham {

    //Tính đóng gói các thuộc tính để private
    private String maSanPham;
    private String tenSanPham;
    private double donGia;
    private int soLuong;

    //constructor đầy đủ tham số
    public sanpham(String maSanPham, String tenSanPham, double donGia, int soLuong) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }

    public sanpham() {
    }

    //Getter
    public String getMaSanPham() {
        return maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public double getDonGia() {
        return donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    //tính thành tiền
    public double tinhThanhTien() {
        return donGia * soLuong;
    }

    //nhập thêm hàng
    public void nhapHang(int soLuongNhap) {
        if (soLuongNhap > 0) {
            soLuong += soLuongNhap;
            System.out.println("Nhap hang thanh cong");
        } else {
            System.out.println("So luong phai lon hon 0");
        }
    }

    //Bán hàng
    public boolean banHang(int soLuongBan) {
        if (soLuongBan <= 0) {
            System.out.println("So luong ban phai lon hon 0!");
            return false;
        }

        if (soLuongBan > soLuong) {
            System.out.println("khong du hang trong kho!");
            return false;
        }

        soLuong -= soLuongBan;
        System.out.println("Ban hang thanh cong!");
        return true;
    }

    //hien thi thong tin san pham
    public void hienThiThongTin() {
        System.out.println("Ma san pham: " + maSanPham);
        System.out.println("Ten san pham: " + tenSanPham);
        System.out.println("Don gia: " + donGia);
        System.out.println("So luong ton kho: " + soLuong);
        System.out.println("Thanh tien:  " + tinhThanhTien());
        System.out.println("-----------------------------");

    }

}
