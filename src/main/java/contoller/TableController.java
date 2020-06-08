/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contoller;

import database.DataBase;
import java.awt.Color;
import javax.swing.JTable;
import main.Main;


/**
 *
 * @author Cedric Jansen
 */

// Parent class for all Table Controller
public abstract class TableController {
    //Reference to the object table that should be controlled
    protected static Main main;
    protected JTable table;
    protected static DataBase dataBase;
    protected Integer selectionPointer = 0;
    
    public TableController(Main main, JTable table) {
        TableController.main = main;
        this.table = table;
        try {
          dataBase = main.getDataBase();
        } 
        catch( NullPointerException ex) {
            System.out.println("Tried to initialise a Table Controller before the database was set up.");
            throw new NullPointerException();
        }
        
        table.setSelectionBackground(Color.BLACK);
        table.setSelectionForeground(Color.WHITE);
        
    }
    
    public void setSelectionPointer(int pointer) {
        selectionPointer = pointer;
    }
    
    // load objects into the table
    public abstract void populate();
    
}
