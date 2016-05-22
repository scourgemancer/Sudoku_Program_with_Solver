package backtracking;

import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * This times how long it takes for the program to solve the puzzle.
 * @author Timothy Geary
 */
public class SudokuSolver{
    public static void main(String[] args) throws FileNotFoundException{
        if(args.length != 2){
            System.out.println("Usage: java SudokuSolver sudokuFile.txt line#");
        }else{
            Configuration init = new SudokuConfig(args[0], Integer.parseInt(args[1]));
            Backtracker bt = new Backtracker();

            double start = System.currentTimeMillis();

            Optional<Configuration> sol = bt.solve(init);

            System.out.println("Solved in: " + (System.currentTimeMillis() - start)/1000.0 + " seconds.");

            if(sol.isPresent()){
                System.out.println("Solution:\n" + sol.get());
            }else{
                System.out.println("No solution found.");
            }
        }
    }
}