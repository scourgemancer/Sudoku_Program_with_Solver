package backtracking;

import model.SudokuModel;

import java.io.FileNotFoundException;

/**
 * This tests the faster sudoku solver on all of the
 * available 10000 puzzles for each difficulty, timing
 * how long it takes on average, at worst, and at best.
 * @author Timothy Geary
 */
public class AverageFasterTimes{
    public static void main(String[] args) throws FileNotFoundException{
        System.out.println("Average times:\n");

        //find the average time to solve all 10000 super easy problems
        double superEasyTime = 0;
        double bestSuperEasy = Double.MAX_VALUE;
        double worstSuperEasy = 0;
        System.out.println("Super easy:");
        for(int line=1; line < 10001; line++){
            double start = System.currentTimeMillis();

            SudokuModel init = new SudokuModel("super_easy", line);
            FasterSolver.solveBoard( init.puzzle );

            double timing = (System.currentTimeMillis() - start) / 1000.0;
            superEasyTime += timing;
            if(timing < bestSuperEasy) bestSuperEasy = timing;
            if(timing > worstSuperEasy) worstSuperEasy = timing;
        }
        System.out.println("\tBest:\t\t" + bestSuperEasy + " seconds");
        System.out.println("\tAverage:\t" + superEasyTime/10000 + " seconds");
        System.out.println("\tWorst:\t\t" + worstSuperEasy + " seconds\n");

        //find the average time to solve all 10000 easy problems
        double easyTime = 0;
        double bestEasy = Double.MAX_VALUE;
        double worstEasy = 0;
        System.out.println("Easy:");
        for(int line=1; line < 10001; line++){
            double start = System.currentTimeMillis();

            SudokuModel init = new SudokuModel("easy", line);
            FasterSolver.solveBoard( init.puzzle );

            double timing = (System.currentTimeMillis() - start) / 1000.0;
            easyTime += timing;
            if(timing < bestEasy) bestEasy = timing;
            if(timing > worstEasy) worstEasy = timing;
        }
        System.out.println("\tBest:\t\t" + bestEasy + " seconds");
        System.out.println("\tAverage:\t" + easyTime/10000 + " seconds");
        System.out.println("\tWorst:\t\t" + worstEasy + " seconds\n");

        //find the average time to solve all 10000 normal problems
        double normalTime = 0;
        double bestNormal = Double.MAX_VALUE;
        double worstNormal = 0;
        System.out.println("Normal:");
        for(int line=1; line < 10001; line++){
            double start = System.currentTimeMillis();

            SudokuModel init = new SudokuModel("normal", line);
            FasterSolver.solveBoard( init.puzzle );

            double timing = (System.currentTimeMillis() - start) / 1000.0;
            normalTime += timing;
            if(timing < bestNormal) bestNormal = timing;
            if(timing > worstNormal) worstNormal = timing;
        }
        System.out.println("\tBest:\t\t" + bestNormal + " seconds");
        System.out.println("\tAverage:\t" + normalTime/10000 + " seconds");
        System.out.println("\tWorst:\t\t" + worstNormal + " seconds\n");

        //find the average time to solve all 10000 hard problems
        double hardTime = 0;
        double bestHard = Double.MAX_VALUE;
        double worstHard = 0;
        System.out.println("Hard:");
        for(int line=1; line < 10001; line++){
            double start = System.currentTimeMillis();

            SudokuModel init = new SudokuModel("hard", line);
            FasterSolver.solveBoard( init.puzzle );

            double timing = (System.currentTimeMillis() - start) / 1000.0;
            hardTime += timing;
            if(timing < bestHard) bestHard = timing;
            if(timing > worstHard) worstHard = timing;
        }
        System.out.println("\tBest:\t\t" + bestHard + " seconds");
        System.out.println("\tAverage:\t" + hardTime/10000 + " seconds");
        System.out.println("\tWorst:\t\t" + worstHard + " seconds\n");

        //find the average time to solve all 10000 extreme problems
        double extremeTime = 0;
        double bestExtreme = Double.MAX_VALUE;
        double worstExtreme = 0;
        System.out.println("Extreme:");
        for(int line=1; line < 10001; line++){ //strap yourselves in folks
            double start = System.currentTimeMillis();

            SudokuModel init = new SudokuModel("extreme", line);
            FasterSolver.solveBoard( init.puzzle );

            double timing = (System.currentTimeMillis() - start) / 1000.0;
            extremeTime += timing;
            if(timing < bestExtreme) bestExtreme = timing;
            if(timing > worstExtreme) worstExtreme = timing;
        }
        System.out.println("\tBest:\t\t" + bestExtreme + " seconds");
        System.out.println("\tAverage:\t" + extremeTime/10000 + " seconds");
        System.out.println("\tWorst:\t\t" + worstExtreme + " seconds\n");
    }
}
