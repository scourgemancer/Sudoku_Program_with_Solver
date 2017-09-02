package gui.state;

import gui.SudokuGUI;

/**
 * This class sets up the GUI's donation payment option page using the State pattern
 * The donation page just explains who they're donating to and provides the options to donate
 * @author Timothy Geary
 */
public class DonateState extends State{
    @Override
	public void setPage(SudokuGUI gui){ //TODO - Allow for GooglePay, Bitcoin, Dogecoin, etc
        gui.openWebpage( "https://www.paypal.me/TimGeary" );
    	nextPage( gui, Page.MENU );
        //todo - figure out if this just opens the webpage or has an in-gui selection screen
	}

    @Override
    public void nextPage(SudokuGUI gui, Page name){
        switch(name){
            case MENU: gui.setState(new MenuState()); break;
        }
    }
}
