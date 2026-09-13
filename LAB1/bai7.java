// delete folder

import java.io.File;

public class bai7 {

    public boolean deleteEmptyFolder(String source) {
        File folder = new File(source);
        //kiem tra neu folder ton tai thi xoa
        if (folder.exists()) {
            folder.delete();
            System.out.println("folder ton tai\n xoa folder thanh cong");
            return true;
        } else {
            System.out.println("folder khong ton tai");
            return false;

        }

    }
    public static void main(String[] args) {
        bai7 Bai7 = new bai7();
        Bai7.deleteEmptyFolder("D:/HocJava/New folder");
    }
}
