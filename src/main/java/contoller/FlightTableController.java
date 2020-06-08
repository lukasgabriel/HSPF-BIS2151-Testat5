<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contoller;

import items.Flight;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import main.Main;

/**
 *
 * @author Cedric Jansen
 */
public class FlightTableController extends TableController{

    private JLabel flightTitle;
    private JLabel flightIDLabel;
    private JLabel flightStartAirportLabel;
    private JLabel flightdestAirportLabel;
     
    
    public FlightTableController(Main main, JTable table) {
        super(main, table);
        
        flightTitle = main.getFlightOverviewTitle();
        flightIDLabel = main.getFlightOverviewIDContent();
        flightStartAirportLabel = main.getFlightOverviewStartContent();
        flightdestAirportLabel = main.getFlightOverviewDestContent();
        
    }
    
    @Override
    public void populate() {
       DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();
       ArrayList<Flight> flightsFromDb = dataBase.getDeserializedFlights();
       
       for( Flight flight : flightsFromDb) {
           defaultTable.addRow(new Object[] { flight.getName(), flight.getId(), flight.getStartPoint(), flight.getDestinationAirport() });
       }
    }
    
    
    public void listen() {
        //Anonymous subclass used for event handling
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            // when the selection inside the flight table changes
            updateOverview();
          
        });
    }
    
    
    private void updateOverview() {
            int selectedRow = table.getSelectedRow();
            String id = tryToFetchAttribute(1, selectedRow);
            String startAirport = tryToFetchAttribute(2, selectedRow);
            String destinationAirport = tryToFetchAttribute(3, selectedRow);
            
            flightIDLabel.setText(id);
            flightStartAirportLabel.setText(startAirport);
            flightdestAirportLabel.setText(destinationAirport);
    }
    
    private String tryToFetchAttribute(int index, int row) {
        if( index > table.getColumnCount() ) {
            throw new ArrayIndexOutOfBoundsException();
        }
        String result;
        try {
            result = table.getValueAt(row, index).toString();
        } 
        catch(NullPointerException ex) {
            result = "";
        }
        
        return result;
    }
    
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contoller;

import items.Flight;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import main.Main;

/**
 *
 * @author Cedric Jansen
 */
public class FlightTableController extends TableController{

    private JLabel flightTitle;
    private JLabel flightIDLabel;
    private JLabel flightStartAirportLabel;
    private JLabel flightdestAirportLabel;
     
    
    public FlightTableController(Main main, JTable table) {
        super(main, table);
        
        flightTitle = main.getFlightOverviewTitle();
        flightIDLabel = main.getFlightOverviewIDContent();
        flightStartAirportLabel = main.getFlightOverviewStartContent();
        flightdestAirportLabel = main.getFlightOverviewDestContent();
        
    }
    
    @Override
    public void populate() {
       DefaultTableModel defaultTable = (DefaultTableModel) table.getModel();
       ArrayList<Flight> flightsFromDb = dataBase.getDeserializedFlights();
       
       for( Flight flight : flightsFromDb) {
           defaultTable.addRow(new Object[] { flight.getName(), flight.getId(), flight.getStartPoint(), flight.getDestinationAirport() });
       }
    }
    
    
    public void listen() {
        //Anonymous subclass used for event handling
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            // when the selection inside the flight table changes
            updateOverview();
          
        });
    }
    
    
    private void updateOverview() {
            int selectedRow = table.getSelectedRow();
            String id = tryToFetchAttribute(1, selectedRow);
            String startAirport = tryToFetchAttribute(2, selectedRow);
            String destinationAirport = tryToFetchAttribute(3, selectedRow);
            
            flightIDLabel.setText(id);
            flightStartAirportLabel.setText(startAirport);
            flightdestAirportLabel.setText(destinationAirport);
    }
    
    private String tryToFetchAttribute(int index, int row) {
        if( index > table.getColumnCount() ) {
            throw new ArrayIndexOutOfBoundsException();
        }
        String result;
        try {
            result = table.getValueAt(row, index).toString();
        } 
        catch(NullPointerException ex) {
            result = "";
        }
        
        return result;
    }
    
}
>>>>>>> 2122bab... Add project files
