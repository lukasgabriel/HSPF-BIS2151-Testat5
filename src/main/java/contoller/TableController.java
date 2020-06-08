<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contoller;

import database.DataBase;
import javax.swing.JTable;
import main.Main;


/**
 *
 * @author Cedric Jansen
 */
public abstract class TableController {
    //Reference to the object table that should be controlled
    protected static Main main;
    protected JTable table;
    protected static DataBase dataBase;
    
    public TableController(Main main, JTable table) {
        this.main = main;
        this.table = table;
        try {
          dataBase = main.getDataBase();
        } 
        catch( NullPointerException ex) {
            System.out.println("Tried to initialise a Table Controller before the database was set up.");
            throw new NullPointerException();
        }
        
    }
    
    
    
    // load objects into the table
    public abstract void populate();
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contoller;

import database.DataBase;
import javax.swing.JTable;
import main.Main;


/**
 *
 * @author Cedric Jansen
 */
public abstract class TableController {
    //Reference to the object table that should be controlled
    protected static Main main;
    protected JTable table;
    protected static DataBase dataBase;
    
    public TableController(Main main, JTable table) {
        this.main = main;
        this.table = table;
        try {
          dataBase = main.getDataBase();
        } 
        catch( NullPointerException ex) {
            System.out.println("Tried to initialise a Table Controller before the database was set up.");
            throw new NullPointerException();
        }
        
    }
    
    
    
    // load objects into the table
    public abstract void populate();
}
>>>>>>> 2122bab... Add project files
