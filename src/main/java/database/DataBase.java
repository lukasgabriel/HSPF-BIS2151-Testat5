
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import items.Dish;
import items.Flight;
import java.util.ArrayList;
import main.Main;

/**
 *
 * @author Cedric Jansen
 */
public class DataBase {
    
    private static Main main;
    private ArrayList<Flight> deserializedFlights = new ArrayList<>();
    private ArrayList<Dish> deserializedDishes = new ArrayList<>();
    
    public DataBase(Main main) {
        DataBase.main = main;
        
        // Flight setup
        Flight f1, f2, f3, f4;
        f1 = new Flight("Test1", "TST", "Amsterdam", "New York", 150, 2);
        f2 = new Flight("Test2", "TS2", "Berlin", "Muhnich", 100, 3);
        f3 = new Flight("Test3", "TS3", "Stuttgart", "Hamburg", 120, 3);
        f4 = new Flight("Test4", "TS4", "Stuttgart", "Hamburg", 120, 3);
        
         
        deserializedFlights.add(f1);
        deserializedFlights.add(f2);
        deserializedFlights.add(f3);
        deserializedFlights.add(f4);
        
        Dish d1, d2, d3, d4;
        d1 = new Dish("Butterchicken", false, false, 10f);
        d2 = new Dish("Vegan curry", true, true, 12f);
        d3 = new Dish("Käsespätzle", false, true, 8f);
        d4 = new Dish("Spaghetti Carbonara", false, false, 9f);
        
        deserializedDishes.add(d1);
        deserializedDishes.add(d2);
        deserializedDishes.add(d3);
        deserializedDishes.add(d4);
        
        
        // Add some dishes to flights.
        f1.add(d1);
        f1.add(d2);
        f2.add(d3);
        f2.add(d4);
        f3.add(d4);
        // Currently, flights are hardcoded. But the chosen code design enables an easy
        // futher implementation of deserilisation.
       
    }
    
    public ArrayList<Dish> getDeserializedDishes() {
        return deserializedDishes;
    }
    
    public ArrayList<Flight> getDeserializedFlights() {
        return deserializedFlights;
    }
}

