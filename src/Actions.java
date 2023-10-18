
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Actions extends Remote {
    void addBlock(int payment, String senderName, String receiverName) throws RemoteException;
    String getBlockUsers() throws RemoteException;
}

