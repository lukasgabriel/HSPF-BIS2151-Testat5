
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import items.Dish;
import items.Flight;
import java.io.File;
import java.sql.Connection;
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
 * @author Cedric Jansen, Lukas Gabriel
 */
public class DataBase implements Runnable {

    private static Main main;
    private boolean loading;

    private long savingInterval = 600000; // In miliseconds

    private ArrayList<Flight> databaseFlights = new ArrayList<>();
    private ArrayList<Dish> databaseDishes = new ArrayList<>();

    // Database connection
    private static final String DB_FILENAME = "flights.db";
    private static final String DB_PATH = "datastorage/" + DB_FILENAME;
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    public DataBase(Main main) {
        System.out.println("[DATABASE] Initialising database...");
        DataBase.main = main;
        
        // Check if the database at path exists, otherwise call the frame to handle this.
        if(!isExistingAtPath()) main.databaseNotExisting();
         
        
    }
    
    public final boolean isExistingAtPath() {
        File database = new File(DB_PATH);
        return database.exists();
    }

    
    public void startAutosave() {
        // Save every 10 minutes
        Thread thread = new Thread(this);
        thread.start();
    }
   
    // Automatic data saving every 10 minutes.
    // Redundant because the data gets saved with every change but
    // implemented nevertheless.
    public void run() {
        System.out.println("[AUTOSAVE] Initialising autosaving.");
        long currentInterval = savingInterval;
        int minute = 60000;
        while (true) {
            try {
                Thread.sleep(2000);
                System.out.println("[AUTOSAVE] Automatic saving in " + currentInterval / minute + " minutes.");
                Thread.sleep(minute);
            } catch (InterruptedException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
            currentInterval -= minute;
            if (currentInterval <= 0) {
                System.out.println("[AUTOSAVE] Saving...");
                currentInterval = savingInterval;
            }
        }
    }

    /**
     * Create a connection to the database to queue Queries.
     */
    private Connection connect() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("[ERROR] Failed to connect to the database.");
            return null;
        }
        return conn;
    }

    private static Connection staticConnect() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("[ERROR] Failed to connect to the database.");
            return null;
        }
        return conn;
    }

    public ArrayList<Flight> getFlightData() {
        System.out.println("[DATABASE] Flight load request detected.");
        String sql = "SELECT * FROM flights";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            // While we read new data from the database
            while (resultSet.next()) {
                String id = resultSet.getString("flightId");
                String name = resultSet.getString("name");
                String start = resultSet.getString("start");
                String dest = resultSet.getString("dest");
                int passengers = resultSet.getInt("passengers");
                int dishCap = resultSet.getInt("dishCapacity");

                Flight f = new Flight(id, name, start, dest, passengers, dishCap, true);
                databaseFlights.add(f);
            }
            loading = false;
            resultSet.close();
            // Not neccesarry per se since they are included in a try block
            // Opened ressources added to the try block are closed by default since
            // Java 7. Sometimes driver could cause trouble when not closing.
            // Better save than sorry!.
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] Failed to retrieve flight data.");
            System.out.println(e.toString());

        }

        return databaseFlights;
    }

    public ArrayList<Dish> getDishData() {
        System.out.println("[DATABASE] Dish load request detected.");
        String sql = "SELECT * FROM dishes";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            // While we read new data from the database
            while (resultSet.next()) {
                String id = resultSet.getString("dishId");
                String name = resultSet.getString("name");
                String veganStatus = resultSet.getString("vegan");
                String vegetarianStatus = resultSet.getString("vegetarian");
                Float price = resultSet.getFloat("price");

                boolean isVegan = veganStatus.equals("true");
                boolean isVegetarian = vegetarianStatus.equals("true");

                Dish d = new Dish(name, id, isVegan, isVegetarian, price, true);
                databaseDishes.add(d);
            }        
            resultSet.close();
            // Not neccesarry per se since they are included in a try block
            // Opened ressources added to the try block are closed by default since
            // Java 7. Sometimes driver could cause trouble when not closing.
            // Better save than sorry!.
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] Failed to retrieve dish data.");
            System.out.println(e.toString());

        }
        return databaseDishes;
    }

    // Deletes a flight object from the database by the primary key
    public void deleteFlight(Flight flight) {
        String id = flight.getId();
        // SQL query
        String sql = "DELETE from flights WHERE flightId = ?";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            // Not neccesarry per se since they are included in a try block
            // Opened ressources added to the try block are closed by default since
            // Java 7. Sometimes driver could cause trouble when not closing.
            // Better save than sorry!
            pstmt.close();
            conn.close();
            System.out.println("[DATABASE] " + flight.getName() + " removed from database.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Deletes a dish object from the database by the primary key
    public void deleteDish(Dish dish) {
        String id = dish.getId();
        // SQL query
        String sql = "DELETE from dishes WHERE dishId = ?";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            // Not neccesarry per se since they are included in a try block
            // Opened ressources added to the try block are closed by default since
            // Java 7. Sometimes driver could cause trouble when not closing.
            // Better save than sorry!
            pstmt.close();
            conn.close();
            System.out.println("[DATABASE] " + dish.getName() + " removed from database.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Inserts a Flight object into the database.
    public void insertFlight(Flight flight) {
        //SQL query is prepared with not fixed values
        //The question marks (?) will be replaced by the actual values.
        String sql = "INSERT INTO flights(flightId,name, start, dest, passengers, dishCapacity) VALUES(?,?,?,?,?,?)";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flight.getId());
            pstmt.setString(2, flight.getName());
            pstmt.setString(3, flight.getStartAirport());
            pstmt.setString(4, flight.getDestinationAirport());
            pstmt.setInt(5, flight.getMaxPassengers());
            pstmt.setInt(6, flight.getDishCapacity());

            //now completed sql statement is executed
            pstmt.executeUpdate();
            // Not neccesarry per se since they are included in a try block
            // Opened ressources added to the try block are closed by default since
            // Java 7. Sometimes driver could cause trouble when not closing.
            // Better save than sorry!
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Inserts a Dish objct into the database.
    public void insertDish(Dish dish) {
        //SQL query is prepared with not fixed values
        //The question marks (?) will be replaced by the actual values.
        String sql = "INSERT INTO dishes(dishId,name, vegan, vegetarian, price) VALUES(?,?,?,?, ?)";

        boolean isVegan = dish.isVegan();
        boolean isVegetarian = dish.isVegetarian();
        String veganStatus = isVegan ? "true" : "false";
        String vegetarianStatus = isVegetarian ? "true" : "false";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dish.getId());
            pstmt.setString(2, dish.getName());
            pstmt.setString(3, veganStatus);
            pstmt.setString(4, vegetarianStatus);
            pstmt.setFloat(5, dish.getPrice());

            //now completed sql statement is executed
            pstmt.executeUpdate();
            System.out.println("[DATABASE] " + dish.getName() + " added into database.");
            // Not neccesarry per se since they are included in a try block
            // Opened ressources added to the try block are closed by default since
            // Java 7. Sometimes driver could cause trouble when not closing.
            // Better save than sorry!
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Dynamic database updates from class when update methods are called.
    // This way, we can hide the Database instance from Flight and Dish
    public static void update(Object sender) {
        if (sender instanceof Flight) {
            Flight f = (Flight) sender;
            flightUpdate(f);
        } else if (sender instanceof Dish) {
            Dish d = (Dish) sender;
            dishUpdate(d);
        }
    }

    private static void flightUpdate(Flight flight) {
        String sql = "UPDATE flights SET name=?,start=?,dest=?, passengers=?,dishCapacity=?"
                + "WHERE flightId=?";
        try (Connection conn = DataBase.staticConnect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flight.getName());
            pstmt.setString(2, flight.getStartAirport());
            pstmt.setString(3, flight.getDestinationAirport());
            pstmt.setInt(4, flight.getMaxPassengers());
            pstmt.setInt(5, flight.getDishCapacity());
            pstmt.setString(6, flight.getId());

            pstmt.executeUpdate();       
            // Not neccesarry per se since they are included in a try block
            // Opened ressources added to the try block are closed by default since
            // Java 7. Sometimes driver could cause trouble when not closing.
            // Better save than sorry!
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    private static void dishUpdate(Dish dish) {
        String sql = "UPDATE dishes SET name=?,vegan=?,vegetarian=?, price=?"
                + "WHERE dishId=?";
        try (Connection conn = DataBase.staticConnect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dish.getName());
            pstmt.setString(2, String.valueOf(dish.isVegan()));
            pstmt.setString(3, String.valueOf(dish.isVegetarian()));
            pstmt.setFloat(4, dish.getPrice());
            pstmt.setString(5, dish.getId());

            pstmt.executeUpdate();
            // Not neccesarry per se since they are included in a try block
            // Opened ressources added to the try block are closed by default since
            // Java 7. Sometimes driver could cause trouble when not closing.
            // Better save than sorry!
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

}
