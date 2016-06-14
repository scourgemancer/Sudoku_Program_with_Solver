package gui;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import model.SudokuModel;

import com.sun.glass.ui.Screen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

//todo - select puzzle before playing it, let them cycle through all 10000 and keep count
//todo - original numbers are black and placed numbers are blue
//todo - selecting a number option highlights all squares of that option (if all filled make the num option green)
//todo - difficulty selection highlight animates it's movement between difficulties
//todo - highlighting a numbered square will make simliar numbered squares have yellow text and all boxes that matter to it get highlightedish
//todo - combine isGoal and isValid into Check and it says how many are left if isValid but not isGoal
//todo - have the given numbers be grayed out and not be instantiated as the private class (make a gray.png)
//todo - also have the option of solving the user's own puzzles they input themselves (backtrack for these)
//todo - have an option to submit an answer and set off fireworks if it's correct
//todo - allow scrawling small ints as personal reminders, 1 in top left, 9 bottom right, 4 middle left style
//todo - play fireworks from ensemble upon successful solving, one firework if solved was used

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
    private HashMap<Integer, ArrayList<numButton>> puzzle;

    /** The provided filename */
    private String filename;

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
                    Image whiteImg = new Image(getClass().getResourceAsStream("resources/white.png"));
                    ImageView whiteIcon = new ImageView(whiteImg);
                    this.setGraphic(whiteIcon);
                    setButtonBackground(this, "white.png");
            }
            this.setOnAction(e -> this.pressed());
        }

        private void pressed(){
            //todo - increase num and cycle to empty
        }
    }

    /**
     * A private utility function for setting the background
     * of a button to an image in the resources subdirectory.
     * @param button the button who's background will be set
     * @param bgImgName the name of the image file to be used as a background
     */
    private void setButtonBackground(Button button, String bgImgName){//
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image( getClass().getResourceAsStream("resources/" + bgImgName) ),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true) );
        Background background = new Background(backgroundImage);
        button.setBackground(background); //todo - remove this function and it's dependents
    }

    /** Utility function to set the size of a button */
    private void setSize( Region reg, double width, double height ){
        reg.setMaxSize( width, height );
        reg.setMinSize( width, height );
        reg.setPrefSize( width, height );
    }

    /** Utility function to set the style of a button */
    private void styleButton( Button button, double width ){
        button.setStyle("-fx-background-color: transparent;");
        button.setOnMouseEntered(e ->  button.setStyle("-fx-background-color: grey;") );
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent;") );
        button.setFont( Font.loadFont("file:src/gui/resources/IndieFlower.ttf", width / 5 ) );
        button.setEllipsisString("");
    }

    /**
     * Sets up the starting menu screen
     * @param stage - The stage to set up
     */
    private void setMenuScreen(Stage stage){
        BorderPane window = new BorderPane();
        Image img = new Image( getClass().getResourceAsStream("resources/light.jpg") );
        BackgroundImage BI = new BackgroundImage(img,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT );
        window.setBackground( new Background(BI) );
        Scene scene = new Scene( window );

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

        //the sizes for each of the option buttons
        //todo - bamboo widths: 59left 57right 53top 52bottom 456horizontal 402vertical
        double width = ( squareDim -  squareDim*((59.0 + 57.0) / 456.0) ) / 3.0 - 1;
        double height = ( squareDim - squareDim*((53.0 + 52.0) / 402.0) ) / 3.0;

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
        styleButton( about, width );
        setSize( about, width, height );

        Button help = new Button("Help");
        help.setOnAction(e -> setHelpScreen(stage));
        styleButton( help, width );
        setSize( help, width, height );

        Button start = new Button("Play");
        start.setOnAction(e -> setDifficultySelectionScreen(stage));
        styleButton( start, width );
        setSize( start, width, height );

        Button donate = new Button("Donate");
        //todo
        styleButton( donate, width );
        setSize( donate, width, height );

        Button quit = new Button("Quit");
        quit.setOnAction(e -> Platform.exit());
        styleButton( quit, width );
        setSize( quit, width, height );

        Image image = new Image( getClass().getResourceAsStream("resources/frame.png") );
        BackgroundImage optionsBackground = new BackgroundImage( image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize( 100, 100, true, true, true, false ) );  
        options.setBackground( new Background( optionsBackground ) );

        options.setPadding( new Insets( //so the buttons don't go over the bamboo stalks
                (53.0 / 402.0) * squareDim, (57.0 / 456.0) * squareDim,
                (52.0 / 402.0) * squareDim, (59.0 / 456.0) * squareDim ) );

        options.getChildren().addAll( about, option2, help, option4, start, option6, donate, option8, quit );
        window.setBottom( options );
        BorderPane.setAlignment( options, Pos.CENTER );
        BorderPane.setMargin( options, new Insets(
                ( stage.getHeight() - squareDim - title.getFitHeight() ) / 8, 0, 0, 0 ) );

        stage.setScene(scene);
    }

    /**
     * Sets up the help screen
     * @param stage - The screen to set up
     */
    private void setHelpScreen(Stage stage){
        //todo
        //stage.minWidthProperty().bind(scene.heightProperty());
        //stage.minHeightProperty().bind(scene.widthProperty());
    }

    /**
     * Sets up the about screen
     * @param stage - The screen to set up
     */
    private void setAboutScreen(Stage stage){
        //todo
        //stage.minWidthProperty().bind(scene.heightProperty());
        //stage.minHeightProperty().bind(scene.widthProperty());
    }

    /**
     * Selects a difficulty and sets filename based on that
     * @param stage - The stage to set the selection screen on
     */
    private void setDifficultySelectionScreen(Stage stage){
        VBox difficulties = new VBox();
        difficulties.setSpacing( 44 );
        difficulties.setAlignment( Pos.CENTER );
        Scene scene = new Scene( difficulties );

        Label topSpacing = new Label();
        Label bottomSpacing = new Label();

        Button superEasy = new Button("Super Easy");
        superEasy.setFont( Font.loadFont("file:src/gui/resources/IndieFlower.ttf", 30) );
        superEasy.setOnAction(e -> {
            try{
                this.filename = "super_easy";
                this.model = new SudokuModel(this.filename);
            }catch(FileNotFoundException fnfe){
                System.out.println(fnfe.getMessage());
                System.exit(-1);
            }
            this.model.addObserver(this);
            setPuzzleSelectionScreen(stage);
        });

        Button easy = new Button("Easy");
        easy.setFont( Font.loadFont("file:src/gui/resources/IndieFlower.ttf", 30) );
        easy.setOnAction(e -> {
            try{
                this.filename = "easy";
                this.model = new SudokuModel(this.filename);
            }catch(FileNotFoundException fnfe){
                System.out.println(fnfe.getMessage());
                System.exit(-1);
            }
            this.model.addObserver(this);
            setPuzzleSelectionScreen(stage);
        });

        Button normal = new Button("Normal");
        normal.setFont( Font.loadFont("file:src/gui/resources/IndieFlower.ttf", 30) );
        normal.setOnAction(e -> {
            try{
                this.filename = "normal";
                this.model = new SudokuModel(this.filename);
            }catch(FileNotFoundException fnfe){
                System.out.println(fnfe.getMessage());
                System.exit(-1);
            }
            this.model.addObserver(this);
            setPuzzleSelectionScreen(stage);
        });

        Button hard = new Button("Hard");
        hard.setFont( Font.loadFont("file:src/gui/resources/IndieFlower.ttf", 30) );
        hard.setOnAction(e -> {
            try{
                this.filename = "hard";
                this.model = new SudokuModel(this.filename);
            }catch(FileNotFoundException fnfe){
                System.out.println(fnfe.getMessage());
                System.exit(-1);
            }
            this.model.addObserver(this);
            setPuzzleSelectionScreen(stage);
        });

        Button extreme = new Button("Extreme");
        extreme.setFont( Font.loadFont("file:src/gui/resources/IndieFlower.ttf", 30) );
        extreme.setOnAction(e -> {
            try{
                this.filename = "extreme";
                this.model = new SudokuModel(this.filename);
            }catch(FileNotFoundException fnfe){
                System.out.println(fnfe.getMessage());
                System.exit(-1);
            }
            this.model.addObserver(this);
            setPuzzleSelectionScreen(stage);
        });

        difficulties.getChildren().addAll( topSpacing, superEasy, easy, normal, hard, extreme, bottomSpacing );

        stage.minWidthProperty().bind(scene.heightProperty());
        stage.minHeightProperty().bind(scene.widthProperty());
        stage.setScene( scene );
    }

    /**
     * Selects a specific sudoku puzzle from the selected difficulty
     * @param stage - The stage to set the puzzle selection screen on
     */
    private void setPuzzleSelectionScreen(Stage stage){
        //todo
        //stage.minWidthProperty().bind(scene.heightProperty());
        //stage.minHeightProperty().bind(scene.widthProperty());
        setGameScreen(stage);
    }

    /**
     * Sets up the sudoku game once a file and linenumber are selected
     * @param stage - The stage to set the game screen on
     */
    private void setGameScreen(Stage stage){
        this.errorPos = new int[2];
        this.errorPos[0] = 0;
        this.errorPos[1] = 0;

        BorderPane window = new BorderPane();
        Scene scene = new Scene(window);

        Text status = new Text( filename.substring(0, 1).toUpperCase() + filename.substring(1) + " selected");
        this.status = status;
        window.setTop(status);
        BorderPane.setAlignment(status, Pos.CENTER);

        TilePane puzzle = new TilePane();
        puzzle.setPrefColumns(9);
        this.puzzle = new HashMap<>();
        for(int r=0; r < 9; r++){
            this.puzzle.put(r, new ArrayList<>());
            for(int c=0; c < 9; c++){
                numButton newButton = new numButton(r, c, model.puzzle[r][c], model);
                puzzle.getChildren().add( newButton );
                this.puzzle.get(r).add(c, newButton);
            }
        }
        this.grid = puzzle;
        window.setCenter(puzzle);

        FlowPane options = new FlowPane();
        Button verify = new Button("Verify");
        verify.setOnAction(e -> model.isValid(true));
        Button hint = new Button("Hint");
        hint.setOnAction(e -> model.getHint());
        Button solve = new Button("Solve");
        solve.setOnAction(e -> {
            try {
                model.solve(true);
            }catch(FileNotFoundException fnfe){ model.textout = "The file was deleted"; }});
        Button restart = new Button("Restart");
        restart.setOnAction(e -> {
            //todo - do this
            this.model.textout = filename + " has been reset";
            this.model.announceChange();
        });
        Button newGame = new Button("New Game");
        newGame.setOnAction(e -> {
            //todo - do this
            //todo - also change the window title
            this.model.textout = ""; //todo - make this be something
            this.model.announceChange();
        });
        Button newDifficulty = new Button("New Difficulty");
        newDifficulty.setOnAction(e -> {
            //todo - do this as well, maybe open the window from init
            this.model.textout = ""; //todo - make this be something
            this.model.announceChange();
        });
        options.getChildren().addAll(verify, hint, solve, restart, newGame, newDifficulty);
        window.setBottom(options);
        BorderPane.setAlignment(options, Pos.CENTER);

        Text centering = new Text();
        window.setLeft(centering);
        BorderPane.setMargin(centering, new Insets(0, 11.25, 0, 0));

        stage.setScene(scene);
        stage.setMaxWidth(450);
        stage.setMinHeight(440);
        stage.setMaxHeight(500);
        stage.setResizable(false);
        switch(model.filename){
            case "super_easy":
                stage.setTitle("Super Easy Sudoku puzzle #" + model.lineNumber);
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
        Image img = new Image( getClass().getResourceAsStream("resources/light.jpg") );
        BackgroundImage BI = new BackgroundImage(img,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT );
        window.setBackground( new Background(BI) );

        stage.minWidthProperty().bind(scene.heightProperty());
        stage.minHeightProperty().bind(scene.widthProperty());
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
            setButtonBackground(puzzle.get(errorPos[0]).get(errorPos[1]), "white.png");
        }
//todo - get this to work
        if(model.textout.contains("Error")){
            recentError = true;
            System.arraycopy(model.pos, 0, this.errorPos, 0, 2);
            setButtonBackground(puzzle.get(errorPos[0]).get(errorPos[1]), "red.png");
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
