/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hspforzheim.menues;

import de.hspforzheim.Eaf;
import de.hspforzheim.commands.MenuOperation;
import de.hspforzheim.commands.OperationType;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Cedric Jansen, Lukas Gabriel
 */


// Menu is the base class for a console menu.
public class Menu {
    
    // Get the current year.
    protected static final int YEAR_INT = Calendar.getInstance().get(Calendar.YEAR);
    
    // Final strings with values that do not need to be changed.
    protected final String YEAR_STRING = String.valueOf(YEAR_INT);
    protected final String HEADER = "\nEAF: EAT&FLY-Management";
    protected final String VERSION = "1.0";
    protected final int GROUP = 24;
    
    // Instance variables.
    protected String title;
    protected String subTitle;
    protected String enterChoice;
    protected int userInput;
    
    // Menu operations define all possible commands that a user inside this 
    // Menu can cast.
    private final MenuOperation exitProgram = new MenuOperation(0, "Program exit");
    private final MenuOperation manageFlights = new MenuOperation(1, "Managing flights");
    private final MenuOperation manageDishes = new MenuOperation(2,  "Managing dishes");
    
    // Array of all menu operations that this object contains. exitProgram should be the last element inside this array.
    private final MenuOperation[] menuOperations = new MenuOperation[] {manageFlights, manageDishes, exitProgram};
    
    // Constructor. Set title, subtitle and enterChoice string.
    public Menu() {
         this.title = "MAIN MENU";
         this.subTitle = "Please select:\n";
         this.enterChoice = "\nPlease enter your choice: ";
    }
    
    // Get the menu running. This is the core loop of the whole program
    // and can only be exited when the program is terminated.
    public void start() {
        while(true) {
            printFullHeader();
            printSubHeader();
            printCommands();
            printChoiceQuestion();
            userInput();
            handleUserInput();
        }  
    }
    
    
    // Handles the user Input:
    // 0: --> Terminate the program.
    // 1: --> Start the flight menu.
    // 2: --> Start the dish menu
    // Any other key: --> Continue the loop, display main menu again.
    // This method is overwritten by FlightMenu and DishMenu since both menus handle the
    // user input differently.
    protected void handleUserInput( ) {
        switch(userInput) {
            case 0: // User input is 0.
                System.exit(0);
                break;
            case 1: // User input is 1.
                FlightMenu flightMenu = Eaf.getFlightMenu();
                flightMenu.start();
                break;
            case 2: // User input is 2.
                DishMenu dishMenu = Eaf.getDishMenu();
                dishMenu.start();
                break;
            default: // User input is anything but 0, 1 or 2.
                // continues, aka. jumps back to the top inside the while loop
                // of public void start().
                break;
        }
    }
    
    // Get the user input iside the main menu.
    protected void userInput() {
        // Create a scanner object to read user input
        Scanner scanner = new Scanner(System.in);
        try {
            userInput = scanner.nextInt();
        } catch( InputMismatchException e) {
            // User has entered a value that does not represent an integer.
            // Set user input to anything but 0, 1 or 2 to display the main menu again.
            userInput = -1;
            scanner.next(); // ignore this input and clear stream for the next one.
        }
    }
    
    
    // Prints all commands that this main menu has access to. 
    // This is every MenuOperation that is created as a instance reference to this menu object
    // and added to the MenuOperation[] menuOperations array.
    // This design was created to make it easy to add further commands to this menu.
    protected void printCommands() {
        for(MenuOperation op : menuOperations) {  // loop to all MenuOperations inside the menuOperations array.
            if(op.getKey() == 0) { // if the input trigger is 0, this means the command exits the program.
                System.out.printf("\n%-18s%6s\n", op.getConsoleName(), op.getKey());
            }
            else { // any other command.
                 System.out.printf("%-18s%6s\n", op.getConsoleName(), op.getKey());
            } 
        }
    }
    
    protected void printChoiceQuestion() {
        System.out.print(enterChoice + " ");
    }
    
    protected void printSubHeader() {
        System.out.println(title);
        System.out.println(subTitle);
    }
    
    // Build a dynamic string out of the header, version, year and group number
    // and then print it.
    protected void printFullHeader() {
        StringBuilder stringBuilder = new StringBuilder(HEADER);
        String result;
        
        stringBuilder.append(" (Version ")
                .append(VERSION)
                .append(" (C)")
                .append(" ")
                .append(YEAR_STRING)
                .append(" by Group ")
                .append(String.valueOf(GROUP))
                .append(")\n");
        
        result = stringBuilder.toString();
        System.out.println(result);
    }
    
    
    protected void printMenuTitle() {
        System.out.println(title);
    }
    
    protected void printSubTitle() {
        System.out.println(subTitle);
    }
}
