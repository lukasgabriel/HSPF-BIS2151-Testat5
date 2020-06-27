/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hspforzheim.commands;

import de.hspforzheim.Eaf;
import de.hspforzheim.items.FlightManager;
import java.util.ArrayList;

/**
 *
 * @author Cedric Jansen, Lukas Gabriel
 */
public class SubMenuOperation extends MenuOperation {
    
    private static ArrayList<Integer> usedKeys = new ArrayList<Integer>();
    private static FlightManager flightManager = Eaf.getFlightManager();
    private SubOperationType subOperationType;
    
    
    public SubMenuOperation(int key, String string) {
        super(key, string);
        
    }
    
    public SubMenuOperation(int key, String string, SubOperationType type) {
        super(key, string);
        subOperationType = type;
    }
    
   
    @Override
    protected void addKey() {
        if(!usedKeys.contains(key)) {
            usedKeys.add(key);
        }
    }
    
    @Override
    public ArrayList<Integer> getUsedKeys() {
        return usedKeys;
    }
}
