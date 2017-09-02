package gui.state;

import gui.SudokuGUI;

/**
 * This class sets up the GUI's help page using the State pattern
 * The help page describes how to use the program and how to play Sudoku
 * @author Timothy Geary
 */
public class HelpState extends State{
    @Override
    public void setPage(SudokuGUI gui){
        nextPage( gui, Page.MENU );
        //todo - actually construct this page
    }

    @Override
    public void nextPage(SudokuGUI gui, Page name){
        switch(name){
            case MENU: gui.setState(new MenuState()); break;
        }
    }
}
