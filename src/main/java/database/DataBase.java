
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import items.Dish;
import items.Flight;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;


/**
 *
 * @author Cedric Jansen
 */
public class DataBase {

    private static Main main;
    
    private ArrayList<Flight> databaseFlights = new ArrayList<>();
    private ArrayList<Dish> databaseDishes = new ArrayList<>();

    // Database connection
    private static final String DB_FILENAME = "flights.db";
    private static final String DB_PATH = "datastorage/" + DB_FILENAME;
    private static final String URL = "jdbc:sqlite:" + DB_PATH;
    
    public DataBase(Main main) {
        DataBase.main = main;
        
     
        // Flight setup -- TESTING 
        Flight f1, f2, f3, f4;
        f1 = new Flight("Test1", "TST", "Amsterdam", "New York", 150, 2);
        f2 = new Flight("Test2", "TS2", "Berlin", "Muhnich", 100, 3);
        f3 = new Flight("Test3", "TS3", "Stuttgart", "Hamburg", 120, 3);
        f4 = new Flight("Test4", "TS4", "Stuttgart", "Hamburg", 120, 3);

        

        Dish d1, d2, d3, d4;
        d1 = new Dish("Butterchicken", false, false, 10f);
        d2 = new Dish("Vegan curry", true, true, 12f);
        d3 = new Dish("Käsespätzle", false, true, 8f);
        d4 = new Dish("Spaghetti Carbonara", false, false, 9f);

        

        // Add some dishes to flights.
        f1.add(d1);
        f1.add(d2);
        f2.add(d3);
        f2.add(d4);
        f3.add(d4);
        // Currently, flights are hardcoded. But the chosen code design enables an easy
        // futher implementation of deserilisation.  
    }

    /**
     * Create a connection to the database to queue Queries.
     */
    
    private Connection connect() {
        Connection conn;
        try {
            System.out.println("[DATABASE] Trying to connect to the database...");
            conn = DriverManager.getConnection(URL);
            System.out.println("[DATABASE] Connection established.");
        }
        catch( SQLException e) {
            System.out.println("[ERROR] Failed to connect to the database.");
            return null;
        }
        return conn;
    }
    
   
    public ArrayList<Flight> getFlightData() {
        String sql = "SELECT * FROM flights";
        try( Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            // While we read new data from the database
            while(resultSet.next()) {
                String id = resultSet.getString("flightId");
                String name = resultSet.getString("name");
                String start = resultSet.getString("start");
                String dest = resultSet.getString("dest");
                int passengers = resultSet.getInt("passengers");
                int dishCap = resultSet.getInt("dishCapacity");
                
                Flight f = new Flight(id, name, start, dest, passengers, dishCap, true);
                databaseFlights.add(f);
            }
            conn.close();
        }
        catch (SQLException e) 
        {
            System.out.println("[ERROR] Failed to retrieve flight data."); 
            System.out.println(e.toString());
           
        }
        return databaseFlights;
    }
    
    
     public ArrayList<Dish> getDishData() {
        
        return databaseDishes;
    }
    
    
    public void insertFlight(Flight flight) 
    {
        //SQL query is prepared with not fixed values
        //The question marks (?) will be replaced by the actual values.
        String sql = "INSERT INTO flights(flightId,name, start, dest, passengers, dishCapacity) VALUES(?,?,?,?,?,?)";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            pstmt.setString(1, flight.getId());
            pstmt.setString(2, flight.getName());
            pstmt.setString(3, flight.getStartAirport());
            pstmt.setString(4, flight.getDestinationAirport());
            pstmt.setInt(5, flight.getMaxPassengers());
            pstmt.setInt(6, flight.getDishCapacity() );
            
            
            //now completed sql statement is executed
            pstmt.executeUpdate();
            System.out.println("[DATABASE] " + flight.getName() + " added into database.");
            conn.close();
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }
    
  

    

  
   

}
