//package blockchain;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;


public class Ledger implements Actions {

    private ArrayList<Block> blockChain = new ArrayList<>();
    private final int prefix = 4;
    private final int genesisPayment = 100;
    private final String genesisName = "Genesis";
    private int nonce;
    private String hash;
    String prefString = new String(new char[prefix]).replace('\0','0');


    public Ledger() {
        super();

        Block genesisBlock = new Block(null, genesisPayment, "Davou", "Genesis", new Date().getTime());
        blockChain.add(genesisBlock);
    }

    @Override
    public void addBlock(int payment, String senderName, String receiverName) throws RemoteException {

        Block newBlock  = new Block( blockChain.get(blockChain.size()-1).getHash(), payment, senderName, receiverName,new Date().getTime());

        if(!newBlock.getHash().substring(0,prefix).equals(prefString)){
            throw new RemoteException("cannot add block");
        }
        blockChain.add(newBlock);
    }

    public void isChainValid() throws Exception{
        boolean check = true;
        for (int i = 0; i < blockChain.size(); i++) {
            String previousHash = i == 0 ? "0" : blockChain.get(i).getHash();
            check = blockChain.get(i).getHash().equals(blockChain.get(i).hash()) &&
                    previousHash.equals(blockChain.get(i).getPreviousHash()) &&
                    blockChain.get(i).getHash().substring(0, prefix).equals(prefString);
            if (!check) break;
        }

        if(!check){
            throw new Exception("invalid blockchain");
        }
    }


    @Override
    public String mine(int prefix, Block newBlock){

        String prefString = new String(new char[prefix]).replace('\0', '0');

        while(!newBlock.getHash().substring(0,prefix).equals(prefString)){
            nonce = newBlock.getNonce();
            nonce ++;
            newBlock.setNonce(nonce);

            hash = newBlock.hash();
        }
        return hash;
    }

    @Override
    public String getBlockUsers() throws RemoteException{
        String names = null;
        for (int i = 0; i < blockChain.size(); i++){
            names = names + i + ". " + blockChain.get(i).getSenderName() + "\n";
        }

        return names;
    }


    public static void main(String[] args) {
        try{
            Ledger L = new Ledger();
            String name = "myserver";
            Actions stub = (Actions) UnicastRemoteObject.exportObject(L,0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name,stub);
            System.out.println("Server ready");
        } catch (Exception e){
            System.err.println("Exception:");
            e.printStackTrace();
        }


    }

}
