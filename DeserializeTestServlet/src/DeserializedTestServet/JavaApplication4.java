/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeserializedTestServet;

import Classes.Customer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Jigar
 */
public class JavaApplication4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Customer cust = null;
//        try {
//            FileInputStream fileIn = new FileInputStream("C:\\Temp\\879654-10.18.2014-09.38 PM.dat");
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            cust = (Customer) in.readObject();
//            in.close();
//            fileIn.close();
//        } catch (IOException i) {
//            i.printStackTrace();
//            return;
//        } catch (ClassNotFoundException c) {
//            System.out.println("Employee class not found");
//            c.printStackTrace();
//            return;
//        }
//        System.out.println("Deserialized Customer...");
//        System.out.println("Name: " + cust.getCustomerName());
//        System.out.println("ID: " + cust.getCustomerId());
//        for (int i=0; i<cust.getCartSize(); i++) {
//            System.out.println("Pizza Size: " + cust.getOrderElementAt(i).getSize() + " Toppings: " + cust.getOrderElementAt(i).ToppingsToString());
//        }
        System.out.println(System.getProperty("java.home"));
    }
}
