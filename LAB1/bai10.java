
import java.io.File;
import java.io.IOException;
public class bai10 {

    public void finFile(String source, String key) {
        File file = new File(source);
        if (file.exists()) {
            if (file.isFile()) {
                if (file.getName().endsWith(key)) {
                    System.out.println(file.getAbsoluteFile());
                }
            }
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File f : listFiles) {
                    finFile(f.getAbsolutePath(), key);
                }
            }
        } else {
            System.out.println("source khong ton tai");
        }
    }
     public static void main(String[] args) throws IOException {
        bai10 Bai10 = new bai10();

         Bai10.finFile("D:/HocJava", ".txt");
    }
 
}
