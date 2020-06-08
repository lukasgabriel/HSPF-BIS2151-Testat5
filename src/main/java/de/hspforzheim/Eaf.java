/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hspforzheim;

import de.hspforzheim.items.Dish;
import de.hspforzheim.items.DishManager;
import de.hspforzheim.items.Flight;
import de.hspforzheim.items.FlightManager;
import de.hspforzheim.menues.DishMenu;
import de.hspforzheim.menues.FlightMenu;
import de.hspforzheim.menues.Menu;

/**
 *
 * @author Cedric Jansen, Lukas Gabriel
 */

// Start of the whole program. Implements the
// public static void main() method.

public class Eaf {

    // The reference for the main menu. Handles console out/ and input.
    private static Menu menu;
    // The reference for the flight menu. Handles console out/ and input. Extends the main menu.
    private static FlightMenu flightMenu;
    // The reference for the dish menu. Handles console out/ and input. Extends the main menu.
    private static DishMenu dishMenu;
    
    // The reference for the flight manager. Handles the creation, update, deletion and displaying of flights.
    private static FlightManager flightManager;
    
    //The reference for the dish manager. Handles the creation, update, deletion and displaying of dishes.
    private static DishManager dishManager;

    // Old program entry point for Testat 3
    // New entry point can be found in the Main class.
    public static void oldMain(String[] args) {
        // Initialise all needed objects.
        // Initialise the managers before initialising the menues.
        flightManager = new FlightManager();
        dishManager = new DishManager();
        
        
        menu = new Menu();
        flightMenu = new FlightMenu();
        dishMenu = new DishMenu();
      
        testSetup();
        
        // Start the main menu.
        menu.start();
    }

    
    private static void testSetup() {
          
        Flight f1 = new Flight("New York - Paris", "NYL", 150, 2);
        Flight f2 = new Flight("Paris - Berlin", "PBL", 150, 1);
        Flight f3 = new Flight("Munich - Rome", "ZRA", 100, 5);
        Flight f4 = new Flight("London - Amsterdam", "LAF", 30, 5);
        
        Dish d1 = new Dish("Butter Chicken", false, false, 7f);
        Dish d2 = new Dish("Vegetarian lasagna", false, true, 9f);
        Dish d3 = new Dish("Vegan rice curry", true, true, 12f);
    }
    
    // Getters and setters.
    public static void setFlightMenu(FlightMenu flightMenuToSet) {
        flightMenu = flightMenuToSet;
    }

    public static FlightMenu getFlightMenu() {
        return flightMenu;
    }

    public static DishMenu getDishMenu() {
        return dishMenu;
    }

    public static FlightManager getFlightManager() {
        return flightManager;
    }

    public static DishManager getDishManager() {
        return dishManager;
    }

}
