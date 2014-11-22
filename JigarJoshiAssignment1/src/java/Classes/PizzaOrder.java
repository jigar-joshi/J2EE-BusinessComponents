package Classes;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jigar
 */
//implements serializable for writing in a dat file
public class PizzaOrder implements Serializable{
    private int size;
    private String[] toppings;
    
    public PizzaOrder(){}
    
    public PizzaOrder(int size, String[] toppings){
        this.setSize(size);
        this.setToppings(toppings);
    }
    
    public void setSize(int size){
        this.size = size;
    }
    
    public void setToppings(String[] toppings){
        this.toppings = toppings;
    }

    public int getSize(){
        return this.size;
    }
    
    public String[] getToppings(){
        return this.toppings;
    }
    
    public String ToppingsToString(){
        String output="";
        for(int i=0;i<this.toppings.length;i++){
            if(toppings.length - i == 1){
                output += toppings[i] + " ";
            }
            else{
                output += toppings[i] + ", ";
            }
        }
        
        return output;
    }
}
