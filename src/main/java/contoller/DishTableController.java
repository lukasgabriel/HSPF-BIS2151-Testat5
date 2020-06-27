/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contoller;

import items.Dish;
import items.Flight;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import main.Main;

/**
 *
 * @author Cedric Jansen
 */
public class DishTableController extends TableController {

    private final JLabel dishTitle;
    private final JLabel dishIDLabel;
    private final JLabel dishVeganLabel;
    private final JLabel dishVegetarianLabel;
    private final JLabel dishPriceLabel;
    private final JLabel dishOvervieFlightContent;

    private Dish currentSelectedDish;

    public DishTableController(Main main, JTable table) {
        super(main, table);
        System.out.println("[DISH CONTROLLER] Initliasing dish controller...");
        dishTitle = main.getDishOverviewTitle();
        dishIDLabel = main.getDishOverviewIDContent();
        dishVeganLabel = main.getDishOverviewVeganContent();
        dishVegetarianLabel = main.getDishOverviewVegetariannContent();
        dishPriceLabel = main.getDishPriceContent();
        dishOvervieFlightContent = main.getDishOverviewFlightContent();

    }

    @Override
    public void populate() {
        System.out.println("[DISH CONTROLLER] Pupulating dish data...");
        DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();
        ArrayList<Dish> dishesFromDb = dataBase.getDishData();

        dishesFromDb.forEach((dish) -> {
            defaultTable.addRow(new Object[]{dish.getName(), dish.getId(), dish.isVegan(), dish.isVegetarian(), dish.getPrice()});
        });

    }

    // Refreshes the table row when a dish is updated
    public void refreshDishRow(Dish dish) {
        System.out.println("[DISH CONTROLLER] Refreshing dish data...");
        DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();
        int selectedRow = selectionPointer;
        String name = dish.getName();
        boolean vegan = dish.isVegan();
        boolean vegetarian = dish.isVegetarian();
        if (vegan) {
            vegetarian = true;
        }
        float price = dish.getPrice();

        defaultTable.setValueAt(name, selectedRow, 0);
        defaultTable.setValueAt(vegan, selectedRow, 2);
        defaultTable.setValueAt(vegetarian, selectedRow, 3);
        defaultTable.setValueAt(price, selectedRow, 4);
        // Update the right overview panel
        //updateOverview(selectedRow);
        // Redraw the table
        table.invalidate();
    }

    // Called to set the event behaviour of the controller.
    public void listen() {
        System.out.println("[DISH CONTROLLER] Initialising listeners...");
        //Anonymous subclass used for event handling on selection events
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            // when the selection inside the flight table changes
            setSelectionPointer(table.getSelectedRow());
            updateOverview();
        });

        table.getModel().addTableModelListener((TableModelEvent evt) -> {
            // Updates the flight references when the table is directly edited
            updateDishList(evt);
        });
    }

    // Add a dish to the table
    public void addDish(Dish d) {
        System.out.println("[DISH CONTROLLER] Adding new dish...");
        DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();

        //Build an object array with representative strings of the object
        Object[] dataLine = new Object[5];
        //Get the values
        String name = d.getName();
        String id = d.getId();
        boolean vegan = d.isVegan();
        boolean vegetarian = d.isVegetarian();
        if (vegan) {
            vegetarian = true;
        }
        float price = d.getPrice();

        // Pass the values into the array. Could be done when initializing the array
        // but this approach is chosen to improve readability.
        dataLine[0] = name;
        dataLine[1] = id;
        dataLine[2] = vegan;
        dataLine[3] = vegetarian;
        dataLine[4] = price;
        //Add the dataLine with the data to the table model.
        defaultTable.addRow(dataLine);
    }

    // Delete the selected flight
    public void deleteSelected() {
        System.out.println("[DISH CONTROLLER] Deleting selected dish...");
        DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();
        String id = currentSelectedDish.getId();

        for (int i = 0; i < defaultTable.getRowCount(); i++) {
            String rowId = (String) defaultTable.getValueAt(i, 1);
            if (rowId.equals(id)) {
                //if id stored in the row is the flight id, remove it from the table
                selectionPointer = -1;
                currentSelectedDish = null;
                defaultTable.removeRow(i);

                clearOverview();
                break;
            }
        }
    }

    //Updates the flight reference when the flight table is directly edited.
    private void updateDishList(TableModelEvent event) {
        System.out.println("[DISH CONTROLLER] Updating dish data...");
        if (event.getType() == TableModelEvent.UPDATE) {
            int row = event.getFirstRow();
            int lastRow = event.getLastRow();
            for (int i = row; i < lastRow + 1; i++) {
                // fetch updated values
                String id = (String) table.getValueAt(i, 1);
                String name = (String) table.getValueAt(i, 0);
                boolean vegan = (boolean) table.getValueAt(i, 2);
                boolean vegetarian = (boolean) table.getValueAt(i, 3);
                if (vegan) {
                    vegetarian = true;
                }
                float price = (float) table.getValueAt(i, 4);
                Dish dishToUpdate = Dish.getDishById(id);
                // update the flight reference
                dishToUpdate.update(name, vegan, vegetarian, price);
                updateOverview(i);
            }
        }
    }

    private void clearOverview() {
        dishIDLabel.setText("");
        dishVeganLabel.setText("");
        dishVegetarianLabel.setText("");
        dishPriceLabel.setText("");
        dishOvervieFlightContent.setText("");
    }

    // Updates the overview when the selection of the table is changes
    public void updateOverview(Dish dish) {
        int selectedRow = selectionPointer;
        DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();
        String dishes;
        String name = dish.getName();
        boolean vegan = dish.isVegan();
        boolean vegetarian = dish.isVegetarian();
        if (vegan) {
            vegetarian = true;
        }
        float price = dish.getPrice();

        defaultTable.setValueAt(name, selectedRow, 0);
        defaultTable.setValueAt(vegan, selectedRow, 2);
        defaultTable.setValueAt(vegetarian, selectedRow, 3);
        defaultTable.setValueAt(price, selectedRow, 4);

        currentSelectedDish = dish;
        dishes = buildFlightString();
        // Remove the annotation, that a flight needs to be selected
        main.getFlightOverviewActionsFeedbackLabel().setText("");

        dishIDLabel.setText(dish.getId());
        dishVeganLabel.setText(String.valueOf(dish.isVegan()));
        dishVegetarianLabel.setText(String.valueOf(dish.isVegetarian()));
        dishPriceLabel.setText(Float.toString(dish.getPrice()));
        // Add the flight string
        dishOvervieFlightContent.setText(dishes);
    }

    // Updates the overview and reloads the table row with the selected row
    // to display changes in between changes of the flight object.
    public void updateOverview() {
        if (selectionPointer == -1) {
            return;
        }
        int selectedRow = selectionPointer;
        DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();

        String id = tryToFetchAttribute(1, selectedRow);
        System.out.println(id);
        Dish dish = Dish.getDishById(id);
        String name = dish.getName();
        boolean vegan = dish.isVegan();
        boolean vegetarian = dish.isVegetarian();
        if (vegan) {
            vegetarian = true;
        }
        float price = dish.getPrice();
        currentSelectedDish = dish;
        String flights;

        defaultTable.setValueAt(name, selectedRow, 0);
        defaultTable.setValueAt(vegan, selectedRow, 2);

        defaultTable.setValueAt(vegetarian, selectedRow, 3);
        defaultTable.setValueAt(price, selectedRow, 4);

        flights = buildFlightString();

        // Remove the annotation, that a flight needs to be selected
        main.getFlightOverviewActionsFeedbackLabel().setText("");

        dishIDLabel.setText(dish.getId());
        dishVeganLabel.setText(String.valueOf(dish.isVegan()));
        dishVegetarianLabel.setText(String.valueOf(dish.isVegetarian()));
        dishPriceLabel.setText(Float.toString(dish.getPrice()));
        // Add the flight string
        dishOvervieFlightContent.setText(flights);
    }

    // Update the overview with the values from a given row
    private void updateOverview(int row) {
        String id = tryToFetchAttribute(1, row);
        String flights = buildFlightString();
        boolean vegan = Boolean.valueOf(tryToFetchAttribute(2, row));
        boolean vegetarian = Boolean.valueOf(tryToFetchAttribute(3, row));
        if (vegan) {
            vegetarian = true;
        }
        float price = Float.valueOf(tryToFetchAttribute(4, row));

        dishIDLabel.setText(id);
        dishVeganLabel.setText(String.valueOf(vegan));
        dishVegetarianLabel.setText(String.valueOf(vegetarian));
        dishPriceLabel.setText(Float.toString(price));
        // Add the flight string
        dishOvervieFlightContent.setText(flights);
    }

    // Builds the string of dishes stored within a flight
    private String buildFlightString() {
        String result = "";
        int iterator = 1;
        ArrayList<Flight> flights = currentSelectedDish.getFlights();
        if (flights.isEmpty()) {
            return "No flights assigned to this dish...";
        }
        for (Flight f : flights) {
            result += f.getName() + " : " + f.getId() + "";
            if (flights.size() > 1) {
                result += ",  ";
                if (iterator % 2 == 0) {
                    // Add line break
                }
            }
            ++iterator;
        }
        return result;
    }

    // Tries to fetch the attributes from the selected column and converts
    // it into a string.
    private String tryToFetchAttribute(int index, int row) {
        if (row > table.getRowCount()) {
            return "";
        }
        String result;
        try {
            result = table.getValueAt(row, index).toString();
           
        } catch (NullPointerException ex) {
            result = "";
        }
        return result;
    }

    // Fetches the flight object behind the row data
    private Flight fetchSelectedFlight(int row) {
        String id = tryToFetchAttribute(1, row);
        ArrayList<Flight> flights = Flight.getAllFlights();
        // Search for the flight by comparing it's id to all stored flights.
        for (Flight flight : flights) {
            if (flight.getId().equals(id)) {
                return flight;
            }
        }
        return null;
    }

    public Dish getSelectedDish() {
        return currentSelectedDish;
    }

    public TableModel getDefaultTable() {
        return this.table.getModel();
    }

}
