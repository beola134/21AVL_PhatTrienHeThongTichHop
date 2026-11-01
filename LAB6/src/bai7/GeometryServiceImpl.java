package bai7;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GeometryServiceImpl extends UnicastRemoteObject implements GeometryService {

    public GeometryServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public double rectangleArea(double width, double height) throws RemoteException {
        System.out.println("Tính diện tích hình chữ nhật: chiều dài = " + height + ", chiều rộng = " + width);
        return width * height;
    }

    @Override
    public double circleArea(double radius) throws RemoteException {
        System.out.println("Tính diện tích hình tròn: bán kính = " + radius);
        // Diện tích hình tròn = 3,14 * bán kính^2
        return Math.PI * radius * radius;
    }

    @Override
    public double triangleArea(double base, double height) throws RemoteException {
        System.out.println("Tính diện tích hình tam giác: cạnh đáy = " + base + ", chiều cao = " + height);
        return 0.5 * base * height;
    }
}