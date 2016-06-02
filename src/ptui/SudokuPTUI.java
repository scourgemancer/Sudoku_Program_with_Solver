package ptui;

import model.SudokuModel;

import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

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
    public SudokuPTUI() throws FileNotFoundException{
        Scanner in = new Scanner(System.in);
        String difficulty;
        boolean chosen = false;
        do{
            System.out.println("Difficulty options are super_easy, easy, normal, hard, and extreme");
            System.out.print("\nChoose one to play: ");
            difficulty = in.nextLine().toLowerCase();
            if(difficulty.equals("super_easy") || difficulty.equals("easy") || difficulty.equals("normal") ||
                    difficulty.equals("hard") || difficulty.equals("extreme")){
                chosen = true;
            }else{
                System.out.println("Error: Not a valid option, try again");
            }
        }while(!chosen);
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