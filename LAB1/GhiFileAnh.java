
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class GhiFileAnh {

    public static void saveFile(File path, String tfile, byte[] bfile) {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(bfile));
            ImageIO.write(img, tfile, path);
        } catch (IOException ex) {
            Logger.getLogger(GhiFileAnh.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try {
            // Đường dẫn ảnh gốc
            File fileGoc = new File("D:/anh/1.jpg");
            // Đọc ảnh thành byte[]
            byte[] data = Files.readAllBytes(fileGoc.toPath());
            // File ảnh sau khi lưu
            File fileMoi = new File("D:/anh/anh_copy.jpg");
            // Gọi hàm saveFile
            GhiFileAnh.saveFile(fileMoi, "jpg", data);
            System.out.println("Ghi File Thanh Cong");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
