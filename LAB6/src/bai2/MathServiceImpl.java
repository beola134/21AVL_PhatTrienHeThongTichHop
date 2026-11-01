package bai2;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class MathServiceImpl extends UnicastRemoteObject implements MathService {

    public MathServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public int add(int a, int b) throws RemoteException {
        System.out.println("Nhận yêu cầu tính tổng: " + a + " + " + b);
        return a + b;
    }
}