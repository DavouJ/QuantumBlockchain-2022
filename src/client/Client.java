package client;//package blockchain;


//import blockchain.Actions;
//import blockchain.Block;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class  Client {

    Actions ledger = null;
    Block action = null;

    public Client() {
        try{

            String name = "myserver";
            Registry registry = LocateRegistry.getRegistry("localhost");
            ledger = (Actions) registry.lookup(name);


        } catch (Exception e){
            System.err.println("Excpetion:");
            e.printStackTrace();
        }


        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your name:");
        System.out.println("________________");
        String name = scan.nextLine();

        try {
            System.out.println("Who would you like to send money to?:");
            String names = ledger.getBlockUsers();
            System.out.println("________________");
            String senderName = scan.nextLine();
        } catch (Exception e){
            System.err.println("Couldn't retrieve names:");
            e.printStackTrace();
        }
    }





    public static void main(String[] args){

        new Client();
    }
}


