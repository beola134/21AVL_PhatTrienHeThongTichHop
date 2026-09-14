
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServer {

    public final static int serverPort = 7;

    public static void main(String[] args) {
        try {
            //Tạo 1 serverSocket lằng nghe tại cổng số 7
            ServerSocket ss = new ServerSocket(serverPort);
            System.out.println("Server da duoc tao");

            //Vòng lặp để liên tục chấp nhận các kết nối mới
            while (true) {
                try {
                    //Chờ và chấp nhật kết nối từ Clinet
                    Socket s = ss.accept();
                    //lấy luồng Xuất (os) và Nhập (is) để giao tiếp
                    OutputStream os = s.getOutputStream();
                    InputStream is = s.getInputStream();
                    int ch = 0;
                    //vòng lặp đọc dữ liệu từ Client gửi đến
                    while (true) {
                        ch = is.read(); //Đọc từng byte dữ liệu
                        if (ch == -1) {
                            break; //Nếu hết dữ liệu (Client ngắt dòng) thì dừng
                        }
                        System.out.print((char) ch); //In kí tự nhận được ra màn hình Server
                        os.write(ch); //Gửi ngược lại ký tự đó về Client (Echo)
                    }
                    s.close(); //Đóng kết nối với Client hiện tại

                } catch (IOException ie1) {
                    System.out.println("Connection Error: " + ie1);
                }
            }
        } catch (IOException ie) {
            System.out.println("Server Creation Error: " + ie);

        }
    }

}
