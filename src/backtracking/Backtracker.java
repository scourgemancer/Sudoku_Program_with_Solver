package backtracking;

import java.util.Optional;

/**
 * This class implements the recursive backtracking algorithm that'll be
 * used to solve the sudokus. It has a solver that can take a valid
 * configuration and return a solution, provided that one exists.
 * @author Timothy Geary
 */
public class Backtracker{
    /**
     * Finds a solution to the provided configuration, if one exists.
     * @param config A valid configuration
     * @return A solution config, or null if there is no solution
     */
    public Optional<Configuration> solve(Configuration config){
        if(config.isGoal()){
            return Optional.of(config);
        }else{
            //todo - try adding an optimize step here
            //todo - maybe even combine isGoal with optimize if I need to check for zeroes again
            for(Configuration child : config.getSuccessors()){
                if(child.isValid()){    //todo - try threading for each valid successor
                    Optional<Configuration> sol = solve(child);
                    if(sol.isPresent()){
                        return sol;
                    }
                }
            }
        }
        return Optional.empty();
    }
}