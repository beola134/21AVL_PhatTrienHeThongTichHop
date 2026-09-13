
import java.io.File;
import java.io.IOException;

public class bai9 {

    public boolean deleteListFileInfolder(String source) throws IOException {
        File folder = new File(source);
        if (folder.exists()) {
// danh sach file
            File[] listFile = folder.listFiles();
            if (listFile.length != 0) {
                for (File f : listFile) {
// neu la file thi del
                    if (f.isFile()) {
                        f.delete();
                    }
// neu la thu muc thi goi de quy lai
                    if (f.isDirectory()) {
                        deleteListFileInfolder(f.getAbsolutePath());
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

    public static void main(String[] args) throws IOException {
        bai9 deleteDirTH3 = new bai9();

        deleteDirTH3.deleteListFileInfolder("D:/HocJava/TestDeleteDir");
    }

}
