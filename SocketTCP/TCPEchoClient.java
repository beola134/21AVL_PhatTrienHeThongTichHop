
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPEchoClient {

    // public final static String serverIP = "127.0.0.1";
    public final static String serverIP = "localhost";
    public final static int serverPort = 7;

    public static void main(String[] args) throws InterruptedException, IOException {
        Socket s = null;
        try {
            //Khởi tạo kết nối đến Server qua IP và port
            s = new Socket(serverIP, serverPort);
            System.out.println("Clinet da duoc tao");
            //Lấy luồng vào (InputStream) và luồng ra (OutputStream) của Socket
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();
            //Vòng lặp gửi dữ liệu từ 0 đến 9
            for (int i = '0'; i <= '9'; i++) {
                os.write(i); //Gửi ký tự đi
                int ch = is.read(); //Đọc ký tự phản hồi từ Server
                System.out.println((char) ch); //Ép kiểu sang char và in ra màn hình
                Thread.sleep(2000); //Tạm dùng 2 giây

            }

        } catch (IOException ie) {
            System.out.println("Error: Can NOT create socket");
        } finally {
            //Đảm bảo Socket luôn đóng khi kết thúc hoặc có lỗi
            if (s != null) {
                s.close();
            }
        }

    }
}
