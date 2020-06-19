
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import org.apache.derby.drda.NetworkServerControl;

/**
 *
 * @author Cedric Jansen
 */
public class DataBase {

    private static Main main;
    private ArrayList<Flight> deserializedFlights = new ArrayList<>();
    private ArrayList<Dish> deserializedDishes = new ArrayList<>();

    // Database connection
    
    private NetworkServerControl serverControl; // Needed when the JavaDB server is offline
    private DatabaseMetaData metaData;
    private Connection connection;
    private static String dbURL = "jdbc:derby:eaf;create=true";

    public DataBase(Main main) {
        DataBase.main = main;
        // Tries to connect to the database.
        createConnection();
        // Check existance of tables
        checkMetaData();
        // Flight setup -- TESTING 
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

    // Build a connection to the database
    private void createConnection()  {
        try {   
            // Set up the connection
            System.out.println("[DATABASE] ...Creating connection to the database...");
            connection = DriverManager.getConnection(dbURL);
            // Meta data from the database
            metaData = connection.getMetaData();
            System.out.println("[DATABASE] Connection successfully established.");
        }
        catch (SQLException e) {
            System.out.println("[DATABASE] Failed to connect to the database.");
        }   
  
    }

    // Check the meta data for existance of tables
    private void checkMetaData() {
        try {
            // Check if meta data for a flight table exists
            ResultSet tableFlight = metaData.getTables(null, null, "FLIGHTS", null);
            if (tableFlight.next()) {
                // Table exists :)
                System.out.println("[DATABASE] Found flight table inside the database.");
            } else {
                System.out.println("[DATABASE] The databse seems to not contain a table to store the flights.");
                System.out.println("[DATABASE] ...Creating a new table for flights...");
                createFlightTable();
            }
            // Check if dish table exists
            ResultSet tableDish = metaData.getTables(null, null, "DISHES", null);
            if (tableDish.next()) {
                // Table exists!
                System.out.println("[DATABASE] Found dish table inside the database.");
            } else {
                System.out.println("[DATABASE] The databse seems to not contain a table to store the dishes.");
                System.out.println("[DATABASE] ...Creating a new table for dishes...");
                createDishTable();
            }
            System.out.println("\n[DATABASE] Successfully established a connection to the database and tables. \n");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Create flight table
    private void createFlightTable() throws SQLException {
        connection
                .prepareStatement(
                        "CREATE TABLE FLIGHTS ("
                        + "flightId int NOT NULL PRIMARY KEY,"
                        + "flightName varchar(255),"
                        + "startAirport varchar(255),"
                        + "destinationAirport varchar(255),"
                        + "dishCapacity int,"
                        + "passengerCapacity int)")
                .execute();
        System.out.println("[DATABASE] Successfully created a new table for flight objects.");
    }

    // Create dish table
    private void createDishTable() throws SQLException {
        connection
                .prepareStatement(
                        "CREATE TABLE DISHES ("
                        + "dishId int NOT NULL PRIMARY KEY,"
                        + "dishName varchar(255),"
                        + "isVegan boolean,"
                        + "isVegetarian boolean)")
                .execute();
        System.out.println("[DATABASE] Successfully created a new table for dish objects.");
    }

   

    public ArrayList<Dish> getDeserializedDishes() {
        return deserializedDishes;
    }

    public ArrayList<Flight> getDeserializedFlights() {
        return deserializedFlights;
    }
}
