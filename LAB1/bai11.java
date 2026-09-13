
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class bai11 {
    public boolean copyFile(String source, String dest) throws FileNotFoundException, IOException {
        //FILE NGUON
        File sourceFile = new File(source);
        //file dich
        File desFile = new File(dest);
        //kiem tra file nguon co tton tai khong
        if (sourceFile.exists()) {
            //luong doc file
            FileInputStream fis = new FileInputStream(sourceFile);
            //luong ghi file
            FileOutputStream fos = new FileOutputStream(desFile);
            byte[] arr = new byte[1024];
            while ((fis.read(arr)) != -1) {
                fos.write(arr);
                fos.flush();
            }
            fis.close();
            fos.close();
            System.out.println("copy thanh cong");
            return true;
        } else {
            System.out.println("File nguon khong ton tai");
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        bai11 Bai11 = new bai11();
        Bai11.copyFile("D:/HocJava/a.txt", "D:/HocJava/b.txt");
    }
}
