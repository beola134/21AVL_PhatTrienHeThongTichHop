import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 5000;

    private static final String[] ROOMS = { "Study", "Game", "TracNghiem" };

    private static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Máy chủ đang chạy tại cổng " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client kết nối: " + socket.getInetAddress());
                ClientHandler client = new ClientHandler(socket);
                synchronized (clients) {
                    clients.add(client);
                }
                new Thread(client).start();
            }
        } catch (IOException e) {
            System.out.println("Lỗi máy chủ: " + e.getMessage());
        }
    }

    // Gửi danh sách phòng cho client
    private static void sendRooms(PrintWriter out) {
        out.println("DANH SÁCH PHÒNG: Study | Game | TracNghiem"
                + " || Nhập tên phòng để tham gia"
                + " || Nhập /exit để thoát");
    }

    // Gửi tin nhắn đến các client trong cùng phòng
    private static void broadcast(String room, String message,
            ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender && client.room != null && client.room.equals(room)) {
                    client.out.println(message);
                }
            }
        }
    }

    // Kiểm tra phòng có hợp lệ không
    private static boolean isValidRoom(String room) {
        for (String r : ROOMS) {
            if (r.equalsIgnoreCase(room)) {
                return true;
            }
        }
        return false;
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        private String name;
        private String room;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Nhận tên client
                name = in.readLine();

                if (name == null || name.trim().isEmpty()) {
                    return;
                }

                name = name.trim();

                out.println("Xin chào " + name + "! Bạn đã kết nối đến máy chủ.");

                // Chọn phòng và chat
                while (true) {
                    room = null;

                    sendRooms(out);

                    String choice = in.readLine();

                    if (choice == null || choice.equals("/exit")) {
                        break;
                    }

                    choice = choice.trim();

                    // Kiểm tra phòng hợp lệ
                    if (!isValidRoom(choice)) {
                        out.println("Phòng không hợp lệ!" + " Vui lòng chọn lại.");
                        continue;
                    }

                    // Lưu tên phòng chuẩn
                    for (String r : ROOMS) {
                        if (r.equalsIgnoreCase(choice)) {
                            room = r;
                            break;
                        }
                    }

                    out.println("Bạn đã vào phòng " + room + ". Nhập /quit để rời phòng," + " /exit để ngắt kết nối.");

                    broadcast(room, "[Thông báo] " + name + " đã tham gia phòng " + room, this);

                    // Nhận và xử lý tin nhắn
                    String message;

                    while ((message = in.readLine()) != null) {

                        // Rời phòng và quay lại danh sách
                        if (message.equals("/quit")) {
                            broadcast(room, "[Thông báo] " + name + " đã rời phòng " + room, this);
                            room = null;
                            break;
                        }

                        // Ngắt kết nối đến máy chủ
                        if (message.equals("/exit")) {
                            broadcast(room, "[Thông báo] " + name + " đã rời phòng " + room, this);
                            room = null;
                            return;
                        }

                        // Gửi tin nhắn đến các thành viên cùng phòng
                        if (!message.trim().isEmpty()) {
                            broadcast(room, "[" + room + "] " + name + ": " + message, this);
                        }
                    }

                    if (message == null) {
                        break;
                    }
                }

            } catch (IOException e) {
                System.out.println("Client đã ngắt kết nối.");
            } finally {
                synchronized (clients) {
                    clients.remove(this);
                }

                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Lỗi đóng socket: " + e.getMessage());
                }
                System.out.println("Client " + name + " đã ngắt kết nối.");
            }
        }
    }
}