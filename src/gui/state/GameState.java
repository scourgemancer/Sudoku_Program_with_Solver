package gui.state;

import gui.SudokuGUI;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.SudokuModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This class sets up the GUI's main game page using the State pattern
 * The game page is where the user actually views and plays a sudoku game
 * @author Timothy Geary
 */
public class GameState extends State{
    /** The UI's connection to the model */
    private SudokuModel model;

    /** The label for the update method */
    private Text status;

    /** The selected difficulty formatted as a filename */
    public String difficulty;

    /** Lets the update function know if there's error highlighting to undo */
    private boolean recentError;
    private int[] errorPos;

    /** A 2d button matrix for the update method */
    private ArrayList< ArrayList<NumButton> > puzzle;

    /** The tilepane that holds the buttons */
    private TilePane grid;

    /** A private class for the buttons */
    private class NumButton extends Button{
        SudokuModel model;
        private int row;
        private int col;
        private int num;

        private NumButton(int row, int col, int num, SudokuModel model){
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

    @Override
    public void setPage(SudokuGUI gui, Stage stage){
        errorPos = new int[] {0,0};

        //todo - move difficulty into the sudoku model
        status = new Text( difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1) + " selected");

        TilePane puzzle = new TilePane();
        puzzle.setPrefColumns(9);
        this.puzzle = new ArrayList<>();
        for(int r=0; r < 9; r++){
            this.puzzle.add( new ArrayList<>() );
            for(int c=0; c < 9; c++){
                NumButton newButton = new NumButton(r, c, model.puzzle[r][c], model);
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
        //todo - setOnAction() for the check button
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
        restart.setOnAction(e -> nextPage( gui, "game" ));
        newGame.setOnAction(e -> nextPage( gui, "difficulty" ));
        menu.setOnAction(e -> nextPage( gui, "menu" ));
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
    public void nextPage(SudokuGUI gui, String name){

    }
}
