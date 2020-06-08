/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hspforzheim.menues;

import de.hspforzheim.Eaf;
import de.hspforzheim.commands.MenuOperation;
import de.hspforzheim.commands.SubMenuOperation;
import de.hspforzheim.items.DishManager;


/**
 *
 * @author Cedric Jansen, Lukas Gabriel
 */
public class DishMenu extends Menu{
    
   
    private boolean isInDishMenu;
    
    private static DishManager dishManager = Eaf.getDishManager();
    
    private SubMenuOperation backToMenu = new SubMenuOperation(0, "Back to main menu");
    private SubMenuOperation createDish = new SubMenuOperation(1, "Create new dish");
    private SubMenuOperation updateDish = new SubMenuOperation(2, "Update a dish");
    private SubMenuOperation deleteDish = new SubMenuOperation(3, "Delete a dish");
    private SubMenuOperation showDishes = new SubMenuOperation(4, "Show dish list");
    
    
    private final SubMenuOperation[] subMenuOperations = new SubMenuOperation[] { 
                                                        createDish, updateDish, deleteDish, 
                                                        showDishes, backToMenu};
    
    
    public DishMenu() {
        super();
        this.title = "MANAGING DISH MENU";
    }
     
    @Override
    public void start() {
        isInDishMenu = true;
        while(isInDishMenu) {
            printFullHeader();
            printSubHeader();
            printCommands();
            printChoiceQuestion();
            userInput();
            handleUserInput();
        }  
    }
    
  
    @Override
    protected void handleUserInput( ) {
        switch(userInput) {
            case 0:
                isInDishMenu = false;
                break;
            case 1:
                System.out.println("\nEFA: ADD DISH:" );
                dishManager.addDish();
                break;
            case 2:
                System.out.println("\nEFA: DISH UPDATE:" );
                dishManager.updateDish();
                break;
            case 3:
                System.out.println("\nEFA: DISH DELETION:" );
                dishManager.deleteDish();
                break;
            case 4:
                System.out.println("\nEFA: DISH LIST:" );
                dishManager.listDishes();
                break;
            default:
                
                break;
        }
    }
    
    @Override
    protected void printCommands() {
        for(MenuOperation op : subMenuOperations) {
            if(op.getKey() == 0) {
                System.out.printf("\n%-18s%6s\n", op.getConsoleName(), op.getKey());
            }
            else {
                 System.out.printf("%-18s%6s\n", op.getConsoleName(), op.getKey());
            }
        }
    }
    
    public void setIsInDishMenu( boolean valueToSet) {
        isInDishMenu = valueToSet;
    }
    
}
