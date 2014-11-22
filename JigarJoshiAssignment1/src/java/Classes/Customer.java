package Classes;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jigar
 */
//Implements Serializable inorder to write the object
public class Customer implements Serializable{
    private String custId,custName;
    private ArrayList<PizzaOrder> shoppingCart;
    
    public Customer(){}
    
    public Customer(String custId, String custName){
        shoppingCart = new ArrayList<PizzaOrder>();
        this.setCustomerId(custId);
        this.setCustomerName(custName);
    }

    public String getCustomerId() {
        return custId;
    }

    public void setCustomerId(String custId) {
        this.custId = custId;
    }

    public String getCustomerName() {
        return custName;
    }

    public void setCustomerName(String custName) {
        this.custName = custName;
    }
    
    public ArrayList<PizzaOrder> getShoppingCart() {
        return shoppingCart;
    }

    public void addToCart(PizzaOrder order) {
        this.shoppingCart.add(order);
    }    
    
    public int getCartSize(){
        return shoppingCart.size();
    }
    
    public PizzaOrder getOrderElementAt(int i){
        return shoppingCart.get(i);
    }
    
    
}
