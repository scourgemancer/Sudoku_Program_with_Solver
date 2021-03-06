package gui.state;

import gui.SudokuGUI;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This class sets up the GUI's menu page using the State pattern
 * The menu page just presents options for the user to move onto using the program
 * @author Timothy Geary
 */
public class MenuState extends State{
    private double spriteWidth;
    private double spriteHeight;
    private ImageView optionsBackground;

	/** Utility function to set the style of a main menu option button */
	private void styleOptionButton( Button button, double width, int index, Stage stage ){
		if(button.getText().length() > 5){
			button.setFont( Font.loadFont( getClass().getResourceAsStream("resources/Indieflower.ttf"), width/5 ));
		}else{
            button.setFont( Font.loadFont( getClass().getResourceAsStream("resources/Indieflower.ttf"), width/4 ));
		}
		button.setEllipsisString("");

		//the rest is all for a 'hover-over' background image and cursor change
		button.setBackground( new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)) );
		button.setOnMouseEntered(e -> {
            Platform.runLater(() -> stage.getScene().setCursor( Cursor.HAND ));
            optionsBackground.setViewport(new Rectangle2D(0, (spriteHeight/10)*(index), spriteWidth, spriteHeight/10));
        });
		button.setOnMouseExited(e -> {
            Platform.runLater(() -> stage.getScene().setCursor(Cursor.DEFAULT));
            optionsBackground.setViewport(new Rectangle2D(0, 0, spriteWidth, spriteHeight/10));
		});
	}

    @Override
	public void setPage(SudokuGUI gui){
		BorderPane window = new BorderPane();
		Scene scene = new Scene( window );
		setBackground( window, "light.jpg" );

		Image titleImage = new Image( getClass().getResourceAsStream("resources/sudoku_title.png") );
		ImageView title = new ImageView( titleImage );
		title.setPreserveRatio( true );
		title.setFitWidth( 2.0 * gui.stage.getWidth() / 3.0 );
		BorderPane.setMargin( title, new Insets( gui.stage.getHeight()/38, 0, 0, 0 ) );
		window.setTop( title );
		BorderPane.setAlignment( title, Pos.TOP_CENTER );

		TilePane options = new TilePane();
		options.setPrefColumns( 3 );
		double squareDim = ( gui.stage.getHeight() - title.getFitHeight() ) * (35.0/48.0);
		setSize( options, squareDim, squareDim );

		//the sizes for each of the option buttons
		double width = squareDim * (341.0 / 456.0) / 3.119;
		double height = squareDim * ( (298.0 / 402.0) / 2.72 ) - (gui.stage.getHeight() / 100)*3;

		//blank squares
		Label option2 = new Label("");
		Label option4 = new Label("");
		Label option6 = new Label("");
		Label option8 = new Label("");
		setSize( option2, width, height );
		setSize( option4, width, height );
		setSize( option6, width, height );
		setSize( option8, width, height );

		Button about = new Button("About");
		about.setOnAction(e -> nextPage(gui, Page.ABOUT));
		styleOptionButton( about, width, 1, gui.stage );
		setSize( about, width, height );

		Button help = new Button("Help");
		help.setOnAction(e -> nextPage(gui, Page.HELP));
		styleOptionButton( help, width, 3, gui.stage );
		setSize( help, width, height );

		Button start = new Button("Play");
		start.setOnAction(e -> nextPage(gui, Page.DIFFICULTY));
		styleOptionButton( start, width, 5, gui.stage );
		setSize( start, width, height );

		Button donate = new Button("Support");
		donate.setOnAction(e -> nextPage(gui, Page.DONATE) );
		styleOptionButton( donate, width, 7, gui.stage );
		setSize( donate, width, height );

		Button quit = new Button("Quit");
		quit.setOnAction(e -> Platform.exit());
		styleOptionButton( quit, width, 9, gui.stage );
		setSize( quit, width, height );

		//Actually put all of the buttons together and add a background image behind them
		StackPane optionsStack = new StackPane();

        spriteWidth = 457;
        spriteHeight = 4030;
		optionsBackground = new ImageView( new Image( getClass().getResourceAsStream("resources/menuSprite.png") ) );
		optionsBackground.setViewport(new Rectangle2D(0, 0, spriteWidth, spriteHeight/10));
		optionsBackground.setFitWidth( width*3 + ((57.0 / 456.0) * squareDim) + ((59.0 / 456.0) * squareDim) );
		optionsBackground.setFitHeight( height*3 + ((51.0 / 402.0) * squareDim) + ((52.0 / 402.0) * squareDim) );

		options.setPadding( new Insets( //so the buttons don't go over the bamboo stalks
				0,//(35.0 / 402.0) * squareDim, //top
				(57.0 / 456.0) * squareDim, //right
				0,//(52.0 / 402.0) * squareDim, //bottom
				(65.0 / 456.0) * squareDim ) ); //left
		options.getChildren().addAll( about, option2, help, option4, start, option6, donate, option8, quit );

		optionsStack.getChildren().addAll( optionsBackground, options );
		window.setBottom( optionsStack );
        StackPane.setMargin(options, new Insets(gui.stage.getHeight()/8, 0, 0, 0));
        StackPane.setMargin(optionsBackground, new Insets(0, 0, gui.stage.getHeight()/10, 0));

		gui.stage.setTitle( "Sudoku" );
		gui.stage.setScene(scene);
    }

    @Override
    public void nextPage(SudokuGUI gui, Page name){
		switch(name){
			case ABOUT:      gui.setState(new AboutState());               break;
			case HELP:       gui.setState(new HelpState());                break;
			case DIFFICULTY: gui.setState(new DifficultySelectionState()); break;
			case DONATE:     gui.setState(new DonateState());              break;
		}
    }
}
