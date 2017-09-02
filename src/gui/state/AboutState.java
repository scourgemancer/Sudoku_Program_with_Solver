package gui.state;

import gui.SudokuGUI;

/**
 * This class sets up the GUI's about page using the State pattern
 * The about page just displays relevant information about the program such as who made it, why, and copyright info
 * @author Timothy Geary
 */
public class AboutState extends State{
    @Override
    public void setPage(SudokuGUI gui){
        nextPage( gui, Page.MENU );
        //todo - implement this page
    }

    @Override
    public void nextPage(SudokuGUI gui, Page name){
        switch(name){
            case MENU: gui.setState(new MenuState()); break;
        }
    }
}
