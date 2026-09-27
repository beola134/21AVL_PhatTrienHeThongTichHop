
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DocFile {

    public static void loadSV(String src) throws IOException {
        // MỞ LUỒNG ĐỌC DỮ LIỆU
        DataInputStream dis = new DataInputStream(new FileInputStream(new File(
                src)));
        // ĐỌC SỐ LƯỢNG SINH VIÊN VÀ KHỞI TẠO DANH SÁCH CHỨA
        int size = dis.readInt();
        ArrayList<SinhVien> listSV = new ArrayList<>();
        // VÒNG LẶP ĐỌC THÔNG TIN TỪNG SINH VIÊN
        for (int i = 0; i < size; i++) {
            // Đọc thông tin cơ bản của sinh viên
            String mssv = dis.readUTF();
            String name = dis.readUTF();
            int age = dis.readInt();
            int sizemh = dis.readInt();
            // Đọc số lượng môn học của sinh viên này
            ArrayList<MonHoc> listMH = new ArrayList<>();
            // Vòng lặp đọc danh sách môn học của sinh viên
            for (int j = 0; j < sizemh; j++) {
                String tenMonHoc = dis.readUTF();
                int tinChi = dis.readInt();
                double diem = dis.readDouble();
                MonHoc mh1 = new MonHoc(tenMonHoc, tinChi, diem);
                listMH.add(mh1);
            }
            // Tạo đối tượng SinhVien hoàn chỉnh và thêm vào danh sách
            listSV.add(new SinhVien(mssv, name, age, listMH));
        }
        // In ra màn hình
        for (SinhVien sv : listSV) {
            System.out.println(sv.toString());
        }
        dis.close();
    }

    public static void main(String[] args) throws IOException {
        loadSV("E:\\a.txt"); // doc file tai duong dan
    }
}
