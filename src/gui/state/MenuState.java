package gui.state;

import gui.SudokuGUI;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This class sets up the GUI's menu page using the State pattern
 * The menu page just presents options for the user to move onto using the program
 * @author Timothy Geary
 */
public class MenuState extends State{
	/** Utility function to set the style of a main menu option button */
	private void styleOptionButton( Button button, double width, Stage stage ){ //todo - use sprites instead to look better
		if(button.getText().length() > 5){
			button.setFont( Font.loadFont( getClass().getResourceAsStream("resources/Indieflower.ttf"), width/5 ));
		}else{
            button.setFont( Font.loadFont( getClass().getResourceAsStream("resources/Indieflower.ttf"), width/4 ));
		}
		button.setEllipsisString("");

		//the rest is all for a 'hover-over' background color change
		button.setBackground( new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)) );
		button.setOnMouseEntered(e -> {
            Platform.runLater(() -> stage.getScene().setCursor( Cursor.HAND ));
			button.setBackground( new Background(
					new BackgroundFill(Color.valueOf("#F4F9FF"), new CornerRadii(5.0), Insets.EMPTY)) );
		});
		button.setOnMouseExited(e -> {
            Platform.runLater(() -> stage.getScene().setCursor(Cursor.DEFAULT));
			button.setBackground(new Background(
					new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
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
		BorderPane.setMargin( title, new Insets( 0.05 * gui.stage.getHeight(), 0, 0, 0 ) );
		window.setTop( title );
		BorderPane.setAlignment( title, Pos.CENTER );

		TilePane options = new TilePane();
		options.setPrefColumns( 3 );
		double squareDim = ( gui.stage.getHeight() - title.getFitHeight() ) * (35.0/48.0);
		setSize( options, squareDim, squareDim );
		options.setHgap( gui.stage.getHeight() / 110.0 );
		options.setVgap( gui.stage.getWidth() / 110.0 );

		//the sizes for each of the option buttons
		//todo double width = squareDim * (340.0 / 456.0) / 3.1;       FIGURE OUT WHAT THIS MEANS
		double width = squareDim * (340.0 / 456.0) / 3.119;
		double height = squareDim * ( (297.0 / 402.0) / 3.1 ) - (gui.stage.getHeight() / 100)*3 + 2;

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
		about.setOnAction(e -> nextPage(gui, "about"));
		styleOptionButton( about, width, gui.stage );
		setSize( about, width, height );

		Button help = new Button("Help");
		help.setOnAction(e -> nextPage(gui, "help"));
		styleOptionButton( help, width, gui.stage );
		setSize( help, width, height );

		Button start = new Button("Play");
		start.setOnAction(e -> nextPage(gui, "difficulty"));
		styleOptionButton( start, width, gui.stage );
		setSize( start, width, height );

		Button donate = new Button("Support");
		donate.setOnAction(e -> gui.openWebpage( "https://www.paypal.me/TimGeary" ) );
		styleOptionButton( donate, width, gui.stage );
		setSize( donate, width, height );

		Button quit = new Button("Quit");
		quit.setOnAction(e -> Platform.exit());
		styleOptionButton( quit, width, gui.stage );
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
				( gui.stage.getHeight() - squareDim - title.getFitHeight() ) / 8, 0, 0, 0 ) );

		gui.stage.setTitle( "Sudoku" );
		gui.stage.setScene(scene);
	}

    @Override
    public void nextPage(SudokuGUI gui, String name){
		switch(name){
			case "about":      gui.setState(new AboutState());               break;
			case "help":       gui.setState(new HelpState());                break;
			case "difficulty": gui.setState(new DifficultySelectionState()); break;
			case "support":    gui.setState(new DonateState());              break;
		}
    }
}
