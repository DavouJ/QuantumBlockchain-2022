//package blockchain;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;



public class Block {

    private int payment, nonce;
    private long timeStamp;
    private String senderName, receiverName, hash, previousHash;
    private ArrayList<String> transactions = new ArrayList<>();
    //private String[] transactions;


    public Block(String previousHash, int payment, String senderName, String receiverName, long timeStamp){

        this.previousHash = previousHash;
        this.payment = payment;
        this.timeStamp = timeStamp;
        this.receiverName = receiverName;
        this.senderName = senderName;

        transaction();

        hash = hash();
    }

    public void transaction(){
        if(receiverName.equals("Genesis")) {
            transactions.add(senderName + " sent " + payment + " to Gensis");
        }
        else {
            transactions.add(senderName + " sent " + payment + " to " + receiverName);
        }

    }

    public String hash()  {
        String hashData = previousHash + Long.toString(timeStamp) + nonce + transactions.toString();

        MessageDigest digest;
        byte[] bytes = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(hashData.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        StringBuffer buffer = new StringBuffer();

       for (byte b : bytes) {
           buffer.append(String.format("%02x", b));
       }

       return buffer.toString();

    }

    public String getPreviousHash() {
        return previousHash;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    public String getSenderName(){
        return senderName;
    }

    public String getHash(){
        return hash;
    }

    public int getNonce(){
        return nonce;
    }

    public void setNonce(int nonce){
        this.nonce = nonce;
    }


}
