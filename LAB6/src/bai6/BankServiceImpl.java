package bai6;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BankServiceImpl extends UnicastRemoteObject implements BankService {
    private double balance;

    public BankServiceImpl(double initialBalance) throws RemoteException {
        super();
        this.balance = initialBalance;
    }

    @Override
    public synchronized double getBalance() throws RemoteException {
        System.out.println("Yêu cầu kiểm tra số dư. Số dư hiện tại: " + balance);
        return balance;
    }

    @Override
    public synchronized void deposit(double amount) throws RemoteException {
        System.out.println("Yêu cầu gửi tiền: " + amount);
        if (amount > 0) {
            balance += amount;
            System.out.println("Gửi tiền thành công: +" + amount + " | Số dư mới: " + balance);
        } else {
            System.out.println("Gửi tiền thất bại: Số tiền gửi phải lớn hơn 0.");
        }
    }

    @Override
    public synchronized boolean withdraw(double amount) throws RemoteException {
        System.out.println("Yêu cầu rút tiền: " + amount);
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Rút tiền thành công: -" + amount + " | Số dư còn lại: " + balance);
            return true;
        }
        System.out.println("Rút tiền thất bại: Số dư không đủ hoặc số tiền không hợp lệ.");
        return false;
    }
}