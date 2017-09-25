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

    @Override
    public void setPage(SudokuGUI gui){
        gui.errorPos = new int[] {0,0};

        gui.status = new Text(gui.difficulty.substring(0, 1).toUpperCase() + gui.difficulty.substring(1) + " selected");

        //Sets up the actual game's squares and background
        double gameWidth = (19.0/24.0) * gui.stage.getWidth();

        ImageView background = new ImageView(
                new Image( getClass().getResourceAsStream("resources/gameFrameNoLinesResized.png") ));
        background.setFitWidth( gameWidth );
        background.setFitHeight( gameWidth );

        TilePane innerSquares = new TilePane();
        TilePane upperLeft = new TilePane();
        TilePane upperCenter = new TilePane();
        TilePane upperRight = new TilePane();
        TilePane centerLeft = new TilePane();
        TilePane center = new TilePane();
        TilePane centerRight = new TilePane();
        TilePane lowerLeft = new TilePane();
        TilePane lowerCenter = new TilePane();
        TilePane lowerRight = new TilePane();

        innerSquares.setPrefColumns(3);
        upperLeft.setPrefColumns(3);
        upperCenter.setPrefColumns(3);
        upperRight.setPrefColumns(3);
        centerLeft.setPrefColumns(3);
        center.setPrefColumns(3);
        centerRight.setPrefColumns(3);
        lowerLeft.setPrefColumns(3);
        lowerCenter.setPrefColumns(3);
        lowerRight.setPrefColumns(3);

        innerSquares.getChildren().addAll( upperLeft, upperCenter, upperRight,
                                           centerLeft,   center,   centerRight,
                                           lowerLeft, lowerCenter, lowerRight );
        puzzle = new ArrayList<>();
        for(int r=0; r < 9; r++){
            puzzle.add(new ArrayList<>());
            for(int c=0; c < 9; c++){
                NumButton newButton = new NumButton(r, c, gui.model.puzzle[r][c], gui.model);
                setSize( newButton, gui.stage.getWidth()/13.2, gui.stage.getWidth()/12.7 );
                newButton.setBackground( new Background(new BackgroundFill(Color.TRANSPARENT,
                        CornerRadii.EMPTY, Insets.EMPTY)) );
                newButton.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"),
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                if(r < 3){//top three
                    if(c < 3){
                        upperLeft.getChildren().add( newButton );
                    }else if(c < 6){
                        upperCenter.getChildren().add( newButton );
                    }else{
                        upperRight.getChildren().add( newButton );
                    }
                }else if(r < 6){//center three
                    if(c < 3){
                        centerLeft.getChildren().add( newButton );
                    }else if(c < 6){
                        center.getChildren().add( newButton );
                    }else{
                        centerRight.getChildren().add( newButton );
                    }
                }else{//bottom three
                    if(c < 3){
                        lowerLeft.getChildren().add( newButton );
                    }else if(c < 6){
                        lowerCenter.getChildren().add( newButton );
                    }else{
                        lowerRight.getChildren().add( newButton );
                    }
                }
                puzzle.get(r).add(c, newButton);
            }
        }
        innerSquares.setPadding(new Insets(0, gui.stage.getWidth()/9, 0, gui.stage.getWidth()/9));
        innerSquares.setHgap( gui.stage.getWidth()/100 );
        innerSquares.setVgap( gui.stage.getHeight()/100 );
        innerSquares.setAlignment(Pos.CENTER);

        ImageView gameBorder = new ImageView(
                new Image( getClass().getResourceAsStream("resources/gameFrameBorderResized.png") ));
        gameBorder.setFitWidth( gameWidth );
        gameBorder.setFitHeight( gameWidth );

        //todo - shifted order to help space squares: StackPane sudokuSquare = new StackPane(background, innerSquares, gameBorder);
        StackPane sudokuSquare = new StackPane(background, gameBorder, innerSquares);
        setSize(sudokuSquare, (12*gui.stage.getWidth()/12.5), (10*gui.stage.getWidth()/12.5));


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
