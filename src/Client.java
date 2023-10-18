
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class  Client {

    private Actions ledger = null;
    private Block action = null;
    private int amount, choice_1;
    private String name, receiverName;

    Scanner scan = new Scanner(System.in);


    public Client() {
        try{

            String name = "myserver";
            Registry registry = LocateRegistry.getRegistry("localhost");
            ledger = (Actions) registry.lookup(name);




        } catch (Exception e){
            System.err.println("Excpetion:");
            e.printStackTrace();
        }




;
        start();

    }

    public void start(){

        System.out.println("Enter your name:");
        System.out.println("________________");
        name = scan.nextLine();

        System.out.println("What would you like to do?:");
        System.out.println("0. Send money");
        System.out.println("________________");
        choice_1 = scan.nextInt();
        scan.nextLine();

        if(choice_1 == 0){
            sendMoney();
        }
    }

    public void sendMoney(){
        try {
            System.out.println("Who would you like to send money to?:");
            String names = ledger.getBlockUsers();
            System.out.println(names);
            System.out.println("________________");
            receiverName = scan.nextLine();
        } catch (Exception e){
            System.err.println("Couldn't retrieve names:");
            e.printStackTrace();
        }
        try {
            System.out.println("How much money would you like to send?");
            System.out.println("________________");
            amount = scan.nextInt();
        } catch (Exception e){
            System.err.println("Enter valid amount");
            e.printStackTrace();
        }

        try {
            ledger.addBlock(amount, name, receiverName);
            String pound = "\u00a3";
            System.out.println("Successfully added to ledger, " + pound + amount + " was sent to " + receiverName );
        } catch (Exception e){
            System.err.println("Couldn't add to ledger");
            e.printStackTrace();
        }

        start();
    }

    public static void main(String[] args){

        new Client();


    }
}


