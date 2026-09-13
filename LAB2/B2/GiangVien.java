package B2;

public class GiangVien extends nguoi {

    private String maGiangVien;
    private String chuyenMon;
    private double luongCoBan;
    private double heSoLuong;

    // Constructor
    public GiangVien(String hoTen, int namSinh, String diaChi,
            String maGiangVien, String chuyenMon,
            double luongCoBan, double heSoLuong) {

        super(hoTen, namSinh, diaChi);

        this.maGiangVien = maGiangVien;
        this.chuyenMon = chuyenMon;
        this.luongCoBan = luongCoBan;
        this.heSoLuong = heSoLuong;
    }

    // Tính lương
    public double tinhLuong() {
        return luongCoBan * heSoLuong;
    }

    // Ghi đè phương thức của lớp cha
    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("Ma giang vien:  " + maGiangVien);
        System.out.println("Chuyen mon:  " + chuyenMon);
        System.out.println("Luong co ban:  " + luongCoBan);
        System.out.println("He so luong:  " + heSoLuong);
        System.out.println("Luong:  " + tinhLuong());
        System.out.println("-------------------------------");

        
    }

    public String getMaGiangVien() {
        return maGiangVien;
    }
    
}
