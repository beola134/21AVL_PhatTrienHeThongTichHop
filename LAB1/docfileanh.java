
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class docfileanh {
    public static byte[] readFile(File path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            byte[] buf = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            fis.close();
            return bos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(docfileanh.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
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
