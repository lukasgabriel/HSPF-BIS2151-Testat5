<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Cedric Jansen, Lukas Gabriel
 */
public class FlightManager extends Manager {

    private static final String ASK_FOR_FLIGHT_NAME = "Please enter a FLIGHT NAME. Must be between 3 and 64 characters: ";
    private static final String ASK_FOR_FLIGHT_NUMBER = "Please enter a FLIGHT NUMBER \n(any 3 chars e.g. LTH, can contain numbers, special characters, etc.):";
    private static final String ASK_FOR_DISH_CAPACITY = "Please enter a DISH CAPACITY (1-5): ";
    private static final String ASK_FOR_PASSENGER_CAPACITY = "Please enter a MAXIMUM PASSENGER AMOUNT(1-200): ";

    public void updateFlight() {
        Scanner scanner = new Scanner(System.in);
        Flight flightToUpdate = chooseFlight(scanner);
        updateName(flightToUpdate, scanner);
        updateFlightNumber(flightToUpdate, scanner);
        updateMaxPassengers(flightToUpdate, scanner);
        System.out.println("Flight update complete!");
    }

    public void deleteFlight() {
        super.delete(this);
    }

    public void addFlight() {
        Scanner scanner = new Scanner(System.in);
        String name = checkName(scanner);
        String flightNumber = checkFlightNumber(scanner);
        int dishCapacity = checkDishCapacity(scanner);
        int maxPassengers = checkPassengerCapacity(scanner);

        //Flight flight = new Flight(name, flightNumber, maxPassengers, dishCapacity);
    }

    public void addDishToFlight() {
        ArrayList<Dish> allAvailableDishes = Dish.getAllDishes();
        ArrayList<Flight> allFlights = Flight.getAllFlights();

        int dishInput;
        int flightInput;
        Dish dishToAdd;
        Flight flightToAddDishTo;

        if (allAvailableDishes.isEmpty()) {
            System.out.println("No dishes available.");
            return;
        }

        if (allFlights.isEmpty()) {
            System.out.println("No flights to add a dish to.");
        }

        dishInput = getDishInput(allAvailableDishes);
        dishToAdd = allAvailableDishes.get(dishInput);

        flightInput = getFlightInput(allFlights);
        flightToAddDishTo = allFlights.get(flightInput);

        if (flightToAddDishTo.getDishes().contains(dishToAdd)) {
            System.out.println("Flight already contains dish.");
        } else {
            flightToAddDishTo.add(dishToAdd);
            dishToAdd.add(flightToAddDishTo);
        }
    }

    private void updateMaxPassengers(Flight flight, Scanner scanner) {
        String confirmation = "";
        int newPassengers;
        System.out.print("Do you want to update the passenger capacity?");
        System.out.println(" Current capacity is: " + flight.getMaxPassengers());
        System.out.print("Please enter: (Y/N)");
        while (confirmation.isEmpty()) {
            
            try {
                confirmation = scanner.nextLine();
                confirmation = confirmation.trim();
                confirmation = confirmation.toUpperCase();
                if (confirmation.equals("Y")) {
                    newPassengers = checkPassengerCapacity(scanner);
                    flight.setMaxPassengers(newPassengers);
                } else {
                    if (confirmation.equals("N")) {
                        System.out.println("No update for flight number.");
                        break;
                    } else {
                        confirmation = "";
                    }
                }

            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                confirmation = "";
                scanner.next();
            }
            confirmation = confirmation.trim();
        }
    }

    private void updateFlightNumber(Flight flight, Scanner scanner) {
        String confirmation = "";
        String newNumber;
        System.out.print("Do you want to update the flight's number?");
        System.out.println(" Current flight number is: " + flight.getFlightNumber());
        System.out.print("Please enter: (Y/N)");
        while (confirmation.isEmpty()) {
            
            try {
                confirmation = scanner.nextLine();
                confirmation = confirmation.trim();
                confirmation = confirmation.toUpperCase();
                if (confirmation.equals("Y")) {
                    newNumber = checkFlightNumber(scanner);
                    flight.setFlightNumber(newNumber);
                } else {
                    if (confirmation.equals("N")) {
                        System.out.println("No update for flight number.");
                        break;
                    }
                    else {
                        confirmation = "";
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                confirmation = "";
                scanner.next();
            }
            confirmation = confirmation.trim();
        }
    }

    private void updateName(Flight flight, Scanner scanner) {
        String confirmation = "";
        String name = "";
        System.out.print("Do you want to update the flight's name?");
        System.out.println(" Current name is: " + flight.getName());
        System.out.print("Please enter: (Y/N)");
        while (confirmation.isEmpty()) {
            try {
                
                confirmation = scanner.nextLine();
                confirmation = confirmation.trim();
                confirmation = confirmation.toUpperCase();
                if (confirmation.equals("Y")) {
                    name = checkName(scanner);
                    flight.setName(name);
                } else {
                    if (confirmation.equals("N")) {
                        System.out.println("No update for name.");
                        break;
                    }
                    else {
                        confirmation = "";
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                confirmation = "";
                scanner.next();
            }
            confirmation = confirmation.trim();
        }
    }

    private Flight chooseFlight(Scanner scanner) {
        ArrayList<Flight> flights = Flight.getAllFlights();
        int input = -1;
        System.out.println("Please choose a flight to update by entering it's index:");
        for (int i = 0; i < flights.size(); i++) {
            Flight flight = flights.get(i);
            System.out.println(i + " : " + flight.getName() + " [" + flight.getId() + "]");
        }
        while (input < 0 || input > flights.size() - 1) {
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("An error occured. Please try again.");
                input = -1;
                scanner.next();
            }
        }
        return flights.get(input);

    }

    private int getFlightInput(ArrayList<Flight> allFlights) {
        System.out.println("Please enter the index of the flight to which you want to add the dish to");
        System.out.println("All flights available:");
        for (int i = 0; i < allFlights.size(); i++) {
            String output = "";
            output += allFlights.get(i).name;
            output += " : " + i;
            System.out.println(output);
        }
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        while (input < 0 || input > allFlights.size() - 1) {
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                input = -1;
                scanner.next();
            }
        }

        return input;
    }

    private int getDishInput(ArrayList<Dish> dishes) {
        System.out.println("Please enter the index of the dish to add.");
        System.out.println("All dishes available:");
        for (int i = 0; i < dishes.size(); i++) {
            String output = "";
            output += dishes.get(i).name;
            output += " : " + i;
            System.out.println(output);
        }
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        while (input < 0 || input > dishes.size() - 1) {
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                input = -1;
                scanner.next();
            }
        }
        return input;
    }

    private int checkPassengerCapacity(Scanner scanner) {
        int passengerNumber = 0;
        while (passengerNumber < 1 || passengerNumber > 200) {
            System.out.print(ASK_FOR_PASSENGER_CAPACITY);
            try {
                passengerNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                passengerNumber = 0;
                scanner.next();
            }

        }
        return passengerNumber;
    }

    private int checkDishCapacity(Scanner scanner) {
        int dishCapacity = 0;
        while (dishCapacity < 1 || dishCapacity > 5) {
            System.out.print(ASK_FOR_DISH_CAPACITY);
            try {
                dishCapacity = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                dishCapacity = 0;
                scanner.next();
            }
        }
        return dishCapacity;
    }

    private String checkFlightNumber(Scanner scanner) {
        String flightNumber = "";
        while (flightNumber.length() != 3) {
            System.out.print(ASK_FOR_FLIGHT_NUMBER);
            try {
                flightNumber = scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                flightNumber = "";
                scanner.next();
            }
            flightNumber = flightNumber.trim();
            flightNumber = flightNumber.toUpperCase();
        }
        return flightNumber;
    }

    private String checkName(Scanner scanner) {
        String name = "";
        while (name.length() < 3 || name.length() > 64) {
            System.out.print(ASK_FOR_FLIGHT_NAME);
            try {
                name = scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                name = "";
                scanner.next();
            }
            name = name.trim();
        }
        return name;
    }

    public void listFlights() {
        ArrayList<Flight> flights = Item.getAllFlights();
        if (flights.isEmpty()) {
            System.out.println("No flights to display.");
        } else {

            for (Flight flight : flights) {
                System.out.println(flight);
            }
        }
    }

    @Override
    protected void deleteWithId(String id) {
        ArrayList<Flight> flights = Item.getAllFlights();
        for (Flight flight : flights) {
            if (flight.getId().equals(id)) {
                flight.delete();
                System.out.println("Deleted flight: " + flight);
                return;
            }
        }
        System.out.println("No Flight with given id: " + id + "was found.");
    }

    @Override
    protected void deleteAtIndex(int index) {
        ArrayList<Flight> flights = Item.getAllFlights();
        System.out.println(flights.get(index) + "was deleted.");
        flights.get(index).delete();
    }

    @Override
    protected void showItemIds() {
        ArrayList<Flight> flights = Item.getAllFlights();
        for (Flight flight : flights) {
            System.out.println("[Flight ID:] " + flight.getId() + " --> " + flight.getName());
        }
    }

    @Override
    protected void showItemIndexes() {
        ArrayList<Flight> flights = Item.getAllFlights();
        for (int i = 0; i < flights.size(); i++) {
            System.out.println("[Flight INDEX:] " + i + " --> " + flights.get(i).getName());
        }
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
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Cedric Jansen, Lukas Gabriel
 */
public class FlightManager extends Manager {

    private static final String ASK_FOR_FLIGHT_NAME = "Please enter a FLIGHT NAME. Must be between 3 and 64 characters: ";
    private static final String ASK_FOR_FLIGHT_NUMBER = "Please enter a FLIGHT NUMBER \n(any 3 chars e.g. LTH, can contain numbers, special characters, etc.):";
    private static final String ASK_FOR_DISH_CAPACITY = "Please enter a DISH CAPACITY (1-5): ";
    private static final String ASK_FOR_PASSENGER_CAPACITY = "Please enter a MAXIMUM PASSENGER AMOUNT(1-200): ";

    public void updateFlight() {
        Scanner scanner = new Scanner(System.in);
        Flight flightToUpdate = chooseFlight(scanner);
        updateName(flightToUpdate, scanner);
        updateFlightNumber(flightToUpdate, scanner);
        updateMaxPassengers(flightToUpdate, scanner);
        System.out.println("Flight update complete!");
    }

    public void deleteFlight() {
        super.delete(this);
    }

    public void addFlight() {
        Scanner scanner = new Scanner(System.in);
        String name = checkName(scanner);
        String flightNumber = checkFlightNumber(scanner);
        int dishCapacity = checkDishCapacity(scanner);
        int maxPassengers = checkPassengerCapacity(scanner);

        //Flight flight = new Flight(name, flightNumber, maxPassengers, dishCapacity);
    }

    public void addDishToFlight() {
        ArrayList<Dish> allAvailableDishes = Dish.getAllDishes();
        ArrayList<Flight> allFlights = Flight.getAllFlights();

        int dishInput;
        int flightInput;
        Dish dishToAdd;
        Flight flightToAddDishTo;

        if (allAvailableDishes.isEmpty()) {
            System.out.println("No dishes available.");
            return;
        }

        if (allFlights.isEmpty()) {
            System.out.println("No flights to add a dish to.");
        }

        dishInput = getDishInput(allAvailableDishes);
        dishToAdd = allAvailableDishes.get(dishInput);

        flightInput = getFlightInput(allFlights);
        flightToAddDishTo = allFlights.get(flightInput);

        if (flightToAddDishTo.getDishes().contains(dishToAdd)) {
            System.out.println("Flight already contains dish.");
        } else {
            flightToAddDishTo.add(dishToAdd);
            dishToAdd.add(flightToAddDishTo);
        }
    }

    private void updateMaxPassengers(Flight flight, Scanner scanner) {
        String confirmation = "";
        int newPassengers;
        System.out.print("Do you want to update the passenger capacity?");
        System.out.println(" Current capacity is: " + flight.getMaxPassengers());
        System.out.print("Please enter: (Y/N)");
        while (confirmation.isEmpty()) {
            
            try {
                confirmation = scanner.nextLine();
                confirmation = confirmation.trim();
                confirmation = confirmation.toUpperCase();
                if (confirmation.equals("Y")) {
                    newPassengers = checkPassengerCapacity(scanner);
                    flight.setMaxPassengers(newPassengers);
                } else {
                    if (confirmation.equals("N")) {
                        System.out.println("No update for flight number.");
                        break;
                    } else {
                        confirmation = "";
                    }
                }

            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                confirmation = "";
                scanner.next();
            }
            confirmation = confirmation.trim();
        }
    }

    private void updateFlightNumber(Flight flight, Scanner scanner) {
        String confirmation = "";
        String newNumber;
        System.out.print("Do you want to update the flight's number?");
        System.out.println(" Current flight number is: " + flight.getFlightNumber());
        System.out.print("Please enter: (Y/N)");
        while (confirmation.isEmpty()) {
            
            try {
                confirmation = scanner.nextLine();
                confirmation = confirmation.trim();
                confirmation = confirmation.toUpperCase();
                if (confirmation.equals("Y")) {
                    newNumber = checkFlightNumber(scanner);
                    flight.setFlightNumber(newNumber);
                } else {
                    if (confirmation.equals("N")) {
                        System.out.println("No update for flight number.");
                        break;
                    }
                    else {
                        confirmation = "";
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                confirmation = "";
                scanner.next();
            }
            confirmation = confirmation.trim();
        }
    }

    private void updateName(Flight flight, Scanner scanner) {
        String confirmation = "";
        String name = "";
        System.out.print("Do you want to update the flight's name?");
        System.out.println(" Current name is: " + flight.getName());
        System.out.print("Please enter: (Y/N)");
        while (confirmation.isEmpty()) {
            try {
                
                confirmation = scanner.nextLine();
                confirmation = confirmation.trim();
                confirmation = confirmation.toUpperCase();
                if (confirmation.equals("Y")) {
                    name = checkName(scanner);
                    flight.setName(name);
                } else {
                    if (confirmation.equals("N")) {
                        System.out.println("No update for name.");
                        break;
                    }
                    else {
                        confirmation = "";
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                confirmation = "";
                scanner.next();
            }
            confirmation = confirmation.trim();
        }
    }

    private Flight chooseFlight(Scanner scanner) {
        ArrayList<Flight> flights = Flight.getAllFlights();
        int input = -1;
        System.out.println("Please choose a flight to update by entering it's index:");
        for (int i = 0; i < flights.size(); i++) {
            Flight flight = flights.get(i);
            System.out.println(i + " : " + flight.getName() + " [" + flight.getId() + "]");
        }
        while (input < 0 || input > flights.size() - 1) {
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("An error occured. Please try again.");
                input = -1;
                scanner.next();
            }
        }
        return flights.get(input);

    }

    private int getFlightInput(ArrayList<Flight> allFlights) {
        System.out.println("Please enter the index of the flight to which you want to add the dish to");
        System.out.println("All flights available:");
        for (int i = 0; i < allFlights.size(); i++) {
            String output = "";
            output += allFlights.get(i).name;
            output += " : " + i;
            System.out.println(output);
        }
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        while (input < 0 || input > allFlights.size() - 1) {
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                input = -1;
                scanner.next();
            }
        }

        return input;
    }

    private int getDishInput(ArrayList<Dish> dishes) {
        System.out.println("Please enter the index of the dish to add.");
        System.out.println("All dishes available:");
        for (int i = 0; i < dishes.size(); i++) {
            String output = "";
            output += dishes.get(i).name;
            output += " : " + i;
            System.out.println(output);
        }
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        while (input < 0 || input > dishes.size() - 1) {
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                input = -1;
                scanner.next();
            }
        }
        return input;
    }

    private int checkPassengerCapacity(Scanner scanner) {
        int passengerNumber = 0;
        while (passengerNumber < 1 || passengerNumber > 200) {
            System.out.print(ASK_FOR_PASSENGER_CAPACITY);
            try {
                passengerNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                passengerNumber = 0;
                scanner.next();
            }

        }
        return passengerNumber;
    }

    private int checkDishCapacity(Scanner scanner) {
        int dishCapacity = 0;
        while (dishCapacity < 1 || dishCapacity > 5) {
            System.out.print(ASK_FOR_DISH_CAPACITY);
            try {
                dishCapacity = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                dishCapacity = 0;
                scanner.next();
            }
        }
        return dishCapacity;
    }

    private String checkFlightNumber(Scanner scanner) {
        String flightNumber = "";
        while (flightNumber.length() != 3) {
            System.out.print(ASK_FOR_FLIGHT_NUMBER);
            try {
                flightNumber = scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                flightNumber = "";
                scanner.next();
            }
            flightNumber = flightNumber.trim();
            flightNumber = flightNumber.toUpperCase();
        }
        return flightNumber;
    }

    private String checkName(Scanner scanner) {
        String name = "";
        while (name.length() < 3 || name.length() > 64) {
            System.out.print(ASK_FOR_FLIGHT_NAME);
            try {
                name = scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                name = "";
                scanner.next();
            }
            name = name.trim();
        }
        return name;
    }

    public void listFlights() {
        ArrayList<Flight> flights = Item.getAllFlights();
        if (flights.isEmpty()) {
            System.out.println("No flights to display.");
        } else {

            for (Flight flight : flights) {
                System.out.println(flight);
            }
        }
    }

    @Override
    protected void deleteWithId(String id) {
        ArrayList<Flight> flights = Item.getAllFlights();
        for (Flight flight : flights) {
            if (flight.getId().equals(id)) {
                flight.delete();
                System.out.println("Deleted flight: " + flight);
                return;
            }
        }
        System.out.println("No Flight with given id: " + id + "was found.");
    }

    @Override
    protected void deleteAtIndex(int index) {
        ArrayList<Flight> flights = Item.getAllFlights();
        System.out.println(flights.get(index) + "was deleted.");
        flights.get(index).delete();
    }

    @Override
    protected void showItemIds() {
        ArrayList<Flight> flights = Item.getAllFlights();
        for (Flight flight : flights) {
            System.out.println("[Flight ID:] " + flight.getId() + " --> " + flight.getName());
        }
    }

    @Override
    protected void showItemIndexes() {
        ArrayList<Flight> flights = Item.getAllFlights();
        for (int i = 0; i < flights.size(); i++) {
            System.out.println("[Flight INDEX:] " + i + " --> " + flights.get(i).getName());
        }
    }

}
>>>>>>> 2122bab... Add project files
