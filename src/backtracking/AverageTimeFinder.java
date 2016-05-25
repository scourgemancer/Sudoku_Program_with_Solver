package backtracking;

import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * This solves each of the 10000 puzzles for each difficulty and times
 * how long it takes to solve them all to find the average time taken.
 * @author Timothy Geary
 */
public class AverageTimeFinder{
    public static void main(String[] args) throws FileNotFoundException{
        System.out.println("Average times:\n");

        //find the average time to solve all 10000 super easy problems
        double superEasyTime = 0;
        for(int line=1; line < 1001; line++){
            double start = System.currentTimeMillis();

            Configuration init = new SudokuConfig("super_easy.txt", line);
            Backtracker bt = new Backtracker();
            Optional<Configuration> sol = bt.solve(init);

            superEasyTime += (System.currentTimeMillis() - start) / 1000.0;
        }

        System.out.println("Super easy: " + superEasyTime/10000 + " seconds.\n");

        //find the average time to solve all 10000 easy problems
        double easyTime = 0;
        for(int line=1; line < 1001; line++){
            double start = System.currentTimeMillis();

            Configuration init = new SudokuConfig("easy.txt", line);
            Backtracker bt = new Backtracker();
            Optional<Configuration> sol = bt.solve(init);

            easyTime += (System.currentTimeMillis() - start) / 1000.0;
        }

        System.out.println("Easy: " + easyTime/10000 + " seconds.\n");

        //find the average time to solve all 10000 normal problems
        double normalTime = 0;
        for(int line=1; line < 1001; line++){
            double start = System.currentTimeMillis();

            Configuration init = new SudokuConfig("normal.txt", line);
            Backtracker bt = new Backtracker();
            Optional<Configuration> sol = bt.solve(init);

            normalTime += (System.currentTimeMillis() - start) / 1000.0;
        }

        System.out.println("Normal: " + normalTime/10000 + " seconds.\n");

        //find the average time to solve all 10000 hard problems
        double hardTime = 0;
        System.out.println("Hard:");
        for(int line=1; line < 1001; line++){
            if(line%100 == 0) System.out.println("\t" + line + " done so far");
            double start = System.currentTimeMillis();

            Configuration init = new SudokuConfig("hard.txt", line);
            Backtracker bt = new Backtracker();
            Optional<Configuration> sol = bt.solve(init);

            hardTime += (System.currentTimeMillis() - start) / 1000.0;
        }

        System.out.println("Hard: " + hardTime/10000 + " seconds.\n");

        //find the average time to solve all 10000 extreme problems
        double extremeTime = 0;
        System.out.println("Extreme:");
        for(int line=1; line < 1001; line++){ //strap yourselves in folks
            if(line%100 == 0) System.out.println("\t" + line + " done so far");
            double start = System.currentTimeMillis();

            Configuration init = new SudokuConfig("extreme.txt", line);
            Backtracker bt = new Backtracker();
            Optional<Configuration> sol = bt.solve(init);

            extremeTime += (System.currentTimeMillis() - start) / 1000.0;
        }

        System.out.println("Extreme: " + extremeTime/10000 + " seconds.\n");
    }
}