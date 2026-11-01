package bai5;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {

    public ChatServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String sendMessage(String message) throws RemoteException {
        System.out.println("[Client gửi]: " + message);

        // Server tự động tạo phản hồi đơn giản dựa trên tin nhắn nhận được
        String reply = "Server đã nhận được tin nhắn: \"" + message + "\" (Phản hồi tự động)";
        return reply;
    }
}