
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Ledger implements Actions {

    private ArrayList<Block> blockChain = new ArrayList<>();
    private final int genesisPayment = 100;
    private final String genesisName = "Genesis";
    private int nonce, fact1, fact2, minedHash;
    private String hash;



    public Ledger() throws Exception {
        super();
            Block genesisBlock = new Block(100, genesisPayment, "Davou", "Genesis", new Date().getTime());

            blockChain.add(genesisBlock);
//            int semi = genesisBlock.getHash();
//            try {
//                Shor.main(semi);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

    }

    @Override
    public void addBlock(int payment, String senderName, String receiverName) throws RemoteException {

        Block newBlock = new Block(blockChain.get(blockChain.size()-1).getHash(), payment, senderName, receiverName,new Date().getTime());
        System.out.println("");
        System.out.println("Mining...");

        int semi = newBlock.getHash();
        try {
            fact1 = Shor.main(semi);
            fact2 = semi/fact1;
            minedHash = newBlock.mine(fact1, fact2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(minedHash == 0){
            throw new RemoteException("cannot mine block");
        }


        blockChain.add(newBlock);
        System.out.println("added, to blockchain!");
        return;

    }


    @Override
    public String getBlockUsers() throws RemoteException {
        String names = "Genesis\n";
        for (int i = 1; i < blockChain.size() + 1; i++) {
            names = names + blockChain.get(i - 1).getSenderName() + "\n";
        }
        return names;
    }



    public static void main(String[] args) {
        try {
            Ledger L = new Ledger();
            String name = "myserver";
            Actions stub = (Actions) UnicastRemoteObject.exportObject(L, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }


    }



}

