package backtracking;

import java.util.Optional;

/**
 * This class implements the recursive backtracking algorithm that'll be
 * used to solve the sudokus. It has a solver that can take a valid
 * configuration and return a solution, provided that one exists.
 * @author Timothy Geary
 */
public class MultiThreadedBacktracker{
    public static volatile Optional solution;

    /**
     * Finds a solution to the provided configuration, if one exists.
     * @param config A valid configuration
     * @return A solution config, or null if there is no solution
     */
    public Optional<Configuration> solve(Configuration config){
        solution = Optional.empty();
        ConfigThread solving = new ConfigThread( config, solution );
        solving.start();
        try{ solving.join();
        }catch(InterruptedException ie){ System.out.println(ie.getMessage()); }
        while(solving.isAlive()){
            try{ solving.join();
            }catch(InterruptedException ie){ System.out.println(ie.getMessage()); }
        }
        return solution;
    }
}
