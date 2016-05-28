package ptui;

import model.SudokuModel;

import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

/**
 * This is the plain text UI's view class, it displays what the
 * user sees and is controlled by the controller(ControllerPTUI).
 * @author Timothy Geary
 */
public class SudokuPTUI implements Observer{

    private SudokuModel model;

    /**
     * Creates the model and initializes the view.
     * @throws FileNotFoundException if file not found
     */
    public SudokuPTUI(String difficulty) throws FileNotFoundException{
        try{
            this.model = new SudokuModel(difficulty);
        }catch (FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
            System.exit(-1);
        }
        this.model.addObserver(this);
    }

    public SudokuModel getModel(){ return this.model; }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("\n" + this.model.textout);
        System.out.print(this.model.toString());
        System.out.print("> ");
    }
}