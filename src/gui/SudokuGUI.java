package gui;

import gui.state.GameState;
import gui.state.MenuState;
import gui.state.State;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import model.SudokuModel;

import com.sun.glass.ui.Screen;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
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
    /** Used by the States to set their respective pages */
    public Stage stage;

    /** The UI's connection to the model */
    public SudokuModel model;

    /** The label for the update method */
    private Text status;

    /** A 2d button matrix for the update method */
    private ArrayList< ArrayList<gui.state.NumButton> > puzzle;

    /** The selected difficulty formatted as a filename */
    public String difficulty;

    /** Lets the update function know if there's error highlighting to undo */
    private boolean recentError;
    private int[] errorPos;

    /** The tilepane that holds the buttons */
    private TilePane grid;

	/** The current state(page) that the gui is on **/
	private State currentState;

	/** A setter for the currentState variable **/
	public void setState( State newState ){
		currentState = newState;
		currentState.setPage( this );
	}

    /** Animates the surrounding frame to move between two difficulty selections */
    private void animateSelection( Node target, Node frame, Stage stage ){
        TranslateTransition animation = new TranslateTransition( Duration.millis(450), frame );
        animation.setInterpolator( Interpolator.EASE_IN );
        //centers the difficulties with and without 'y' in them within the frame properly
        if( target.localToScene( target.getBoundsInLocal(), false ).getMaxY() < stage.getHeight() * 0.4 ){
            animation.setByY( target.localToScene( target.getBoundsInLocal(), false ).getMaxY() -
                    frame.localToScene( frame.getBoundsInLocal(), false ).getMaxY() + stage.getHeight() / 100 );
        }else{
            animation.setByY( target.localToScene( target.getBoundsInLocal(), false ).getMaxY() -
                    frame.localToScene( frame.getBoundsInLocal(), false ).getMaxY() + stage.getHeight() / 150 );
        }
        animation.play();
    }

    /** A utility function that attempts to open a given url in the default web browser **/
    public void openWebpage( String url ){ getHostServices().showDocument( url ); }

    @Override public void start(Stage stage) throws Exception{
    	this.stage = stage;
        stage.setHeight( Screen.getMainScreen().getHeight() * 7 / 8 );
        stage.setWidth( Screen.getMainScreen().getWidth() / 2 );
        setState( new MenuState( ) );
        stage.setTitle("Sudoku");
        stage.getIcons().add( new Image( new File("/resources/icon.png").toURI().toString() ) );
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
