/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hspforzheim.commands;

import de.hspforzheim.Eaf;
import de.hspforzheim.menues.FlightMenu;
import java.util.ArrayList;

/**
 *
 * @author Cedric Jansen, Lukas Gabriel
 */
public class MenuOperation {
    
  
    private static ArrayList<Integer> usedKeys = new ArrayList<>();
    
    protected int key;
    protected String consoleName;
    private OperationType operationType;
    
    
    public MenuOperation(int key, String name) {
        this.key = key;
        this.consoleName = name;
        addKey();
    }
    
    public MenuOperation(int key, String name, OperationType type) {
        this.key = key;
        this.consoleName = name;
        this.operationType = type;
        addKey();
    }
    
    
    protected void addKey() {
        if(!usedKeys.contains(key)) {
            usedKeys.add(key);
        }
    }
    
    public int getKey() {
        return key;
    }
    
    public String getConsoleName() {
        return consoleName;
    }
    
    public ArrayList<Integer> getUsedKeys() {
        return usedKeys;
    }
    
}
