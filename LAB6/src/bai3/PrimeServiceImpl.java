package bai3;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class PrimeServiceImpl extends UnicastRemoteObject implements PrimeService {

    public PrimeServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean isPrime(int n) throws RemoteException {
        System.out.println("Nhận yêu cầu kiểm tra số nguyên tố cho số: " + n);

        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}