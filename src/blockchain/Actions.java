package blockchain;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Actions extends Remote {
    void addBlock(int payment, String senderName, String receiverName) throws RemoteException;
    String mine(int prefix, Block newBlock) throws RemoteException;
    String getBlockUsers() throws RemoteException;

}
