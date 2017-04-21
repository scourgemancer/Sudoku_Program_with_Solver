package gui.state;

import gui.SudokuGUI;

/**
 * The State interface that's used by SudokuGUI to implement the State design pattern
 * Each implementing class represents a different type of page that the user can be on in the GUI
 * @author Timothy Geary
 */
public interface State{
    /** Called when the app changes state, and is passed the Stage to set and the name of the page it's changing to */
    void nextPage(SudokuGUI gui);
}
