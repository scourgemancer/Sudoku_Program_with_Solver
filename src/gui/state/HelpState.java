package gui.state;

import gui.SudokuGUI;
import javafx.stage.Stage;

/**
 * This class sets up the GUI's help page using the State pattern
 * The help page describes how to use the program and how to play Sudoku
 * @author Timothy Geary
 */
public class HelpState extends State{
    @Override
    public void setPage(SudokuGUI gui, Stage stage){
        nextPage( gui, stage, "menu" ); //todo - actually construct this page
    }

    @Override
    public void nextPage(SudokuGUI gui, Stage stage, String name){

    }
}
