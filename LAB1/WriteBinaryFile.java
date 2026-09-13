
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WriteBinaryFile {

    public static void saveSV(String src, ArrayList<SinhVien> listSV)
            throws IOException {

        DataOutputStream dos = new DataOutputStream(new FileOutputStream(src));
        // Ghi số lượng sinh viên
        dos.writeInt(listSV.size());
        // Ghi từng sinh viên
        for (SinhVien sv : listSV) {
            dos.writeUTF(sv.getMssv());
            dos.writeUTF(sv.getTen());
            dos.writeInt(sv.getTuoi());
            dos.writeInt(sv.getListMH().size());
            for (MonHoc mh : sv.getListMH()) {
                dos.writeUTF(mh.getTenMonHoc());
                dos.writeInt(mh.getTinChi());
                dos.writeDouble(mh.getDiem());
            }
        }
        dos.flush();
        dos.close();
    }

    public static void main(String[] args) throws IOException {
        // Tạo các môn học
        MonHoc mh = new MonHoc("Lap trinh co ban", 3, 6.7);
        MonHoc mh1 = new MonHoc("Lap trinh Web", 3, 7.5);
        MonHoc mh2 = new MonHoc("Thiet ke he dieu hanh", 3, 8.0);
        // Danh sách môn học
        ArrayList<MonHoc> listMH = new ArrayList<>();
        listMH.add(mh);
        listMH.add(mh1);
        listMH.add(mh2);
        // Tạo danh sách sinh viên
        ArrayList<SinhVien> listSV = new ArrayList<>();
        SinhVien sv = new SinhVien(
                "11329078",
                "Nguyen Van A",
                23,
                listMH
        );

        SinhVien sv1 = new SinhVien(
                "11329079",
                "Nguyen Van B",
                23,
                listMH
        );
        listSV.add(sv);
        listSV.add(sv1);
        // Lưu xuống file nhị phân
        saveSV("E:\\a.txt", listSV);
        System.out.println("Da ghi danh sach sinh vien vao file thanh cong!");
    }
}
