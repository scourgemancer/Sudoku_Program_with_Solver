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
    /** Animates the surrounding frame to move between two difficulty selections */
    private void animateSelection(Node target, Node frame, Stage stage ){
        TranslateTransition animation = new TranslateTransition( Duration.millis(400), frame );
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

    /** Utility function to set the style of a difficulty selection button */
    private void styleDifficultyButton( Button button, String difficulty, Button target,
                                        Stage stage, ImageView frame, SudokuGUI gui ){
        button.setFont( Font.loadFont( getClass().getResourceAsStream("resources/Indieflower.ttf"), gui.stage.getWidth()/23 ));
        button.setOnAction(e -> {
            gui.difficulty = difficulty;
            animateSelection( target, frame, stage );
        });
        button.setBackground( new Background(
                new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        setMouseHover( button, stage );
    }

    @Override
    public void setPage(SudokuGUI gui){
        BorderPane window = new BorderPane();
        Scene scene = new Scene(window);
        setBackground( window, "light.jpg" );


        VBox difficulties = new VBox();
        difficulties.setSpacing( gui.stage.getHeight() / 15 );
        difficulties.setPadding( new Insets( 0, 0, gui.stage.getHeight()/25, 0 ) );
        difficulties.setAlignment( Pos.CENTER );

        Image frameImage = new Image( getClass().getResourceAsStream("resources/transparentFrame.png") );
        ImageView frame = new ImageView( frameImage );
        frame.setFitHeight( gui.stage.getHeight()/9 );
        frame.setFitWidth( gui.stage.getHeight()/3 );
        gui.difficulty = "normal";
        frame.setTranslateY( - gui.stage.getHeight() / 55 ); //this centers it on the normal difficulty

        Button superEasy = new Button("Super Easy");
        Button easy = new Button("Easy");
        Button normal = new Button("Normal");
        Button hard = new Button("Hard");
        Button extreme = new Button("Extreme");

        styleDifficultyButton( superEasy, "super_easy", superEasy, gui.stage, frame, gui );
        styleDifficultyButton( easy, "easy", easy, gui.stage, frame, gui );
        styleDifficultyButton( normal, "normal", normal, gui.stage, frame, gui );
        styleDifficultyButton( hard, "hard", hard, gui.stage, frame, gui );
        styleDifficultyButton( extreme, "extreme", extreme, gui.stage, frame, gui );

        difficulties.getChildren().addAll( superEasy, easy, normal, hard, extreme );
        StackPane holder = new StackPane( difficulties );
        holder.getChildren().add( frame );
        window.setCenter( holder );


        Button menu = new Button("Menu");
        setMouseHover( menu, gui.stage );
        setSize( menu, gui.stage.getHeight()/6, gui.stage.getHeight()/6 );
        menu.setFont( Font.loadFont( getClass().getResourceAsStream("resources/Indieflower.ttf"), gui.stage.getWidth()/35 ));
        Image menuImage = new Image( getClass().getResourceAsStream("resources/frameSquarish.png") );
        menu.setBackground( new Background( new BackgroundImage( menuImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize( 100, 100, true, true, true, false ) ) ) );
        menu.setOnAction(e -> nextPage( gui, Page.MENU ) );
        window.setLeft( menu );
        BorderPane.setAlignment( menu, Pos.BOTTOM_LEFT );
        BorderPane.setMargin( menu, new Insets(0, 0, gui.stage.getHeight()/100, gui.stage.getWidth()/50) );


        Button select = new Button("Select");
        setMouseHover( select, gui.stage );
        setSize( select, gui.stage.getHeight()/6, gui.stage.getHeight()/6 );
        select.setFont( Font.loadFont( getClass().getResourceAsStream("resources/Indieflower.ttf"), gui.stage.getWidth()/35 ));
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
            nextPage( gui, Page.PUZZLE );
        });
        window.setRight( select );
        BorderPane.setAlignment( select, Pos.BOTTOM_RIGHT );
        BorderPane.setMargin( select, new Insets(0, gui.stage.getWidth()/50, gui.stage.getHeight()/100, 0 ));


        gui.stage.setTitle( "Sudoku" );
        gui.stage.setScene( scene );
    }

    @Override
    public void nextPage(SudokuGUI gui, Page name){
        switch(name){
            case MENU:   gui.setState(new MenuState());            break;
            case PUZZLE: gui.setState(new PuzzleSelectionState()); break;
        }
    }
}
