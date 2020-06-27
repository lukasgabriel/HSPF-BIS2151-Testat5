/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPanel;
import main.Main;

/**
 *
 * @author Cedric Jansen, Lukas Gabriel
 */
public class GuiUtils {
    
    public static Color navActiveColor = new Color(240, 240, 240);
    public static Color navPassiveColor = new Color(255, 255, 255);
    
    private static Main main;
    
    // Setting a static reference variable because we want to use
    // Gutils as static helper class.
    // This method is called after Main is instantiated.
    public static void initMain( Main mainRunnable) {
        main = mainRunnable;
    }
    
    
    // Changes the background colors of the active and passive buttons to enhance
    // visibility for the end user
    public static void changeNavbar(JButton button, JPanel navbar) {
        //set the background color of the active color
        button.setBackground(navActiveColor);
        
        // get all childs in the navigation bar
        Component[] navChilds = navbar.getComponents();
        // loop over all childs
        for(Component child : navChilds) {
            // if the child is a button and not the active button, set the background to passive
            if(child instanceof JButton && !child.equals(button)){
                child.setBackground(navPassiveColor);
            }         
        }
    }
    
    // Makes the new active pane visible and hides the other panes
    // Used for navigation calls simultaneously with changeNavbar()
    public static void swapActivePane(JPanel activePane) {
        JPanel parentContainer = main.getCustomContentPane();
        main.getLayoutManager().show(parentContainer, activePane.getName());    
        
    }
    
    
    
    
    

}