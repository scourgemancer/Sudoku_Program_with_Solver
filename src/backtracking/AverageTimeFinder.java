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
        double bestSuperEasy = 100000000000000000000.0;
        double worstSuperEasy = 0;
        System.out.println("Super easy:");
        for(int line=1; line < 10001; line++){
            if(line%1000 == 0 && line != 10000){
                System.out.println("\t" + line / 100 + "% done");
                System.out.println("\t\t" + superEasyTime / line + " seconds each so far");
                System.out.println("\t\t" + bestSuperEasy + " seconds is best so far");
                System.out.println("\t\t" + worstSuperEasy + " seconds is worst so far");
            }
            double start = System.currentTimeMillis();

            Configuration init = new SudokuConfig("super_easy.txt", line);
            Backtracker bt = new Backtracker();
            Optional<Configuration> sol = bt.solve(init);

            double timing = (System.currentTimeMillis() - start) / 1000.0;
            superEasyTime += timing;
            if(timing < bestSuperEasy) bestSuperEasy = timing;
            if(timing > worstSuperEasy) worstSuperEasy = timing;
        }

        System.out.println("\nSuper easy: " + superEasyTime/10000 + " seconds.");
        System.out.println("Best took " + bestSuperEasy + " seconds to complete.");
        System.out.println("Worst took " + worstSuperEasy + " seconds to complete.\n");

        //find the average time to solve all 10000 easy problems
        double easyTime = 0;
        double bestEasy = 100000000000000000000000.0;
        double worstEasy = 0;
        System.out.println("Easy:");
        for(int line=1; line < 10001; line++){
            if(line%1000 == 0 && line != 10000){
                System.out.println("\t" + line / 100 + "% done");
                System.out.println("\t\t" + easyTime / line + " seconds each so far");
                System.out.println("\t\t" + bestEasy + " seconds is best so far");
                System.out.println("\t\t" + worstEasy + " seconds is worst so far");
            }
            double start = System.currentTimeMillis();

            Configuration init = new SudokuConfig("easy.txt", line);
            Backtracker bt = new Backtracker();
            Optional<Configuration> sol = bt.solve(init);

            double timing = (System.currentTimeMillis() - start) / 1000.0;
            easyTime += timing;
            if(timing < bestEasy) bestEasy = timing;
            if(timing > worstEasy) worstEasy = timing;
        }

        System.out.println("\nEasy: " + easyTime/10000 + " seconds.");
        System.out.println("Best took " + bestEasy + " seconds to complete.");
        System.out.println("Worst took " + worstEasy + " seconds to complete.\n");

        //find the average time to solve all 10000 normal problems
        double normalTime = 0;
        double bestNormal = 10000000000000.0;
        double worstNormal = 0;
        System.out.println("Normal:");
        for(int line=1; line < 10001; line++){
            if(line%1000 == 0 && line != 10000){
                System.out.println("\t" + line / 100 + "% done");
                System.out.println("\t\t" + normalTime / line + " seconds each so far");
                System.out.println("\t\t" + bestNormal + " seconds is best so far");
                System.out.println("\t\t" + worstNormal + " seconds is worst so far");
            }
            double start = System.currentTimeMillis();

            Configuration init = new SudokuConfig("normal.txt", line);
            Backtracker bt = new Backtracker();
            Optional<Configuration> sol = bt.solve(init);

            double timing = (System.currentTimeMillis() - start) / 1000.0;
            normalTime += timing;
            if(timing < bestNormal) bestNormal = timing;
            if(timing > worstNormal) worstNormal = timing;
        }

        System.out.println("\nNormal: " + normalTime/10000 + " seconds.");
        System.out.println("Best took " + bestNormal + " seconds to complete.");
        System.out.println("Worst took " + worstNormal + " seconds to complete.\n");

        //find the average time to solve all 10000 hard problems
        double hardTime = 0;
        double bestHard = 1000000000000000000.0;
        double worstHard = 0;
        System.out.println("Hard:");
        for(int line=1; line < 10001; line++){
            if(line%1000 == 0 && line != 10000){
                System.out.println("\t" + line / 100 + "% done");
                System.out.println("\t\t" + hardTime / line + " seconds each so far");
                System.out.println("\t\t" + bestHard + " seconds is best so far");
                System.out.println("\t\t" + worstHard + " seconds is worst so far");
            }
            double start = System.currentTimeMillis();

            Configuration init = new SudokuConfig("hard.txt", line);
            Backtracker bt = new Backtracker();
            Optional<Configuration> sol = bt.solve(init);

            double timing = (System.currentTimeMillis() - start) / 1000.0;
            hardTime += timing;
            if(timing < bestHard) bestHard = timing;
            if(timing > worstHard) worstHard = timing;
        }

        System.out.println("\nHard: " + hardTime/10000 + " seconds.");
        System.out.println("Best took " + bestHard + " seconds to complete.");
        System.out.println("Worst took " + worstHard + " seconds to complete.\n");

        //find the average time to solve all 10000 extreme problems
        double extremeTime = 0;
        double bestExtreme = 100000000000000000000000000000.0;
        double worstExtreme = 0;
        System.out.println("Extreme:");//TODO - IMPLEMENT THIS WITH THREADS, IMPOSSIBLE ON ITS OWN
        for(int line=1; line < 10001; line++){ //strap yourselves in folks
            if(line%1000 == 0 && line != 10000) {
                System.out.println("\t" + line / 100 + "% done");
                System.out.println("\t\t" + extremeTime / line + " seconds each so far");
                System.out.println("\t\t" + bestExtreme + " seconds is best so far");
                System.out.println("\t\t" + worstExtreme + " seconds is worst so far");
            }
            double start = System.currentTimeMillis();

            Configuration init = new SudokuConfig("extreme.txt", line);
            Backtracker bt = new Backtracker();
            Optional<Configuration> sol = bt.solve(init);

            double timing = (System.currentTimeMillis() - start) / 1000.0;
            extremeTime += timing;
            if(timing < bestExtreme) bestExtreme = timing;
            if(timing > worstExtreme) worstExtreme = timing;
        }

        System.out.println("\nExtreme: " + extremeTime/10000 + " seconds.");
        System.out.println("Best took " + bestExtreme + " seconds to complete.");
        System.out.println("Worst took " + worstExtreme + " seconds to complete.\n");
    }
}