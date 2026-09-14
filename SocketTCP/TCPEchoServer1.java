
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServer1 {

    //Cổng (Port) cố định cho server là 7
    public final static int serverPort = 7;

    public static void main(String[] args) {
        try {
            //Tạo 1 serverSocket lằng nghe tại cổng số 7
            ServerSocket ss = new ServerSocket(serverPort);
            System.out.println("Server da duoc tao");
            //Vòng lặp vô tận để server luôn chạy và chờ Clinet
            while (true) {
                try {
                    //lắng nghe và chấp nhận một kết nối từ client gửi tới
                    Socket s = ss.accept();
                    //Tạo 1 đối tượng xử lý luồng riêng cho Client vừa kết nối
                    RequestProcessing rp = new RequestProcessing(s);
                    // Kích hoạt luồng (Thread) để xử lý yêu cầu độc lập
                    rp.start();
                } catch (IOException ie1) {
                    System.out.println("Connection Error: " + ie1);
                }
            }
        } catch (IOException ie) {
            System.out.println("Server Creation Error: " + ie);

        }
    }
}
