package gui.state;

import gui.SudokuGUI;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * The State interface that's used by SudokuGUI to implement the State design pattern
 * Each implementing class represents a different type of page that the user can be on in the GUI
 * @author Timothy Geary
 */
public abstract class State{
    /** Utility function to set the background for all of the pages */
    private void setBackground(Region region, String image ){
        Image img = new Image( getClass().getResourceAsStream("resources/" + image) );
        BackgroundImage BI = new BackgroundImage(img,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT );
        region.setBackground( new Background(BI) );
    }

    /** Utility function to set the size of a region */
    private void setSize( Region reg, double width, double height ){
        reg.setMaxSize( width, height );
        reg.setMinSize( width, height );
        reg.setPrefSize( width, height );
    }

    /** Sets the mouse to close its hand over the region */
    private void setMouseHover(Node node, Stage stage ){
        node.setOnMouseEntered(e -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stage.getScene().setCursor( Cursor.HAND );
                }
            });
        });
        node.setOnMouseExited(e -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stage.getScene().setCursor(Cursor.DEFAULT);
                }
            });
        });
    }

    /** Called when the app changes state, and is passed the Stage to set and the name of the page it's changing to */
    public void nextPage(SudokuGUI gui){};
}
