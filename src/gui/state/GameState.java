package gui.state;

import gui.SudokuGUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
    private TilePane squares;

    @Override
    public void setPage(SudokuGUI gui){
        gui.errorPos = new int[] {0,0};

        gui.status = new Text(gui.difficulty.substring(0, 1).toUpperCase() + gui.difficulty.substring(1) + " selected");

        //Sets up the actual game's squares and background
        ImageView background = new ImageView(
                new Image( getClass().getResourceAsStream("resources/gameFrameNoLines.png") ));
        background.setPreserveRatio( true );
        background.setFitWidth( 19.0 * gui.stage.getWidth() / 24.0 );

        squares = new TilePane();
        squares.setPrefColumns(9);
        puzzle = new ArrayList<>();
        for(int r=0; r < 9; r++){
            puzzle.add( new ArrayList<>() );
            for(int c=0; c < 9; c++){
                NumButton newButton = new NumButton(r, c, gui.model.puzzle[r][c], gui.model);
                setSize( newButton, gui.stage.getWidth()/12.5, gui.stage.getWidth()/12.5 );
                newButton.setBackground( new Background(new BackgroundFill(Color.TRANSPARENT,
                        CornerRadii.EMPTY, Insets.EMPTY)) );
                newButton.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"),
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                squares.getChildren().add( newButton );
                puzzle.get(r).add(c, newButton);
            }
        }
        squares.setPadding(new Insets(0, gui.stage.getWidth()/9, 0, gui.stage.getWidth()/9));
        squares.setAlignment(Pos.CENTER);

        StackPane sudokuSquare = new StackPane(background, squares);//todo - finish updating

        //Creates the buttons at the bottom of the screen
        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        Button check = new Button("Check");
        Button hint = new Button("Hint");
        Button solve = new Button("Solve");
        HBox features = new HBox( undo, redo, check, hint, solve );

        undo.setOnAction(e -> gui.model.undo());
        redo.setOnAction(e -> gui.model.redo());
        //todo - setOnAction() for the check button
        hint.setOnAction(e -> gui.model.getHint());
        solve.setOnAction(e -> {
            try {
                gui.model.solve(true);
            }catch(FileNotFoundException fnfe){ gui.model.textout = "The file was deleted"; }
        });

        Button restart = new Button("Restart");
        Button newGame = new Button("New Puzzle");
        Button menu = new Button("Menu");

        restart.setOnAction(e -> nextPage( gui, Page.GAME ));
        newGame.setOnAction(e -> nextPage( gui, Page.DIFFICULTY ));
        menu.setOnAction(e -> nextPage( gui, Page.MENU ));
        HBox functions = new HBox( restart, newGame, menu );

        //Adds everything together to complete the scene
        VBox page = new VBox( gui.status, sudokuSquare, features, functions );
        Scene scene = new Scene(page);
        setBackground( page, "light.jpg" );
        page.setAlignment(Pos.CENTER);

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
    public void nextPage(SudokuGUI gui, Page name){
        switch(name){
            case MENU:       gui.setState(new MenuState());                break;
            case DIFFICULTY: gui.setState(new DifficultySelectionState()); break;
            case PUZZLE:     gui.setState(new PuzzleSelectionState());     break;
        }
    }
}
