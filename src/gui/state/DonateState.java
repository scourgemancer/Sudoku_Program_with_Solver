package gui.state;

import gui.SudokuGUI;
import javafx.stage.Stage;

/**
 * This class sets up the GUI's donation payment option page using the State pattern
 * The donation page just explains who they're donating to and provides the options to donate
 * @author Timothy Geary
 */
public class DonateState extends State{
    @Override
	public void setPage(SudokuGUI gui, Stage stage){ //TODO - Allow for GooglePay, Bitcoin, Dogecoin, etc
        gui.openWebpage( "https://www.paypal.me/TimGeary" );
    	nextPage( gui, stage, "menu" );
        //todo - figure out if this just opens the webpage or has an in-gui selection screen
	}

    @Override
    public void nextPage(SudokuGUI gui, Stage stage, String name){
		//todo - decide how to implement
    }
}
