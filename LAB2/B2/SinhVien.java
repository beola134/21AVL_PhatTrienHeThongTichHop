package B2;

public class SinhVien extends nguoi {

    private String maSinhVien;
    private String nganhHoc;
    private double diemTrungBinh;

    //constructor
    public SinhVien(String hoTen, int namSinh, String diaChi,
            String maSinhVien, String nganhHoc,
            double diemTrungBinh) {

        super(hoTen, namSinh, diaChi);
        this.maSinhVien = maSinhVien;
        this.nganhHoc = nganhHoc;
        this.diemTrungBinh = diemTrungBinh;
    }

    public SinhVien(String hoTen, int namSinh, String diaChi) {
        super(hoTen, namSinh, diaChi);
    }

    // Xếp loại
    public String xepLoai() {
        if (diemTrungBinh >= 8.5) {
            return "Gioi";
        } else if (diemTrungBinh >= 7.0) {
            return "Kha";
        } else if (diemTrungBinh >= 5.0) {
            return "Trung binh";
        } else {
            return "Yeu";
        }
    }

    //Ghi de phuong thuc cua lop cha
    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("Ma sinh vien: " + maSinhVien);
        System.out.println("Nganh hoc: " + nganhHoc);
        System.out.println("Diem trung binh: " + diemTrungBinh);
        System.out.println("Xep loai: " + xepLoai());
        System.out.println("-----------------------------");
    }

}
