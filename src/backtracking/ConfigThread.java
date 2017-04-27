package backtracking;

import java.util.Optional;

/**
 * This is a thread that starts with a Sudoku Configuration and starts
 * a new thread for each valid successor of its configuration, with a
 * solution, if found, replacing the optional passed in the constructor.
 * @author Timothy Geary
 */
public class ConfigThread extends Thread{
    public static volatile Optional sol;

    public Configuration config;

    public ConfigThread(Configuration configuration, Optional solution){ config = configuration; sol = solution; }
    public ConfigThread(Configuration child){ config = child; }

    public void run(){
        if(sol.isPresent()){ return; } //If a solution has been found, end all other threads still looking
        //todo - experiment w/ an optimize step here
        ConfigThread[] threads = new ConfigThread[9];
        byte i = 0;
        for(Configuration child : config.getSuccessors()){
            if(child.isGoal()){ MultiThreadedBacktracker.solution = Optional.of(child); return; }
            if(child.isValid()){
                ConfigThread childThread = new ConfigThread(child);
                childThread.start();
                threads[i] = childThread;
            }i++;
        }
        for(ConfigThread thread : threads){
            try{ if(thread != null) thread.join(); }
            catch(InterruptedException ie){ System.out.println(ie.getMessage()); }
        }
    }
}
