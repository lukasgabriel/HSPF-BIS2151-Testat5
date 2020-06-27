/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hspforzheim.items;

import java.util.ArrayList;

/**
 *
 * @author Cedric Jansen, Lukas Gabriel
 */
public class Dish extends Item {
    
    private boolean isVegan;
    private boolean isVegetarian; 
    private float price;
    
    private ArrayList<Flight> flightsThatContainDish = new ArrayList<Flight>();
    // Create this object.
    // 1. Call base constructor, 2. assign name variable, 3. create unique id
    // 4. add to static Item ArrayList
    // 5. back to Dish() constructor. Add this dish to the static ArrayList containing all dishes.
    public Dish(String name) {
       super(name);
       ArrayList<Dish> dishList = Item.getAllDishes();
       dishList.add(this);
    }
       
    public Dish(String name, boolean isVegan, boolean isVegetarian, float price) {
       super(name);
       ArrayList<Dish> dishList = Item.getAllDishes();
       dishList.add(this);
       setFoodProperties(isVegan, isVegetarian);
       this.price = price;
    }
    
    
    private void setFoodProperties(boolean isVegan, boolean isVegetarian) {
        if(isVegan) {
           this.isVegan = true;
           this.isVegetarian = true;
       }
       else {
           this.isVegan = false;
           this.isVegetarian = isVegetarian;     
       }
    
    }
    
    @Override
    public void add(Object object) {
        super.add(object);
        if(!(object instanceof Flight)) {
            System.out.println("Cannot add " + object.toString() + " to a dish");
        }
        else {
            Flight flight = (Flight) object;
            flightsThatContainDish.add(flight);
        } 
    }
    
    
    
    
    @Override
    public void delete() {
        super.delete();
        ArrayList<Flight> flights = Flight.getAllFlights();
        for(Flight flight : flights) {
            ArrayList<Dish> listOfDishesInFlight= flight.getDishes();
            if(listOfDishesInFlight.contains(this)) {
                listOfDishesInFlight.remove(this);
            }
        }
        allDishes.remove(this);
    }
    
 
    public ArrayList<Flight> getFlights() {
        return flightsThatContainDish;
    }
    
    public boolean isVegan() {
        return isVegan;
    }
    
    public boolean isVegetarian() {
        return isVegetarian;
    }
    
    public void isVegan(boolean value) {
        isVegan =  value;
    }
    
    public void isVegetarian(boolean value) {
        isVegetarian = value;
    }
    
    public float getPrice() {
        return price;
    }
    
    public void setPrice( float price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        String overview = "[Dish: [ID:" + id + "] : " + name + " (Vegan: " + isVegan + " | Vegetarian: "
                + isVegetarian + " | Price " + price + ")]"; 
        String flights = "";  
        if(flightsThatContainDish.size() > 0) {
            flights += "\n  Storing: ";
            for( Flight flight : flightsThatContainDish ) {
                flights +="[" + flight.id + "]: " + flight.name + ", ";
            }
        }
        else {
           flights = " << No flights assigned >> ";
        }
        return overview + flights;
    }
    
    
    
}
