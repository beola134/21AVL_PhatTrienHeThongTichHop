package bai9;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AuctionService extends Remote {
    void addItem(String id, String name, double initialPrice) throws RemoteException;

    List<Item> getItems() throws RemoteException;

    boolean placeBid(String itemId, double bidAmount, String bidderName) throws RemoteException;
}