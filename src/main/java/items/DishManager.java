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
public class DishManager extends Manager {

    public void addDish() {
        Scanner scanner = new Scanner(System.in);
        String name = checkName(scanner);
        float price = determinePrice(scanner);
        boolean[] properties = determineProperties(scanner); // consists out of 2 elements

        Dish dish = new Dish(name, properties[0], properties[1], price);
    }

    public void listDishes() {
        ArrayList<Dish> dishList = Item.getAllDishes();
        if (dishList.isEmpty()) {
            System.out.println("No dishes to display.");
        } else {
            for (Dish dish : dishList) {
                System.out.println(dish);
            }
        }
    }

    public void updateDish() {
        Scanner scanner = new Scanner(System.in);
        Dish dishToUpdate = chooseDish(scanner);
        updateName(dishToUpdate, scanner);
        updateProperties(dishToUpdate, scanner);
        updatePrice(dishToUpdate, scanner);
        System.out.println("Dish update complete!");
    }

    public void deleteDish() {
        super.delete(this);
    }

    private void updateProperties(Dish dish, Scanner scanner) {
        String confirmation = "";
        boolean[] newProperties = new boolean[2];
        System.out.println("Do you want to update the current dish properties?");
        System.out.println(" Is vegan: " + dish.isVegan());
        System.out.println(" Is vegetarian: " + dish.isVegetarian());
        System.out.print("Please enter(Y/N): ");
        while (confirmation.isEmpty()) {
            
            try {
                confirmation = scanner.nextLine();
                confirmation = confirmation.trim();
                confirmation = confirmation.toUpperCase();
                if (confirmation.equals("Y")) {
                    newProperties = determineProperties(scanner);
                    dish.isVegan(newProperties[0]);
                    dish.isVegetarian(newProperties[1]);
                } else {
                    if (confirmation.equals("N")) {
                        System.out.println("No update for dish properties.");
                        break;
                    } 
                    else {
                        confirmation = ""; // to get back to the top of the while loop
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

    private void updatePrice(Dish dish, Scanner scanner) {
        String confirmation = "";
        float newPrice;
        System.out.print("Do you want to update the dish's price?");
        System.out.println(" Current dish price is: " + dish.getPrice());
        System.out.print("Please enter: (Y/N)");
        while (confirmation.isEmpty()) {
            
            try {
                confirmation = scanner.nextLine();
                confirmation = confirmation.trim();
                confirmation = confirmation.toUpperCase();
                if (confirmation.equals("Y")) {
                    newPrice = determinePrice(scanner);
                    dish.setPrice(newPrice);
                } else {
                    if (confirmation.equals("N")) {
                        System.out.println("No update for dish price.");
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

    private void updateName(Dish dish, Scanner scanner) {
        String confirmation = "";
        String name = "";
        System.out.print("Do you want to update the dish's name?");
        System.out.println(" Current name is: " + dish.getName());
        System.out.print("Please enter: (Y/N)");
        while (confirmation.isEmpty()) {
            
            try {
                confirmation = scanner.nextLine();
                confirmation = confirmation.trim();
                confirmation = confirmation.toUpperCase();
                if (confirmation.equals("Y")) {
                    name = checkName(scanner);
                    dish.setName(name);
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

    private Dish chooseDish(Scanner scanner) {
        ArrayList<Dish> dishes = Dish.getAllDishes();
        int input = -1;
        System.out.println("Please choose a dish to update by entering it's index:");
        for (int i = 0; i < dishes.size(); i++) {
            Dish dish = dishes.get(i);
            System.out.println(i + " : " + dish.getName() + " [" + dish.getId() + "]");
        }
        while (input < 0 || input > dishes.size() - 1) {
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("An error occured. Please try again.");
                input = -1;
                scanner.next();
            }
        }
        return dishes.get(input);

    }

    private boolean[] determineProperties(Scanner scanner) {
        String userInput = "";
        boolean[] results = new boolean[2];  // boolean array of two storing information properties
        while (userInput.isEmpty()) {        // while input is empty
            System.out.print("Is the dish vegan?(Y/N): ");
            try {
                userInput = scanner.nextLine();  // try to fetch next line
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED); // if next line cannot be parsed into int
                userInput = "";                    //reset and continue again
                scanner.next();
                continue;
            }
            userInput = userInput.trim();       // check for whitespaces before and after input
            userInput = userInput.toUpperCase(); // parse string to uppercase for checking    

            if (userInput.equals("Y")) {        // if dish is vegan, it must be vegetarian as well
                results[0] = true;
                results[1] = true;
                break;
            } else {
                if (userInput.equals("N")) {  // food is not vegan
                    results[0] = false;
                    userInput = "";
                    while (userInput.isEmpty()) {
                        System.out.println("Is the dish vegetarian?(Y/N): ");
                        try {
                            userInput = scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println(ERROR_OCCURED);
                            userInput = "";
                            scanner.next();
                            continue;
                        }
                        userInput = userInput.trim();
                        userInput = userInput.toUpperCase();
                        if (userInput.equals("Y")) {  // food is vegetarian
                            results[1] = true;
                        } else if (userInput.equals("N")) {  // food contains meat, neither vegan or vegetarian
                            results[1] = false;
                        } else { // Input was neither Y or N
                            System.out.println("Please enter either Y or N.");
                            userInput = "";
                            continue;
                        }
                    }
                } else {  // Input was neither Y or N
                    System.out.println("Please enter either Y or N.");
                    userInput = "";
                    continue;
                }
            }
        }

        return results;
    }

    private float determinePrice(Scanner scanner) {
        float priceHolder = -1.0f;
        while (priceHolder < 0f || priceHolder > 100f /* max price */) {
            System.out.print("Please enter a value for the dish price: ");
            try {
                priceHolder = scanner.nextFloat();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                priceHolder = -1.0f;
                scanner.next();
            }
        }
        return priceHolder;
    }

    private String checkName(Scanner scanner) {
        String name = "";
        while (name.length() < 3 || name.length() > 64) {
            System.out.print("Please enter a Dish name: ");
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

    @Override
    protected void deleteWithId(String id) {
        ArrayList<Dish> dishes = Item.getAllDishes();
        for (Dish dish : dishes) {
            if (dish.getId().equals(id)) {
                dish.delete();
                System.out.println("Deleted dish: " + dish);
                return;
            }
        }
        System.out.println("No Dish with given id: " + id + "was found.");
    }

    @Override
    protected void deleteAtIndex(int index) {
        ArrayList<Dish> dish = Item.getAllDishes();
        System.out.println(dish.get(index) + "was deleted.");
        dish.get(index).delete();
    }

    @Override
    protected void showItemIds() {
        ArrayList<Dish> dishes = Item.getAllDishes();
        for (Dish dish : dishes) {
            System.out.println("[Dish ID:] " + dish.getId() + " --> " + dish.getName());
        }
    }

    @Override
    protected void showItemIndexes() {
        ArrayList<Dish> dishes = Item.getAllDishes();
        for (int i = 0; i < dishes.size(); i++) {
            System.out.println("[Dish INDEX:] " + i + " --> " + dishes.get(i).getName());
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
public class DishManager extends Manager {

    public void addDish() {
        Scanner scanner = new Scanner(System.in);
        String name = checkName(scanner);
        float price = determinePrice(scanner);
        boolean[] properties = determineProperties(scanner); // consists out of 2 elements

        Dish dish = new Dish(name, properties[0], properties[1], price);
    }

    public void listDishes() {
        ArrayList<Dish> dishList = Item.getAllDishes();
        if (dishList.isEmpty()) {
            System.out.println("No dishes to display.");
        } else {
            for (Dish dish : dishList) {
                System.out.println(dish);
            }
        }
    }

    public void updateDish() {
        Scanner scanner = new Scanner(System.in);
        Dish dishToUpdate = chooseDish(scanner);
        updateName(dishToUpdate, scanner);
        updateProperties(dishToUpdate, scanner);
        updatePrice(dishToUpdate, scanner);
        System.out.println("Dish update complete!");
    }

    public void deleteDish() {
        super.delete(this);
    }

    private void updateProperties(Dish dish, Scanner scanner) {
        String confirmation = "";
        boolean[] newProperties = new boolean[2];
        System.out.println("Do you want to update the current dish properties?");
        System.out.println(" Is vegan: " + dish.isVegan());
        System.out.println(" Is vegetarian: " + dish.isVegetarian());
        System.out.print("Please enter(Y/N): ");
        while (confirmation.isEmpty()) {
            
            try {
                confirmation = scanner.nextLine();
                confirmation = confirmation.trim();
                confirmation = confirmation.toUpperCase();
                if (confirmation.equals("Y")) {
                    newProperties = determineProperties(scanner);
                    dish.isVegan(newProperties[0]);
                    dish.isVegetarian(newProperties[1]);
                } else {
                    if (confirmation.equals("N")) {
                        System.out.println("No update for dish properties.");
                        break;
                    } 
                    else {
                        confirmation = ""; // to get back to the top of the while loop
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

    private void updatePrice(Dish dish, Scanner scanner) {
        String confirmation = "";
        float newPrice;
        System.out.print("Do you want to update the dish's price?");
        System.out.println(" Current dish price is: " + dish.getPrice());
        System.out.print("Please enter: (Y/N)");
        while (confirmation.isEmpty()) {
            
            try {
                confirmation = scanner.nextLine();
                confirmation = confirmation.trim();
                confirmation = confirmation.toUpperCase();
                if (confirmation.equals("Y")) {
                    newPrice = determinePrice(scanner);
                    dish.setPrice(newPrice);
                } else {
                    if (confirmation.equals("N")) {
                        System.out.println("No update for dish price.");
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

    private void updateName(Dish dish, Scanner scanner) {
        String confirmation = "";
        String name = "";
        System.out.print("Do you want to update the dish's name?");
        System.out.println(" Current name is: " + dish.getName());
        System.out.print("Please enter: (Y/N)");
        while (confirmation.isEmpty()) {
            
            try {
                confirmation = scanner.nextLine();
                confirmation = confirmation.trim();
                confirmation = confirmation.toUpperCase();
                if (confirmation.equals("Y")) {
                    name = checkName(scanner);
                    dish.setName(name);
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

    private Dish chooseDish(Scanner scanner) {
        ArrayList<Dish> dishes = Dish.getAllDishes();
        int input = -1;
        System.out.println("Please choose a dish to update by entering it's index:");
        for (int i = 0; i < dishes.size(); i++) {
            Dish dish = dishes.get(i);
            System.out.println(i + " : " + dish.getName() + " [" + dish.getId() + "]");
        }
        while (input < 0 || input > dishes.size() - 1) {
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("An error occured. Please try again.");
                input = -1;
                scanner.next();
            }
        }
        return dishes.get(input);

    }

    private boolean[] determineProperties(Scanner scanner) {
        String userInput = "";
        boolean[] results = new boolean[2];  // boolean array of two storing information properties
        while (userInput.isEmpty()) {        // while input is empty
            System.out.print("Is the dish vegan?(Y/N): ");
            try {
                userInput = scanner.nextLine();  // try to fetch next line
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED); // if next line cannot be parsed into int
                userInput = "";                    //reset and continue again
                scanner.next();
                continue;
            }
            userInput = userInput.trim();       // check for whitespaces before and after input
            userInput = userInput.toUpperCase(); // parse string to uppercase for checking    

            if (userInput.equals("Y")) {        // if dish is vegan, it must be vegetarian as well
                results[0] = true;
                results[1] = true;
                break;
            } else {
                if (userInput.equals("N")) {  // food is not vegan
                    results[0] = false;
                    userInput = "";
                    while (userInput.isEmpty()) {
                        System.out.println("Is the dish vegetarian?(Y/N): ");
                        try {
                            userInput = scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println(ERROR_OCCURED);
                            userInput = "";
                            scanner.next();
                            continue;
                        }
                        userInput = userInput.trim();
                        userInput = userInput.toUpperCase();
                        if (userInput.equals("Y")) {  // food is vegetarian
                            results[1] = true;
                        } else if (userInput.equals("N")) {  // food contains meat, neither vegan or vegetarian
                            results[1] = false;
                        } else { // Input was neither Y or N
                            System.out.println("Please enter either Y or N.");
                            userInput = "";
                            continue;
                        }
                    }
                } else {  // Input was neither Y or N
                    System.out.println("Please enter either Y or N.");
                    userInput = "";
                    continue;
                }
            }
        }

        return results;
    }

    private float determinePrice(Scanner scanner) {
        float priceHolder = -1.0f;
        while (priceHolder < 0f || priceHolder > 100f /* max price */) {
            System.out.print("Please enter a value for the dish price: ");
            try {
                priceHolder = scanner.nextFloat();
            } catch (InputMismatchException e) {
                System.out.println(ERROR_OCCURED);
                priceHolder = -1.0f;
                scanner.next();
            }
        }
        return priceHolder;
    }

    private String checkName(Scanner scanner) {
        String name = "";
        while (name.length() < 3 || name.length() > 64) {
            System.out.print("Please enter a Dish name: ");
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

    @Override
    protected void deleteWithId(String id) {
        ArrayList<Dish> dishes = Item.getAllDishes();
        for (Dish dish : dishes) {
            if (dish.getId().equals(id)) {
                dish.delete();
                System.out.println("Deleted dish: " + dish);
                return;
            }
        }
        System.out.println("No Dish with given id: " + id + "was found.");
    }

    @Override
    protected void deleteAtIndex(int index) {
        ArrayList<Dish> dish = Item.getAllDishes();
        System.out.println(dish.get(index) + "was deleted.");
        dish.get(index).delete();
    }

    @Override
    protected void showItemIds() {
        ArrayList<Dish> dishes = Item.getAllDishes();
        for (Dish dish : dishes) {
            System.out.println("[Dish ID:] " + dish.getId() + " --> " + dish.getName());
        }
    }

    @Override
    protected void showItemIndexes() {
        ArrayList<Dish> dishes = Item.getAllDishes();
        for (int i = 0; i < dishes.size(); i++) {
            System.out.println("[Dish INDEX:] " + i + " --> " + dishes.get(i).getName());
        }
    }
}
>>>>>>> 2122bab... Add project files
