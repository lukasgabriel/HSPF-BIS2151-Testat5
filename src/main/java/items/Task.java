/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.Main;

/**
 *
 * @author Cedric Jansen
 */

// A new Task is created whenever the user casts an action inside the application
// such a as editing the flight table, updating flights, dishes etc.
// Not implemented yet.
public class Task extends JPanel{
    
    private static final JPanel parentContainer = Main.inst.getTaskScrollPanel();
    
    public enum TaskObjectType{Flight, Dish};
    public enum TaskCallType{Create, Update, Delete};
    
    
    private Date generationDate;
    
    private String month;
    private String day;
    private String sec;
    private String min;
    private String hour;
    
    private String obj;
    private String action;
    private String contentString;
    
    private JLabel content = new JLabel();
    
    
    public Task(TaskObjectType objectType, TaskCallType type) {
       super();
       buildNewLook();
       
       action = determineAction(type); 
       obj = determineObject(objectType);

       
      
       contentString = generateContentString(obj, action);
       content.setText(contentString);
       add(content, BorderLayout.WEST);
       content.setVisible(true);
   
       parentContainer.add(this);
       setVisible(true);
       parentContainer.add(Box.createRigidArea(new Dimension(0, 10)));
       parentContainer.revalidate();
    }
    
    
    
    //Converts the enum to string displayed in a task.
    private String determineObject(TaskObjectType objectType) {
        String result = "";
        switch(objectType) {
            case Flight:
                result = "Flight";
                break;
            case Dish:
                result = "Dish";
                break;
            default: 
                result = "";
                
        }
        return result;
    }
    
    // Sets the action string displayed in the task.
    private String determineAction(TaskCallType type) {
        String result = "was ";
        switch(type) {
            case Create:
                result += "created.";
                break;
            case Update:
                result += "updated.";
                break;
            case Delete:
                result += "deleted";
                break;
            default: 
                result = "";
                break;        
        }
        return result;
    }
    
    private String generateContentString(String obj, String action) {
        String result = "";
        String tmp = ""; 
       
        day = String.valueOf(Calendar.DAY_OF_MONTH);
        hour = String.valueOf(Calendar.HOUR_OF_DAY);
        min = String.valueOf(Calendar.MINUTE);
        tmp = hour + ":" + min + ":  ";
        
        result = tmp + obj + " " + action;
        return result;
    }
    
    // Sets the look/ properties of a task
    public void buildNewLook() {
      Dimension dim = new Dimension();
      dim.setSize(500, 40);
      this.setSize(500, 30);   
      
      this.setMaximumSize(new Dimension(1500,30));
      this.setAlignmentX(LEFT_ALIGNMENT);
      this.setBackground(Color.WHITE);
      
      this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 8));
      
    }
}
