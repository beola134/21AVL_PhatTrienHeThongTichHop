package bai4;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ContactServiceImpl extends UnicastRemoteObject implements ContactService {
    private Map<String, String> contacts;

    public ContactServiceImpl() throws RemoteException {
        super();
        contacts = new HashMap<>();
        // Dữ liệu mẫu ban đầu
        contacts.put("Nguyen Van A", "0901234567");
        contacts.put("Tran Thi B", "0918765432");
    }

    @Override
    public synchronized void addContact(String name, String phone) throws RemoteException {
        contacts.put(name, phone);
        System.out.println("Đã thêm liên hệ: " + name + " - " + phone);
    }

    @Override
    public synchronized boolean updateContact(String name, String newPhone) throws RemoteException {
        System.out.println("Yêu cầu cập nhật liên hệ: " + name);
        if (contacts.containsKey(name)) {
            contacts.put(name, newPhone);
            return true; // Cập nhật thành công
        }
        return false; // Không tìm thấy tên để sửa
    }

    @Override
    public synchronized String findContact(String name) throws RemoteException {
        System.out.println("Yêu cầu tìm kiếm liên hệ: " + name);
        return contacts.get(name); // Trả về số điện thoại hoặc null nếu không tồn tại
    }

    @Override
    public synchronized boolean deleteContact(String name) throws RemoteException {
        System.out.println("Yêu cầu xóa liên hệ: " + name);
        if (contacts.containsKey(name)) {
            contacts.remove(name);
            return true;
        }
        return false;
    }
}