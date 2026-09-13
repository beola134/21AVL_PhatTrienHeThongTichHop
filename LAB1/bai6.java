
import java.io.File;
//Delete	file	trong	Java
public class bai6 {

    private void deleteFile(String source) {
        //new file
        File file = new File(source);
        //check file exist
        //neu ton tai
        if (file.exists()) {
            System.out.println("File ton tai");
            file.delete();
            System.out.println("Xoa file thanh cong");
        } else {
            System.out.println("File khong on tai");
        }

    }

    public static void main(String[] args) {
        bai6 deleteFileIO = new bai6();
        deleteFileIO.deleteFile("D:/HocJava/demo.txt");

    }

}
