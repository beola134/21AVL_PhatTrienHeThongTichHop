
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//Luồng xử lý cho từng Client
public class RequestProcessing extends Thread {

    Socket channel; //Socket kết nối với Client
    // Hàm khởi tạo nhận vào một Socket khi Client kết nối

    public RequestProcessing(Socket s) {
        channel = s;
    }

    // Mã chạy khi Thread bắt đầu
    public void run() {
        try {
            OutputStream os = channel.getOutputStream(); //Luồng gửi dữ liệu
            InputStream is = channel.getInputStream(); //Luồng nhận dữ liệu
            while (true) {
                int n = is.read(); //Đọc 1 byte từ Client
                if (n == -1) {
                    break; //Thoát nếu Client ngắt kết nối
                }
                os.write(n); //Gửi trả lại đúng byte đó cho clinet
            }
        } catch (IOException ie) {
            System.out.println("Request Processing Error: " + ie);

        }
    }
}
