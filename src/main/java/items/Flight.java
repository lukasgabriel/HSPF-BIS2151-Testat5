
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Cedric Jansen, Lukas Gabriel
 */
public class Flight extends Item {
  
    private String flightNumber;
    private String startAirport;
    private String destinationAirport;
    private int dishCapacity;
    private int maxPassengers;
    
    private ArrayList<Dish> storedDishes = new ArrayList<>();
    
    // Create this object.
    // 1. Call base constructor, 2. assign name variable, 3. create unique id
    // 4. add to static Item ArrayList
    // 5. back to Flight() constructor. Add this Flight to the static ArrayList containing all flights.
    public Flight(String name) {
       super(name);
       ArrayList<Flight> flightList = Item.getAllFlights();
       flightList.add(this);
    }
    
    public Flight(String name, String flightNumber, String startPoint, String destination, int maxPassengers, int dishCapacity) {
      this(name);
      this.dishCapacity = dishCapacity;
      this.flightNumber = flightNumber;
      this.maxPassengers = maxPassengers;
      this.startAirport = startPoint;
      this.destinationAirport = destination;
      System.out.println("\n" + this + " was created.");
    }
    
  
    @Override
    public void add(Object object) {
        super.add(object);
        if(!(object instanceof Dish)) {
            System.out.println("Cannot add " + object.toString() + " to a flight");
        }
        else {
            Dish dish = (Dish) object;
            if(storedDishes.size() > dishCapacity-1) {
                System.out.println("Couldn't add this dish to the flight since the maximum dish capacity of: "
                        + dishCapacity + " is reached");
            } else {
               storedDishes.add(dish);
               dish.add(this);
               System.out.println("Added: " + dish.getName() + " to flight " + this.getName() );
            }
            
        } 
    }
    
    //Returns the flight number consiting of the start letter of the startAirport
    // start letter of the destAirport and a random letter from the alphabet or digit number inbetween.
    public static String genFlightNumber(String start, String dest) {
        String alpha = "abcdefghijklmnopqrstuvwxyz1234567890";
        int random = ThreadLocalRandom.current().nextInt(1, alpha.length());
        String result = start.substring(0,1).toUpperCase();
        result += (String) alpha.substring(random-1,random);
        result += dest.substring(0,1).toUpperCase();
        return result;
    }
    
    // Return a flight with given id
    public static Flight flightById( String id ) {
        for( Flight f: getAllFlights()) {
            if( f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }
    
    
    // Item delete() deletes reference from static ArrayList containig Items
    // if afterwards reference stored inside the static ArrayList containing all
    // flights is deleted, this object will be garbage collected.
    @Override
    public void delete() {
        super.delete();
        ArrayList<Dish> dishes = Dish.getAllDishes();
        for(Dish dish : dishes) {
            ArrayList<Flight> listOfFlights = dish.getFlights();
            if(listOfFlights.contains(this)) {
                listOfFlights.remove(this);
            }
        }
        allFlights.remove(this);
    }

 
    @Override
    public String toString() {
        
        String overview = "[Flight: [ID:" + this.id + "] : " + this.name + "(Num: " + flightNumber + " | Max. passengers: "
                + maxPassengers + " | Max. dishes: " + dishCapacity + ") --> From: " + startAirport+ " -> " + destinationAirport+ "]"; 
        String dishes = "";
        dishes += "\n  Storing: ";
        if(storedDishes.size() > 0) {
            for( Dish dish : storedDishes ) {
                 dishes +="[" + dish.id + "]: " + dish.name + ", ";
            }
        }
        else {
           dishes = " << No dishes assigned >> ";
        }
        return overview + dishes;
    }
    
    
    public void update(String name, String startAiport, String destAirport) {
      update(name);
      this.startAirport = startAiport;
      this.destinationAirport = destAirport;
    }
    
    
    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getStartPoint() {
        return startAirport;
    }
    
    public ArrayList<Dish> getDishes() {
        return storedDishes;
    }
    
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    
    public String getFlightNumber() {
        return this.flightNumber;
    }
    
    public int getMaxPassengers() {
        return this.maxPassengers;
    }
    
    public void setMaxPassengers(int maxPassengers) {
        if(maxPassengers > 0) {
            this.maxPassengers = maxPassengers;
        }
    }
    
       public String getStartAirport() {
        return startAirport;
    }

    public void setStartAirport(String startAirport) {
        this.startAirport = startAirport;
    }

    public int getDishCapacity() {
        return dishCapacity;
    }

    public void setDishCapacity(int dishCapacity) {
        this.dishCapacity = dishCapacity;
    }
    

}
