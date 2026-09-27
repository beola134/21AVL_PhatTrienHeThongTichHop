package bai5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {

    private static final ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server dang chay tai port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Co client ket noi: " + socket.getInetAddress());
                pool.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            System.out.println("Loi Server: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }

    public static void broadcast(String message, String sender) {
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet()) {
            if (!entry.getKey().equals(sender)) {
                entry.getValue().sendMessage(message);
            }
        }
    }

    public static String getUsers() {
        return String.join(", ", clients.keySet());
    }

    static class ClientHandler implements Runnable {

        private Socket socket;
        private BufferedReader reader;
        private BufferedWriter writer;
        private String nickname;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

                while (true) {
                    sendMessage("NICK:");
                    String name = reader.readLine();
                    if (name == null) {
                        return;
                    }
                    name = name.trim();
                    if (name.isEmpty()) {
                        sendMessage("Nickname khong duoc de trong!");
                        continue;
                    }
                    if (clients.putIfAbsent(name, this) == null) {
                        nickname = name;
                        sendMessage("Dang nhap thanh cong!");
                        sendMessage("Go USERS de xem danh sach.");
                        sendMessage("Go MSG noi_dung de gui tin nhan.");
                        sendMessage("Go QUIT de thoat.");
                        broadcast(
                                "[SERVER] " + nickname + " da tham gia phong chat.", nickname);
                        break;

                    } else {
                        sendMessage("Nickname da ton tai!");
                    }
                }

                String message;
                while ((message = reader.readLine()) != null) {
                    message = message.trim();
                    if (message.equalsIgnoreCase("USERS")) {
                        sendMessage("Danh sach user: " + getUsers());

                    } else if (message.regionMatches(
                            true, 0, "MSG ", 0, 4)) {
                        String content = message.substring(4).trim();
                        if (content.isEmpty()) {
                            sendMessage("Noi dung tin nhan khong duoc rong!");

                        } else {
                            String chatMessage = nickname + ": " + content;
                            System.out.println(chatMessage);
                            broadcast(chatMessage, nickname);
                        }

                    } else if (message.equalsIgnoreCase("QUIT")) {
                        sendMessage("Tam biet " + nickname + "!");
                        break;

                    } else {
                        sendMessage("Lenh khong hop le! " + "Dung USERS, MSG noi_dung hoac QUIT.");
                    }
                }

            } catch (IOException e) {
                System.out.println("Client " + nickname + " ngat ket noi bat thuong.");
            } finally {

                if (nickname != null) {
                    clients.remove(nickname, this);
                    broadcast("[SERVER] " + nickname + " da roi phong chat.", nickname);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

        public synchronized void sendMessage(String message) {
            try {
                writer.write(message);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {

                if (nickname != null) {
                    clients.remove(nickname, this);
                }
            }
        }
    }
}

// javac -d out src/bai5/ChatServer.java src/bai5/ChatClient.java
// java -cp out bai5.ChatServer
// java -cp out bai5.ChatClient