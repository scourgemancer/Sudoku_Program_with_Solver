package gui.state;

import gui.SudokuGUI;

/**
 * This class sets up the GUI's puzzle selection page using the State pattern
 * The puzzle selection page allows the user to select specific puzzles to play
 * @author Timothy Geary
 */
public class PuzzleSelectionState extends State{
    @Override
    public void setPage(SudokuGUI gui){
        nextPage(gui, Page.GAME);
    }

    @Override
    public void nextPage(SudokuGUI gui, Page name){
        switch(name){
            case GAME: gui.setState(new GameState()); break;
        }
    }
}
