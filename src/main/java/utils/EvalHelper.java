/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Cedric Jansen
 */
public class EvalHelper {
    
    // Returns true if name is larger than 4 and smaller than 64
    public static boolean isName(String s) {
        if(s.length() >= 5 && s.length() < 64) {
           return true;
        }
        return false;
    }
    
    
    // Returns true if destination name is larger than 4 and smaller than 64
    public static boolean isDestination(String s) {
        if(s.length() >= 5 && s.length() < 64) {
           return true;
        }
        return false;
    }
    
    
    // Returns true if airport name is larger than 4 and smaller than 64.
    public static boolean isAirport(String s) {
        if(s.length() >= 5 && s.length() < 64) {
           return true;
        }
        return false;
    }
    
    
    // Returns true if price is higher than 0 and below 100
    public static boolean isPrice(float price) {
        return price > 0 && price < 100;
    }
}
