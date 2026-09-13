package B2;

public class main {

    public static void main(String[] args) {
        // Tạo 2 sinh viên
        SinhVien sv1 = new SinhVien(
                "Nguyen Van An",
                2005,
                "TP. Ho Chi Minh",
                "SV01",
                "Cong nghe thong tin",
                8.8
        );

        SinhVien sv2 = new SinhVien(
                "Tran Thi Binh",
                2004,
                "Dong Nai",
                "SV02",
                "Ke toan",
                6.5
        );

        // Tạo 2 giảng viên
        GiangVien gv1 = new GiangVien(
                "Nguyen Van Minh",
                1980,
                "TP. Ho Chi Minh",
                "GV01",
                "Lap trinh Java",
                5000000,
                2.5
        );

        GiangVien gv2 = new GiangVien(
                "Le Thi Hoa",
                1985,
                "Binh Duong",
                "GV02",
                "Co so du lieu",
                6000000,
                2.2
        );
        //hien thi thong tin sinh vien
        System.out.println("=====Sinh vien 1======");
        sv1.hienThiThongTin();
        System.out.println("=====Sinh vien 2======");
        sv2.hienThiThongTin();
        //hien thi thong tin giang vien
        System.out.println("=====Giang vien 1=====");
        gv1.hienThiThongTin();
        System.out.println("=====Giang vien 2=====");
        gv2.hienThiThongTin();

    }

}
