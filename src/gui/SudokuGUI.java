package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.SudokuModel;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
//todo - also have the option of solving the user's own puzzles they input themselves
//todo - have an option to submit an answer and set off fireworks if it's correct
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
                case 1:
                    Image pillarImg = new Image(getClass().getResourceAsStream("resources/pillarX.png"));
                    ImageView pillarIcon = new ImageView(pillarImg);
                    this.setGraphic(pillarIcon);
                    setButtonBackground(this, "white.png");
                    break;
                case 2:
                    Image beamImg = new Image(getClass().getResourceAsStream("resources/beam.png"));
                    ImageView beamIcon = new ImageView(beamImg);
                    this.setGraphic(beamIcon);
                    setButtonBackground(this, "yellow.png");
                    break;
                case 3:
                    Image laserImg = new Image(getClass().getResourceAsStream("resources/laser.png"));
                    ImageView laserIcon = new ImageView(laserImg);
                    this.setGraphic(laserIcon);
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
                    break;
                default:
                    Image whiteImg = new Image(getClass().getResourceAsStream("resources/white.png"));
                    ImageView whiteIcon = new ImageView(whiteImg);
                    this.setGraphic(whiteIcon);
                    setButtonBackground(this, "white.png");
            }
            this.setOnAction(e -> this.pressed());
        }

        private void pressed() {
            //todo - increase num and cycle to empty
        }
    }

    /**
     * A private utility function for setting the background
     * of a button to an image in the resources subdirectory.
     * @param button the button who's background will be set
     * @param bgImgName the name of the image file to be used as a background
     */
    private void setButtonBackground(Button button, String bgImgName){
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image( getClass().getResource("resources/" + bgImgName).toExternalForm() ),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        button.setBackground(background);
    }

    @Override
    public void init() throws Exception{
        try{
            Parameters params = getParameters();
            this.filename = params.getRaw().get(0);
            //todo - choose dificulty window here
            this.model = new SudokuModel(this.filename);
        }catch(FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
            System.exit(-1);
        }
        this.model.addObserver(this);
    }

    @Override
    public void start(Stage stage) throws Exception{
        this.errorPos = new int[2];
        this.errorPos[0] = 0;
        this.errorPos[1] = 0;

        BorderPane window = new BorderPane();
        Scene cena = new Scene(window);

        Path path = Paths.get(this.filename);
        String file = path.getFileName().toString();
        Text status = new Text( file + " loaded");
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
        verify.setOnAction(e -> model.isValid());
        Button hint = new Button("Hint");
        hint.setOnAction(e -> model.getHint());
        Button solve = new Button("Solve");
        solve.setOnAction(e -> {
            try {
                model.solve();// todo - Platform.runLater(Runnable r), runs operation as a thread in the background
            }catch(FileNotFoundException fnfe){ model.textout = "The file was deleted"; }});
        Button restart = new Button("Restart");
        restart.setOnAction(e -> {
            //todo - do this
            this.model.textout = file + " has been reset";
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

        stage.setScene(cena);
        stage.setMaxWidth(450);
        stage.setMinHeight(440);
        stage.setMaxHeight(500);
        stage.setResizable(false);
        switch(model.filename){
            case "super_easy.txt":
                stage.setTitle("Super Easy Sudoku puzzle #" + model.lineNumber);
                break;
            case "easy.txt":
                stage.setTitle("Easy Sudoku puzzle #" + model.lineNumber);
                break;
            case "normal.txt":
                stage.setTitle("Normal Sudoku puzzle #" + model.lineNumber);
                break;
            case "hard.txt":
                stage.setTitle("Hard Sudoku puzzle #" + model.lineNumber);
                break;
            case "extreme.txt":
                stage.setTitle("I'm sorry, it's Sudoku puzzle #" + model.lineNumber + ", good luck");
                break;
        }
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
