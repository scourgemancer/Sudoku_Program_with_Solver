package ptui;

import model.SudokuModel;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is the plain text UI's controller class, it takes the model from the
 * view(SudokuPTUI) so that run() can perform the operations that are input.
 * @author Timothy Geary
 */
public class ControllerPTUI{

    private SudokuModel model;

    /**
     * Creates the model and initializes the view.
     * @param model The sudoku model being used
     */
    public ControllerPTUI(SudokuModel model){
        this.model = model;
    }

    /** Runs the main loop, performing operations from input */
    public void run() throws FileNotFoundException{
        Scanner in;
        String[] input;
        int r;
        int c;
        int num;

        model.printIt();

        in = new Scanner(System.in);

        while(true){
            input = in.nextLine().trim().split(" ");
            if(input[0].length() != 0){
                switch(input[0].charAt(0)){
                    case 'v': //verify
                        model.isValid(true);
                        break;
                    case 'a': //addNumber
                        if(input.length == 4){
                            r = Integer.parseInt(input[1])-1;
                            c = Integer.parseInt(input[2])-1;
                            num = Integer.parseInt(input[3]);
                        }else{
                            System.out.println("Incorrect usage, try 'h' for help");
                            break;
                        }
                        model.addNumber(r, c, num, true);
                        break;
                    case 'r': //removeNumber
                        if(input.length == 3){
                            r = Integer.parseInt(input[1])-1;
                            c = Integer.parseInt(input[2])-1;
                        }else{
                            System.out.println("Incorrect usage, try 'h' for help");
                            break;
                        }
                        model.removeNumber(r, c);
                        break;
                    case 'h': //print help
                        model.setHelpMsg();
                        break;
                    case 'd': //display puzzle
                        model.printIt();
                        break;
                    case 's': //solve
                        model.solve();
                        break;
                    case 'c': //clue
                        model.getHint();
                        break;
                    case 'g': //guess
                        model.isGoal(true);
                        break;
                    case 'q': //quit
                        return;
                    default:
                        System.out.print("Unrecognized command: ");
                        for(String wrong: input){
                            System.out.print(wrong + " ");
                        }
                        System.out.println();
                        break;
                }
            }
        }
    }
}