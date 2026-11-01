package bai8;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FlightService extends Remote {
    int availableSeats(String flightNumber) throws RemoteException;

    boolean bookTicket(String flightNumber, int seats) throws RemoteException;

    boolean cancelBooking(String flightNumber, int seats) throws RemoteException;
}