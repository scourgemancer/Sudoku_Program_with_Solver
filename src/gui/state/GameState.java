package gui.state;

import gui.SudokuGUI;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This class sets up the GUI's main game page using the State pattern
 * The game page is where the user actually views and plays a sudoku game
 * @author Timothy Geary
 */
public class GameState extends State{
    /** A 2d button matrix for the update method */
    private ArrayList< ArrayList<NumButton> > puzzle;

    /** The tilepane that holds the buttons */
    private TilePane grid;

    @Override
    public void setPage(SudokuGUI gui){
        gui.errorPos = new int[] {0,0};

        //todo - move difficulty into the sudoku model
        gui.status = new Text( gui.difficulty.substring(0, 1).toUpperCase() + gui.difficulty.substring(1) + " selected");

        TilePane puzzle = new TilePane();
        puzzle.setPrefColumns(9);
        this.puzzle = new ArrayList<>();
        for(int r=0; r < 9; r++){
            this.puzzle.add( new ArrayList<>() );
            for(int c=0; c < 9; c++){
                NumButton newButton = new NumButton(r, c, gui.model.puzzle[r][c], gui.model);
                puzzle.getChildren().add( newButton );
                this.puzzle.get(r).add(c, newButton);
            }
        }
        this.grid = puzzle;
        Image img = new Image( getClass().getResourceAsStream("resources/gameFrameNoLines.png") );
        BackgroundImage BI = new BackgroundImage( img,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize( gui.stage.getWidth(), 100, false, true, true, false ) );
        puzzle.setBackground( new Background(BI) );

        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        Button check = new Button("Check");
        Button hint = new Button("Hint");
        Button solve = new Button("Solve");
        //add the functionality of the buttons
        undo.setOnAction(e -> gui.model.undo());
        redo.setOnAction(e -> gui.model.redo());
        //todo - setOnAction() for the check button
        hint.setOnAction(e -> gui.model.getHint());
        solve.setOnAction(e -> {
            try {
                gui.model.solve(true);
            }catch(FileNotFoundException fnfe){ gui.model.textout = "The file was deleted"; }
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

        VBox page = new VBox( gui.status, puzzle, features, functions );
        Scene scene = new Scene(page);
        setBackground( page, "light.jpg" );

        switch( gui.model.filename.substring(0, gui.model.filename.length() - 4).toLowerCase() ){
            case "super_easy":
                gui.stage.setTitle( gui.stage.getTitle() + " - Super Easy Puzzle #" + gui.model.lineNumber);
                break;
            case "easy":
                gui.stage.setTitle("Easy Sudoku puzzle #" + gui.model.lineNumber);
                break;
            case "normal":
                gui.stage.setTitle("Normal Sudoku puzzle #" + gui.model.lineNumber);
                break;
            case "hard":
                gui.stage.setTitle("Hard Sudoku puzzle #" + gui.model.lineNumber);
                break;
            case "extreme":
                gui.stage.setTitle("I'm sorry, it's Sudoku puzzle #" + gui.model.lineNumber + ", good luck");
                break;
        }

        gui.stage.setScene(scene);
    }

    @Override
    public void nextPage(SudokuGUI gui, String name){
        switch(name){
            case "menu":                gui.setState(new MenuState());                break;
            case "difficultyselection": gui.setState(new DifficultySelectionState()); break;
            case "puzzleselection":     gui.setState(new PuzzleSelectionState());     break;
        }
    }
}
