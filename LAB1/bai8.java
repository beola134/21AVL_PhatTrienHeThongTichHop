//Delete thư mục chứa Files
import java.io.File;
public class bai8 {
    public boolean deleteListFileInfolder(String source) {
        File folder = new File(source);
        // folder ton tai
        if (folder.exists()) {
            // danh sách file
            File[] listFile = folder.listFiles();
            if (listFile.length != 0) {
                for (File f : listFile) {
                    // file thì xóa
                    if (f.isFile()) {
                        f.delete();
                    }
                }
            }
            folder.delete();
            System.out.println("Delete folder thanh cong!");
            return true;
        } else {
            System.out.println("folder khong ton tai");
            return false;
        }
    }
    public static void main(String[] args) {
        bai8 Bai8 = new bai8();
        Bai8.deleteListFileInfolder("D:/HocJava");
    }
}
