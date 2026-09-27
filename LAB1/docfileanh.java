import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class docfileanh {

    // HÀM ĐỌC FILE VÀ CHUYỂN THÀNH MẢNG BYTE
    public static byte[] readFile(File path) {
        try {
            // KHỞI TẠO LUỒNG ĐỌC FILE VÀ BỘ ĐỆM TẠM
            FileInputStream fis = new FileInputStream(path);
            byte[] buf = new byte[1024]; // Bộ đệm đọc 1KB mỗi lần
            ByteArrayOutputStream bos = new ByteArrayOutputStream();// khởi tạo một luồng xuất dữ liệu ghi vào bộ nhớ

            // VÒNG LẶP ĐỌC TỪNG KHỐI DỮ LIỆU ĐẾN HẾT FILE
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); // Ghi số byte đọc được vào bộ nhớ tạm
            }
            // ĐÓNG LUỒNG VÀ TRẢ VỀ MẢNG BYTE HOÀN CHỈNH
            fis.close();
            return bos.toByteArray();

        } catch (IOException ex) {
            // XỬ LÝ LỖI NẾU XẢY RA SỰ CỐ ĐỌC FILE
            Logger.getLogger(docfileanh.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // Trả về null nếu xảy ra lỗi
    }

    // THỰC THI CHƯƠNG TRÌNH (MAIN)
    public static void main(String[] args) {
        File file = new File("D:\\anh\\1.jpg");
        byte[] data = readFile(file);
        if (data != null) {
            System.out.println("Doc file anh thanh cong + Kich thuoc: " + data.length + " bytes.");
        } else {
            System.out.println("Doc file that bai");
        }
    }
}