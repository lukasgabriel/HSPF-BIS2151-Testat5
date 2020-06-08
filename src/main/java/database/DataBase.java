<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

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
    
    public DataBase(Main main) {
        this.main = main;
        
        Flight f1, f2, f3, f4;
        f1 = new Flight("Test1", "TST", "Amsterdam", "New York", 150, 2);
        f2 = new Flight("Test2", "TS2", "Berlin", "Muhnich", 100, 3);
        f3 = new Flight("Test3", "TS3", "Stuttgart", "Hamburg", 120, 3);
        f4 = new Flight("Test4", "TS4", "Stuttgart", "Hamburg", 120, 3);
        
        deserializedFlights.add(f1);
        deserializedFlights.add(f2);
        deserializedFlights.add(f3);
        deserializedFlights.add(f4);
    }
    
    
    
    public ArrayList<Flight> getDeserializedFlights() {
        return deserializedFlights;
    }
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

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
    
    public DataBase(Main main) {
        this.main = main;
        
        Flight f1, f2, f3, f4;
        f1 = new Flight("Test1", "TST", "Amsterdam", "New York", 150, 2);
        f2 = new Flight("Test2", "TS2", "Berlin", "Muhnich", 100, 3);
        f3 = new Flight("Test3", "TS3", "Stuttgart", "Hamburg", 120, 3);
        f4 = new Flight("Test4", "TS4", "Stuttgart", "Hamburg", 120, 3);
        
        deserializedFlights.add(f1);
        deserializedFlights.add(f2);
        deserializedFlights.add(f3);
        deserializedFlights.add(f4);
    }
    
    
    
    public ArrayList<Flight> getDeserializedFlights() {
        return deserializedFlights;
    }
}
>>>>>>> 2122bab... Add project files
