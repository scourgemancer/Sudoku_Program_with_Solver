package gui.state;

import gui.SudokuGUI;

/**
 * The State interface that's used by SudokuGUI to implement the State design pattern
 * Each implementing class represents a different type of page that the user can be on in the GUI
 * @author Timothy Geary
 */
public interface State{
    void nextPage(SudokuGUI gui);
}
