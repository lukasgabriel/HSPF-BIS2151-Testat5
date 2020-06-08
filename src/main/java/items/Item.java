<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Cedric Jansen, Lukas Gabriel
 */

// Base class for all items. 
public class Item {
    
    protected static ArrayList<Item> allItems = new ArrayList<>();
    protected static ArrayList<Dish> allDishes = new ArrayList<>();
    protected static ArrayList<Flight> allFlights = new ArrayList<>();
    
    protected String id;
    protected String name;
    
    public Item(String name) {
        this.name = name;
        generateUniqueId();
 
    }
    
    
    public void add(Object object) {
       if( !(object instanceof Item)){
           System.out.println("Cannot add this item to" + this);
       }
    }
    
    // Delete this item from the ArrayList.
    // Garbage collector will handle this object if all references are deleted.
    public void delete() {
        allItems.remove(this); 
    }
    
    @Override
    public String toString() {
        return "[Item: [ID:" + this.id + "] " + this.name + "]"; 
    }
    

    // Generates a unique id for each item with the length
    // of 6 characters.
    private void generateUniqueId() {
        UUID idGen = UUID.randomUUID();
        id = idGen.toString().substring(0,6);
    }
    
    // Object getters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if(!(name.length() < 3 || name.length() > 64)) {
            this.name = name;
        }
     
    }
    
    
    // Static getters
    
    public static ArrayList<Item> gettAllItems() {
        return allItems;
    }
    
    public static ArrayList<Flight> getAllFlights() {
        return allFlights;
    }
    
    public static ArrayList<Dish> getAllDishes() {
        return allDishes;
    }
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Cedric Jansen, Lukas Gabriel
 */

// Base class for all items. 
public class Item {
    
    protected static ArrayList<Item> allItems = new ArrayList<>();
    protected static ArrayList<Dish> allDishes = new ArrayList<>();
    protected static ArrayList<Flight> allFlights = new ArrayList<>();
    
    protected String id;
    protected String name;
    
    public Item(String name) {
        this.name = name;
        generateUniqueId();
 
    }
    
    
    public void add(Object object) {
       if( !(object instanceof Item)){
           System.out.println("Cannot add this item to" + this);
       }
    }
    
    // Delete this item from the ArrayList.
    // Garbage collector will handle this object if all references are deleted.
    public void delete() {
        allItems.remove(this); 
    }
    
    @Override
    public String toString() {
        return "[Item: [ID:" + this.id + "] " + this.name + "]"; 
    }
    

    // Generates a unique id for each item with the length
    // of 6 characters.
    private void generateUniqueId() {
        UUID idGen = UUID.randomUUID();
        id = idGen.toString().substring(0,6);
    }
    
    // Object getters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if(!(name.length() < 3 || name.length() > 64)) {
            this.name = name;
        }
     
    }
    
    
    // Static getters
    
    public static ArrayList<Item> gettAllItems() {
        return allItems;
    }
    
    public static ArrayList<Flight> getAllFlights() {
        return allFlights;
    }
    
    public static ArrayList<Dish> getAllDishes() {
        return allDishes;
    }
}
>>>>>>> 2122bab... Add project files
