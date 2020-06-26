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
public class FlightTableController extends TableController {

    private final JLabel flightTitle;
    private final JLabel flightIDLabel;
    private final JLabel flightStartAirportLabel;
    private final JLabel flightdestAirportLabel;
    private final JLabel flightOverviewDishContent;

    private Flight currentSelectedFlight;

    public FlightTableController(Main main, JTable table) {
        super(main, table);
        System.out.println("[FLIGHT CONTROLLER] Initliasing flight controller...");
        flightTitle = main.getFlightOverviewTitle();
        flightIDLabel = main.getFlightOverviewIDContent();
        flightStartAirportLabel = main.getFlightOverviewStartContent();
        flightdestAirportLabel = main.getFlightOverviewDestContent();
        flightOverviewDishContent = main.getFlightOverviewDishContent();

    }

    @Override
    public void populate() {
        System.out.println("[FLIGHT CONTROLLER] Populating data tables...");
        DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();
        ArrayList<Flight> flightsFromDb = dataBase.getFlightData();

        flightsFromDb.forEach((flight) -> {
            defaultTable.addRow(new Object[]{flight.getName(), flight.getId(), flight.getStartPoint(), flight.getDestinationAirport()});
        });
    }

    // Refreshes the table row when an flight is updated
    public void refreshFlightRow(Flight flight) {
        System.out.println("[FLIGHT CONTROLLER] Refreshing data tables...");
        DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();
        int selectedRow = selectionPointer;
        String name = flight.getName();
        String start = flight.getStartAirport();
        String dest = flight.getDestinationAirport();

        defaultTable.setValueAt(name, selectedRow, 0);
        defaultTable.setValueAt(start, selectedRow, 2);
        defaultTable.setValueAt(dest, selectedRow, 3);
        // Update the right overview panel
        //updateOverview(selectedRow);
        // Redraw the table
        table.invalidate();
    }

    // Called to set the event behaviour of the controller.
    public void listen() {
        System.out.println("[FLIGHT CONTROLLER] Initlialisng listeners...");
        //Anonymous subclass used for event handling on selection events
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            // when the selection inside the flight table changes
            setSelectionPointer(table.getSelectedRow());
            updateOverview();
        });

        table.getModel().addTableModelListener((TableModelEvent evt) -> {
            // Updates the flight references when the table is directly edited
            updateFlightList(evt);

        });
    }

    // Add a flight to the table
    public void addFlight(Flight f) {
        System.out.println("[FLIGHT CONTROLLER] Adding new flight...");
        DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();

        //Build an object array with representative strings of the object
        Object[] dataLine = new Object[4];
        //Get the values
        String name = f.getName();
        String id = f.getId();
        String start = f.getStartAirport();
        String dest = f.getDestinationAirport();

        // Pass the values into the array. Could be done when initializing the array
        // but this approach is chosen to improve readability.
        dataLine[0] = name;
        dataLine[1] = id;
        dataLine[2] = start;
        dataLine[3] = dest;
        //Add the dataLine with the data to the table model.
        defaultTable.addRow(dataLine);
    }

    // Delete the selected flight
    public void deleteSelected() {
        System.out.println("[FLIGHT CONTROLLER] Deleting selected flight...");
        DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();
        String id = currentSelectedFlight.getId();

        for (int i = 0; i < defaultTable.getRowCount(); i++) {
            String rowId = (String) defaultTable.getValueAt(i, 1);
            if (rowId.equals(id)) {
                //if id stored in the row is the flight id, remove it from the table
                selectionPointer = -1;
                currentSelectedFlight = null;
                defaultTable.removeRow(i);

                clearOverview();
                break;
            }
        }
    }

    //Updates the flight reference when the flight table is directly edited.
    private void updateFlightList(TableModelEvent event) {
        System.out.println("[FLIGHT CONTROLLER] Updating flight..");
        if (event.getType() == TableModelEvent.UPDATE) {
            int row = event.getFirstRow();
            int lastRow = event.getLastRow();
            for (int i = row; i < lastRow + 1; i++) {
                // fetch updated values
                String id = (String) table.getValueAt(i, 1);
                String name = (String) table.getValueAt(i, 0);
                String startAirport = (String) table.getValueAt(i, 2);
                String destAirport = (String) table.getValueAt(i, 3);
                Flight flightToUpdate = Flight.flightById(id);
                // update the flight reference
                flightToUpdate.update(name, startAirport, destAirport);
                updateOverview(i);
            }
        }
    }

    private void clearOverview() {
        flightIDLabel.setText("");
        flightStartAirportLabel.setText("");
        flightdestAirportLabel.setText("");
        flightOverviewDishContent.setText("");
    }

    // Updates the overview when the selection of the table is changes
    public void updateOverview(Flight f) {
        int selectedRow = selectionPointer;
        DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();

        String name = f.getName();
        String start = f.getStartAirport();
        String dest = f.getDestinationAirport();

        defaultTable.setValueAt(name, selectedRow, 0);
        defaultTable.setValueAt(start, selectedRow, 2);
        defaultTable.setValueAt(dest, selectedRow, 3);
        String dishes;

        currentSelectedFlight = f;
        dishes = buildDishString();
        // Remove the annotation, that a flight needs to be selected
        main.getFlightOverviewActionsFeedbackLabel().setText("");

        flightIDLabel.setText(f.getId());
        flightStartAirportLabel.setText(f.getStartAirport());
        flightdestAirportLabel.setText(f.getDestinationAirport());
        // Add the dish string
        flightOverviewDishContent.setText(dishes);

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
        Flight f = Flight.flightById(id);
        String dishes;

        defaultTable.setValueAt(f.getName(), selectedRow, 0);
        defaultTable.setValueAt(f.getStartAirport(), selectedRow, 2);
        defaultTable.setValueAt(f.getDestinationAirport(), selectedRow, 3);

        currentSelectedFlight = f;
        dishes = buildDishString();

        // Remove the annotation, that a flight needs to be selected
        main.getFlightOverviewActionsFeedbackLabel().setText("");

        flightIDLabel.setText(f.getId());
        flightStartAirportLabel.setText(f.getStartAirport());
        flightdestAirportLabel.setText(f.getDestinationAirport());

        // Add the dish string
        flightOverviewDishContent.setText(dishes);
    }

    // Update the overview with the values from a given row
    private void updateOverview(int row) {
        String id = tryToFetchAttribute(1, row);
        String startAirport = tryToFetchAttribute(2, row);
        String destinationAirport = tryToFetchAttribute(3, row);
        flightIDLabel.setText(id);
        flightStartAirportLabel.setText(startAirport);
        flightdestAirportLabel.setText(destinationAirport);
    }

    // Builds the string of dishes stored within a flight
    private String buildDishString() {
        String result = "";
        int iterator = 1;
        ArrayList<Dish> dishes = currentSelectedFlight.getDishes();
        if (dishes.isEmpty()) {
            return "No dishes assigned to this flight...";
        }
        for (Dish d : dishes) {
            result += d.getName() + " : " + d.getId() + "";
            if (dishes.size() > 1) {
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

    public Flight getSelectedFlight() {
        return currentSelectedFlight;
    }

    public TableModel getDefaultTable() {
        return this.table.getModel();
    }
}
