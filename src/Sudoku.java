import gui.SudokuGUI;
import javafx.application.Application;
import ptui.ControllerPTUI;
import ptui.SudokuPTUI;

import java.io.FileNotFoundException;

/**
 * This is the main class for my Sudoku program. It can either be run with
 * a plain text UI(SudokuPTUI) or with a JavaFX graphical UI(SudokuGUI).
 * It takes one argument, either 'ptui' or 'gui' to use the respective view
 * @author Timothy Geary
 */
public class Sudoku{
    /** The 2 modes are GUI and PTUI */
    private enum Views{GUI, PTUI, UNKNOWN}

    /** Displays the usage message and exits the program. */
    private static void usageError(){
        System.err.println("Usage: java Sudoku (gui | ptui (super_easy | easy | normal | hard | extreme))");
        System.exit(-1);
    }

    /**
     * Determines the view to use, either launching the gui
     * or handing control over to the ptui's controller
     * @param args the preferred view
     */
    public static void main(String[] args){
        //get the preferred view from the command line
        Views view = Views.UNKNOWN;
        if(args.length == 1){
            try{
                view = Views.valueOf(args[0].toUpperCase());
            }catch(IllegalArgumentException iae){
                usageError();
            }
        }else{
            usageError();
        }
        try{    //now the program either launches the GUI or creates the PTUI to pass control to
            switch(view){
                case GUI:   //this launches the GUI
                    Application.launch(SudokuGUI.class);
                    break;
                case PTUI:
                    SudokuPTUI ptui = new SudokuPTUI();
                    ControllerPTUI ctrlr = new ControllerPTUI(ptui.getModel());
                    ctrlr.run();    //now pass control to the run method of the controller
                    break;
                case UNKNOWN:
                    usageError();
            }
        }catch(FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
            System.exit(-1);
        }
    }
}