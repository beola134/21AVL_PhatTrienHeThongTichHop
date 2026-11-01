// javac -d out src\bai2\*.java
// java -cp out bai2.NumberServer
// java -cp out bai2.NumberClient
package bai2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MathService extends Remote {
    int add(int a, int b) throws RemoteException;
}