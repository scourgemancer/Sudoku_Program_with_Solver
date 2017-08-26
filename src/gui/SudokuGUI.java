package gui;

import gui.state.MenuState;
import model.SudokuModel;

import gui.state.State;
import javafx.stage.Screen;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

//todo - select puzzle before playing it, let them cycle through all 10000 and keep count
//todo - original numbers are black and placed numbers are blue
//todo - selecting a number option highlights all squares of that option (if all filled make the num option green)
//todo - highlighting a numbered square will make simliar numbered squares have yellow text and all boxes that matter to it get highlightedish
//todo - combine isGoal and isValid into Check and it says how many are left if isValid but not isGoal
//todo - have the given numbers be grayed out and not be instantiated as the private class (make a gray.png)
//todo - also have the option of solving the user's own puzzles they input themselves (backtrack for these)
//todo - allow scrawling small ints as personal reminders, 1 in top left, 9 bottom right, 4 middle left style
//todo - play fireworks from ensemble upon successful solving, one firework if solved was used (option for it in options)
//todo - remember to reset the stage's title after playing a game
//todo: undo redo check hint solve, then restart newGame home; check is isValid and isGoal restart can keep calling undo

/**
 * The view class that implements a JavaFX UI. This class represents both
 * the view and controller portions of the UI. It is connected to the model
 * and receives updates from it.
 * @author Timothy Geary
 */
public class SudokuGUI extends Application implements Observer{
    public SudokuModel model;

    public Stage stage;

    private State currentState;

    /** The label for the update method */
    public Text status;

    public String difficulty;

    /** Lets the update function know if there's error highlighting to undo */
    public boolean recentError;
    public int[] errorPos;

	/** A setter for the currentState variable **/
	public void setState( State newState ){
		currentState = newState;
		currentState.setPage( this );
	}

    /** A utility function that attempts to open a given url in the default web browser **/
    public void openWebpage( String url ){ getHostServices().showDocument( url ); }

    @Override
    public void start(Stage stage) throws Exception{
        stage.setHeight( Screen.getPrimary().getBounds().getHeight() * 7 / 8 );
        stage.setWidth( Screen.getPrimary().getBounds().getWidth() / 2 );
        this.stage = stage;
        setState( new MenuState( ) );
        stage.setTitle("Sudoku");
        stage.getIcons().add( new Image(getClass().getResourceAsStream("state/resources/icon.png")) );
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg){
        status.setText(model.textout);
        if(recentError){
            recentError = false;
            //setBackground(puzzle.get(errorPos[0]).get(errorPos[1]), "white.png"); - image no longer exists
        }
//todo - remember how this function is used and update it so the program can operate again
        if(model.textout.contains("Error")){
            recentError = true;
            System.arraycopy(model.pos, 0, this.errorPos, 0, 2);
            //setBackground(puzzle.get(errorPos[0]).get(errorPos[1]), "red.png"); - image no longer exists
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
