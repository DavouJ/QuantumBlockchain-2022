


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;



public class Block {

    private int payment, nonce, firstHalf, secondHalf, hash, previousHash;
    private long timeStamp;
    private String senderName, receiverName ;
    private ArrayList<String> transactions = new ArrayList<>();
    private Integer numVer;
    Integer semiPrimeHash;
    private String strVer;
    //private String[] transactions;


    public Block(int previousHash, int payment, String senderName, String receiverName, long timeStamp){
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

    public int hash()  {
        String hashData = previousHash + Long.toString(timeStamp) + nonce + transactions.toString();

        MessageDigest digest;
        byte[] bytes = null;

        try {

            /*generate a semiprime number and make it a hash
            calculate the two prime factors that make it up
            store those two prime factors and make the
             */
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(hashData.getBytes(StandardCharsets.UTF_8));

            numVer = bytes.hashCode();

            System.out.println(numVer);
            System.out.println("");

            strVer = numVer.toString();

            String one = strVer.substring(0, (strVer.length()/2));
            String two = strVer.substring((strVer.length()/2));

            firstHalf = Integer.parseInt(one) % 10;
            secondHalf = Integer.parseInt(two) % 10;

            System.out.println(firstHalf);
            System.out.println(secondHalf);
            System.out.println("");

            boolean flag1 = false;

            while (flag1 == false) {
                System.out.println("checking if " + firstHalf + " is a prime");
                boolean flag2 = false;
                for (int i = 2; i <= firstHalf / 2; ++i) {
                    // condition for nonprime number
                    if (firstHalf % i == 0 ) {
                        flag2 = true;
                        break;
                    }

                }
                if (firstHalf == 0)
                    flag2 = true;
                if (!flag2) {
                    System.out.println(firstHalf + " is a prime number.");
                    flag1 = true;
                }
                else {
                    System.out.println(firstHalf + " isn't a prime number.");
                    firstHalf++;
                }
            }
            flag1 = false;

            while (flag1 == false) {
                System.out.println("checking if " + secondHalf + " is a prime");
                boolean flag2 = false;
                for (int i = 2; i <= secondHalf / 2; ++i) {
                    // condition for nonprime number
                    if (secondHalf % i == 0 & secondHalf == 0) {
                        flag2 = true;
                        break;
                    }
                }
                if (secondHalf == 0)
                    flag2 = true;
                if (!flag2) {
                    System.out.println(secondHalf + " is a prime number.");
                    flag1 = true;
                }
                else {
                    System.out.println(secondHalf + " isn't a prime number.");
                    secondHalf++;
                }
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        semiPrimeHash = firstHalf * secondHalf;
        System.out.println("");

        System.out.println(firstHalf + " and " + secondHalf + " are the prime numbers");
        System.out.println("hash is: " + semiPrimeHash);

       return semiPrimeHash;

    }

    public int mine(int fact1, int fact2){


        if (fact1 == firstHalf || fact1 == secondHalf)
            if(fact1 == firstHalf || fact1 == secondHalf) {
                System.out.println("mined!");
                return 1;
            }
        return 0;

    }

    public String getReceiverName(){
        return receiverName;
    }

    public String getSenderName(){
        return senderName;
    }

    public int getHash(){
        return hash;
    }




}
