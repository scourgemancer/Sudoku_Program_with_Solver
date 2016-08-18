package gui;

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
//todo - have an option to submit an answer and set off fireworks if it's correct
//todo - allow scrawling small ints as personal reminders, 1 in top left, 9 bottom right, 4 middle left style
//todo - play fireworks from ensemble upon successful solving, one firework if solved was used
//todo - remember to reset the stage's title after playing a game
//todo - allow user to input their own puzzles
//todo: undo redo check hint solve, then restart newGame home; check is isValid and isGoal restart can keep calling undo

/**
 * The view class that implements a JavaFX UI. This class represents both
 * the view and controller portions of the UI. It is connected to the model
 * and receives updates from it.
 * @author Timothy Geary
 */
public class SudokuGUI extends Application implements Observer{
    /** The UI's connection to the model */
    private SudokuModel model;

    /** The label for the update method */
    private Text status;

    /** A 2d button matrix for the update method */
    private ArrayList< ArrayList<numButton> > puzzle;

    /** The selected difficulty formatted as a filename */
    private String difficulty;

    /** Lets the update function know if there's error highlighting to undo */
    private boolean recentError;
    private int[] errorPos;

    /** The tilepane that holds the buttons */
    private TilePane grid;

    /** A private class for the buttons */
    private class numButton extends Button{
        SudokuModel model;
        private int row;
        private int col;
        private int num;

        private numButton(int row, int col, int num, SudokuModel model){
            this.model = model; //todo - set images to numbers if text isn't enough
            this.row = row;
            this.col = col;
            this.num = num;
            switch(num){
                /*case 1:
                    //Image pillarImg = new Image(getClass().getResourceAsStream("resources/pillarX.png"));
                    //ImageView pillarIcon = new ImageView(pillarImg);
                    //this.setGraphic(pillarIcon);
                    setButtonBackground(this, "white.png");
                    break;
                case 2:
                    //Image beamImg = new Image(getClass().getResourceAsStream("resources/beam.png"));
                    //ImageView beamIcon = new ImageView(beamImg);
                    //this.setGraphic(beamIcon);
                    setButtonBackground(this, "yellow.png");
                    break;
                case 3:
                    //Image laserImg = new Image(getClass().getResourceAsStream("resources/laser.png"));
                    //ImageView laserIcon = new ImageView(laserImg);
                    //this.setGraphic(laserIcon);
                    setButtonBackground(this, "yellow.png");
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;*/
                default:
                    //Image whiteImg = new Image(getClass().getResourceAsStream("resources/white.png"));
                    //ImageView whiteIcon = new ImageView(whiteImg);
                    //this.setGraphic(whiteIcon);
                    //setButtonBackground(this, "white.png");
            }
            this.setOnAction(e -> this.pressed());
        }

        private void pressed(){
            //todo - increase num and cycle to empty
        }
    }

    /** Utility function to set the background for all of the pages */
    private void setBackground( Region region, String image ){
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

    /** Utility function to set the style of a main menu option button */
    private void styleOptionButton( Button button, double width, Stage stage ){
        if(button.getText().length() > 5){
            button.setFont( Font.loadFont("file:src/gui/resources/IndieFlower.ttf", width / 5 ) );
        }else{
            button.setFont( Font.loadFont("file:src/gui/resources/IndieFlower.ttf", width / 4 ) );
        }
        button.setEllipsisString("");

        //the rest is all for a 'hover-over' background color change
        button.setBackground( new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)) );
        button.setOnMouseEntered(e -> {
            button.setBackground( new Background(
                    new BackgroundFill(Color.valueOf("#F4F9FF"), new CornerRadii(5.0), Insets.EMPTY)) );
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stage.getScene().setCursor( Cursor.HAND );
                }
            });
        });
        button.setOnMouseExited(e -> {
            button.setBackground(new Background(
                    new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stage.getScene().setCursor(Cursor.DEFAULT);
                }
            });
        });
    }

    /** Utility function to set the style of a difficulty selection button */
    private void styleDifficultyButton( Button button, String difficulty, Button target, Stage stage, ImageView frame ){
        button.setFont( Font.loadFont("file:src/gui/resources/IndieFlower.ttf", 30) );
        button.setOnAction(e -> {
            this.difficulty = difficulty;
            animateSelection( target, frame, stage );
        });
        button.setBackground( new Background(
                new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        setMouseHover( button, stage );
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

    /** Sets up the starting menu stage */
    private void setMenuScreen(Stage stage){
        BorderPane window = new BorderPane();
        Scene scene = new Scene( window );
        setBackground( window, "light.jpg" );

        Image titleImage = new Image( getClass().getResourceAsStream("resources/sudoku_title.png") );
        ImageView title = new ImageView( titleImage );
        title.setPreserveRatio( true );
        title.setFitWidth( 2.0 * stage.getWidth() / 3.0 );
        BorderPane.setMargin( title, new Insets( 0.05 * stage.getHeight(), 0, 0, 0 ) );
        window.setTop( title );
        BorderPane.setAlignment( title, Pos.CENTER );

        TilePane options = new TilePane();
        options.setPrefColumns( 3 );
        double squareDim = ( stage.getHeight() - title.getFitHeight() ) * (35.0/48.0);
        setSize( options, squareDim, squareDim );
        options.setHgap( stage.getHeight() / 110.0 );
        options.setVgap( stage.getWidth() / 110.0 );

        //the sizes for each of the option buttons
        //todo double width = squareDim * (340.0 / 456.0) / 3.1;
        double width = squareDim * (340.0 / 456.0) / 3.12;
        double height = squareDim * ( (297.0 / 402.0) / 3.1 ) - (stage.getHeight() / 100)*3 + 2;

        //blank squares
        Label option2 = new Label();
        Label option4 = new Label();
        Label option6 = new Label();
        Label option8 = new Label();
        setSize( option2, width, height );
        setSize( option4, width, height );
        setSize( option6, width, height );
        setSize( option8, width, height );

        Button about = new Button("About");
        about.setOnAction(e -> setAboutScreen(stage));
        styleOptionButton( about, width, stage );
        setSize( about, width, height );

        Button help = new Button("Help");
        help.setOnAction(e -> setHelpScreen(stage));
        styleOptionButton( help, width, stage );
        setSize( help, width, height );

        Button start = new Button("Play");
        start.setOnAction(e -> setDifficultySelectionScreen(stage));
        styleOptionButton( start, width, stage );
        setSize( start, width, height );

        Button donate = new Button("Donate"); //TODO - Allow for GooglePay, Bitcoin, Dogecoin, etc
        donate.setOnAction(e -> getHostServices().showDocument( "https://www.paypal.me/TimGeary" ) );
        styleOptionButton( donate, width, stage );
        setSize( donate, width, height );

        Button quit = new Button("Quit");
        quit.setOnAction(e -> Platform.exit());
        styleOptionButton( quit, width, stage );
        setSize( quit, width, height );

        Image image = new Image( getClass().getResourceAsStream("resources/frame.png") );
        BackgroundImage optionsBackground = new BackgroundImage( image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize( 100, 100, true, true, true, false ) );
        options.setBackground( new Background( optionsBackground ) );

        options.setPadding( new Insets( //so the buttons don't go over the bamboo stalks
                (51.0 / 402.0) * squareDim, //top
                (57.0 / 456.0) * squareDim, //right
                (52.0 / 402.0) * squareDim, //bottom
                (59.0 / 456.0) * squareDim ) ); //left

        options.getChildren().addAll( about, option2, help, option4, start, option6, donate, option8, quit );
        window.setBottom( options );
        BorderPane.setAlignment( options, Pos.CENTER );
        BorderPane.setMargin( options, new Insets(
                ( stage.getHeight() - squareDim - title.getFitHeight() ) / 8, 0, 0, 0 ) );

        stage.setTitle( "Sudoku" );
        stage.setScene(scene);
    }

    /** Sets up the help stage */
    private void setHelpScreen(Stage stage){
        //todo
    }

    /** Sets up the about stage */
    private void setAboutScreen(Stage stage){
        //todo
    }

    /** Sets up a stage to select a difficulty and set a filename based on that */
    private void setDifficultySelectionScreen(Stage stage){
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
        this.difficulty = "normal";
        frame.setTranslateY( - stage.getHeight() / 14 ); //this centers it on the normal difficulty

        styleDifficultyButton( superEasy, "super_easy", superEasy, stage, frame );
        styleDifficultyButton( easy, "easy", easy, stage, frame );
        styleDifficultyButton( normal, "normal", normal, stage, frame );
        styleDifficultyButton( hard, "hard", hard, stage, frame );
        styleDifficultyButton( extreme, "extreme", extreme, stage, frame );

        HBox options = new HBox();
        options.setPadding( new Insets( 0, 0, 0, stage.getWidth()/20 ) );
        options.setSpacing( 5*stage.getWidth()/8 );

        Image backImage = new Image( getClass().getResourceAsStream("resources/back.png") );
        ImageView back = new ImageView( backImage );
        back.setPreserveRatio( true );
        back.setFitHeight( stage.getHeight()/9 );
        back.setPickOnBounds(true);
        setMouseHover( back, stage );
        back.setOnMouseClicked(e -> setMenuScreen(stage) );

        Button select = new Button("Select");
        setMouseHover( select, stage );
        setSize( select, stage.getHeight()/8, stage.getHeight()/8 );
        Image selectImage = new Image( getClass().getResourceAsStream("resources/frameSquarish.png") );
        select.setBackground( new Background( new BackgroundImage( selectImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize( 100, 100, true, true, true, false ) ) ) );
        select.setOnAction(e -> {
            try{
                this.model = new SudokuModel(this.difficulty);
            }catch(FileNotFoundException fnfe){
                System.out.println(fnfe.getMessage());
                System.exit(-1);
            }
            this.model.addObserver(this);
            setPuzzleSelectionScreen(stage);
        });

        options.getChildren().addAll( back, select );
        difficulties.getChildren().addAll( superEasy, easy, normal, hard, extreme, options );

        stage.setTitle( "Sudoku" );
        stage.setScene( scene );
    }

    /** Sets up a stage to select a specific sudoku puzzle from the selected difficulty */
    private void setPuzzleSelectionScreen(Stage stage){
        //todo
        setGameScreen(stage);
    }

    /** Sets up the sudoku game's stage once a file and linenumber are selected */
    private void setGameScreen(Stage stage){
        this.errorPos = new int[] {0,0};

        Text status = new Text( difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1) + " selected");
        this.status = status;

        TilePane puzzle = new TilePane();
        puzzle.setPrefColumns(9);
        this.puzzle = new ArrayList<>();
        for(int r=0; r < 9; r++){
            this.puzzle.add( new ArrayList<>() );
            for(int c=0; c < 9; c++){
                numButton newButton = new numButton(r, c, model.puzzle[r][c], model);
                puzzle.getChildren().add( newButton );
                this.puzzle.get(r).add(c, newButton);
            }
        }
        this.grid = puzzle;
        Image img = new Image( getClass().getResourceAsStream("resources/gameFrameNoLines.png") );
        BackgroundImage BI = new BackgroundImage( img,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize( stage.getWidth(), 100, false, true, true, false ) );
        puzzle.setBackground( new Background(BI) );

        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        Button check = new Button("Check");
        Button hint = new Button("Hint");
        Button solve = new Button("Solve");
        //add the functionality of the buttons
        undo.setOnAction(e -> model.undo());
        redo.setOnAction(e -> model.redo());
        //todo - the check button's functionality
        hint.setOnAction(e -> this.model.getHint());
        solve.setOnAction(e -> {
            try {
                model.solve(true);
            }catch(FileNotFoundException fnfe){ model.textout = "The file was deleted"; }
        });
        HBox features = new HBox( undo, redo, check, hint, solve );

        Button restart = new Button("Restart");
        Button newGame = new Button("New Puzzle");
        Button menu = new Button("Menu");
        //add the functionality of the buttons
        restart.setOnAction(e -> setGameScreen( stage ));
        newGame.setOnAction(e -> setGameScreen( stage ));
        menu.setOnAction(e -> setMenuScreen(stage));
        HBox functions = new HBox( restart, newGame, menu );

        VBox page = new VBox( status, puzzle, features, functions );
        Scene scene = new Scene(page);
        setBackground( page, "light.jpg" );

        switch( model.filename.substring(0, model.filename.length() - 4).toLowerCase() ){
            case "super_easy":
                stage.setTitle( stage.getTitle() + " - Super Easy Puzzle #" + model.lineNumber);
                break;
            case "easy":
                stage.setTitle("Easy Sudoku puzzle #" + model.lineNumber);
                break;
            case "normal":
                stage.setTitle("Normal Sudoku puzzle #" + model.lineNumber);
                break;
            case "hard":
                stage.setTitle("Hard Sudoku puzzle #" + model.lineNumber);
                break;
            case "extreme":
                stage.setTitle("I'm sorry, it's Sudoku puzzle #" + model.lineNumber + ", good luck");
                break;
        }

        stage.setScene(scene);
    }

    @Override
    public void start(Stage stage) throws Exception{
        stage.setHeight( Screen.getMainScreen().getHeight() * 7 / 8 );
        stage.setWidth( Screen.getMainScreen().getWidth() / 2 );
        setMenuScreen(stage);
        stage.setTitle("Sudoku");
        stage.getIcons().add( new Image( getClass().getResourceAsStream("resources/icon.png") ) );
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg){
        status.setText(model.textout);
        if(recentError){
            recentError = false;
            //setBackground(puzzle.get(errorPos[0]).get(errorPos[1]), "white.png"); - image no longer exists
        }
//todo - get this to work
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
