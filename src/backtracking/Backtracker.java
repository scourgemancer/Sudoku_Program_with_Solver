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
            return Optional.of(config);                 //TODO - FIGURE OUT WHY IT USED TO MAKE 'DONUT HOLES'
        }else{                                          //TODO - THEN GET RID OF THE CHECK FOR ZEROES IN isValid()
            for(Configuration child : config.getSuccessors()){
                if(child.isValid()){
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