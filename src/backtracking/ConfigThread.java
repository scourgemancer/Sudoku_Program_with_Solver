package backtracking;

import java.util.Optional;

import static java.util.Optional.of;

/**
 * This is a thread that starts with a Sudoku Configuration and starts
 * a new thread for each valid successor of its configuration, with a
 * solution, if found, replacing the optional passed in the constructor.
 * @author Timothy Geary
 */
public class ConfigThread extends Thread{
    private static volatile Optional<ConfigThread> sol;

    private Configuration config;

    private ConfigThread(Configuration child){ config = child; }
    public ConfigThread(Configuration configuration, Optional<ConfigThread> solution){
        config = configuration;
        sol = solution;
    }

    public void run(){
        if(sol.isPresent()){ return; } //If a solution has been found, end all other threads still looking
        //todo - experiment w/ an optimize step here
        for(Configuration child : config.getSuccessors()){
            if(child.isGoal()){
                //sol = Optional<ConfigThread>.of(child);
                sol.notify();
            }else if(child.isValid()){
                ConfigThread childThread = new ConfigThread(child);
                childThread.start();
            }
        }
    }
}
