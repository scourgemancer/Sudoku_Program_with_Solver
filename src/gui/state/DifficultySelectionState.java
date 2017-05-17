package gui.state;

import gui.SudokuGUI;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.SudokuModel;

import java.io.FileNotFoundException;

/**
 * This class sets up the GUI's difficulty selection page using the State pattern
 * The difficulty selection page asks the user to select the difficulty that they want their puzzle to be
 * @author Timothy Geary
 */
public class DifficultySelectionState extends State{
    /** Utility function to set the style of a difficulty selection button */
    /** Animates the surrounding frame to move between two difficulty selections */
    private void animateSelection(Node target, Node frame, Stage stage ){
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

    private void styleDifficultyButton( Button button, String difficulty, Button target,
                                        Stage stage, ImageView frame, SudokuGUI gui ){
        button.setFont( Font.loadFont("file:src/gui/resources/IndieFlower.ttf", 30) );
        button.setOnAction(e -> {
            gui.difficulty = difficulty;
            animateSelection( target, frame, stage );
        });
        button.setBackground( new Background(
                new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        setMouseHover( button, stage );
    }

    @Override
    public void setPage(SudokuGUI gui, Stage stage){
        VBox difficulties = new VBox();
        difficulties.setSpacing( stage.getHeight() / 20 );
        difficulties.setPadding( new Insets( stage.getHeight()/45, 0, 0, 0 ) );
        difficulties.setAlignment( Pos.CENTER );
        StackPane holder = new StackPane( difficulties );
        Scene scene = new Scene( holder );
        setBackground( holder, "light.jpg" );

        Button superEasy = new Button("Super Easy");
        Button easy = new Button("Easy");
        Button normal = new Button("Normal");
        Button hard = new Button("Hard");
        Button extreme = new Button("Extreme");

        Image frameImage = new Image( getClass().getResourceAsStream("resources/transparentFrame.png") );
        ImageView frame = new ImageView( frameImage );
        holder.getChildren().add( frame );
        frame.setFitHeight( stage.getHeight()/9 );
        frame.setFitWidth( stage.getHeight()/3 );
        gui.difficulty = "normal";
        frame.setTranslateY( - stage.getHeight() / 14 ); //this centers it on the normal difficulty

        styleDifficultyButton( superEasy, "super_easy", superEasy, stage, frame, gui );
        styleDifficultyButton( easy, "easy", easy, stage, frame, gui );
        styleDifficultyButton( normal, "normal", normal, stage, frame, gui );
        styleDifficultyButton( hard, "hard", hard, stage, frame, gui );
        styleDifficultyButton( extreme, "extreme", extreme, stage, frame, gui );

        HBox options = new HBox();
        options.setPadding( new Insets( 0, 0, 0, stage.getWidth()/20 ) );
        options.setSpacing( 5*stage.getWidth()/8 );

        Image backImage = new Image( getClass().getResourceAsStream("resources/back.png") );
        ImageView back = new ImageView( backImage );
        back.setPreserveRatio( true );
        back.setFitHeight( stage.getHeight()/9 );
        back.setPickOnBounds(true);
        setMouseHover( back, stage );
        back.setOnMouseClicked(e -> nextPage( gui, "menu" ) );

        Button select = new Button("Select");
        setMouseHover( select, stage );
        setSize( select, stage.getHeight()/8, stage.getHeight()/8 );
        Image selectImage = new Image( getClass().getResourceAsStream("resources/frameSquarish.png") );
        select.setBackground( new Background( new BackgroundImage( selectImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize( 100, 100, true, true, true, false ) ) ) );
        select.setOnAction(e -> {
            try{
                gui.model = new SudokuModel(gui.difficulty);
            }catch(FileNotFoundException fnfe){
                System.out.println(fnfe.getMessage());
                System.exit(-1);
            }
            gui.model.addObserver(gui);
            nextPage( gui, "puzzleselection" );
        });

        options.getChildren().addAll( back, select );
        difficulties.getChildren().addAll( superEasy, easy, normal, hard, extreme, options );

        stage.setTitle( "Sudoku" );
        stage.setScene( scene );
    }

    @Override
    public void nextPage(SudokuGUI gui, String name) {

    }
}
