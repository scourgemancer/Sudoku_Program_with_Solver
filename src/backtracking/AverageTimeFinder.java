package backtracking;

import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * This solves the 10000 puzzles for each of the difficulties
 * and times how long it takes to solve them all to find the
 * average time taken, the best time and the worst time for
 * each. If it isn't able to find a solution it returns and
 * prints out the line it failed to find a solution on.
 * @author Timothy Geary
 */
public class AverageTimeFinder{

    private static int totNumInstantaneous = 0;
    private static int totNumUnderHalfSec = 0;
    private static int totNumUnderSec = 0;
    private static int totNumUnderTwoSecs = 0;
    private static int totNumUnderFiveSecs = 0;
    private static int totNumUnderTenSecs = 0;
    private static int totNumUnderTwentySecs =0;
    private static int totNumUnderThirtySecs = 0;
    private static int totNumUnderMin = 0;
    private static int totNumOverMin = 0;

    public static void timeSolvingFor(String difficulty) throws FileNotFoundException{
        int numInstantaneous = 0;
        int numUnderHalfSec = 0;
        int numUnderSec = 0;
        int numUnderTwoSecs = 0;
        int numUnderFiveSecs = 0;
        int numUnderTenSecs = 0;
        int numUnderTwentySecs =0;
        int numUnderThirtySecs = 0;
        int numUnderMin = 0;
        int numOverMin = 0;
        double avgTime = 0;
        double bestTime = Double.MAX_VALUE;
        double worstTime = 0;
        System.out.println("\n" + difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1) + ":");
        for(int line=1; line < 10001; line++){
            if(line%1000==0 && line != 10000 ||
                        (difficulty.equals("extreme") && line%100==0 && line%1000!=0 && line!=10000)){
                System.out.println("\t" + line / 100 + "% done");
                System.out.println("\t\t" + avgTime / line + " seconds each so far");
                System.out.println("\t\t" + bestTime + " seconds is best so far");
                System.out.println("\t\t" + worstTime + " seconds is worst so far");
            }

            double start = System.currentTimeMillis();

            Configuration init = new SudokuConfig(difficulty + ".txt", line);
            Backtracker bt = new Backtracker();
            Optional<Configuration> sol = bt.solve(init);
            if(!sol.isPresent()){
                System.out.println("There was no solution found at line number: " + line);
                return;
            }

            double timing = (System.currentTimeMillis() - start) / 1000.0;
            avgTime += timing;
            if(timing < bestTime) bestTime = timing;
            if(timing > worstTime) worstTime = timing;
            if(timing < 0.1){      numInstantaneous++;     totNumInstantaneous++;}
            else if(timing < 0.5){ numUnderHalfSec++;      totNumUnderHalfSec++; }
            else if(timing < 1){   numUnderSec++;          totNumUnderSec++;}
            else if(timing < 2){   numUnderTwoSecs++;      totNumUnderTwoSecs++;}
            else if(timing < 5){   numUnderFiveSecs++;     totNumUnderFiveSecs++;}
            else if(timing < 10){  numUnderTenSecs++;      totNumUnderTenSecs++;}
            else if(timing < 20){  numUnderTwentySecs++;   totNumUnderTwentySecs++;}
            else if(timing < 30){  numUnderThirtySecs++;   totNumUnderThirtySecs++;}
            else if(timing < 60){  numUnderMin++;          totNumUnderMin++;}
            else{                  numOverMin++;           totNumOverMin++;}
        }

        System.out.println("\n" + difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1) + ":");
        System.out.println("\tBest:\t\t" + bestTime + " seconds");
        System.out.println("\tAverage:\t" + avgTime/10000 + " seconds");
        System.out.println("\tWorst:\t\t" + worstTime + " seconds\n");
        if(numInstantaneous>0){   System.out.println("\tInstantaneous (<0.1s):  " + numInstantaneous);
        if(numUnderHalfSec>0){    System.out.println("\tUnder half of a second: " + numUnderHalfSec);
        if(numUnderSec>0){        System.out.println("\tUnder a second:         " + numUnderSec);
        if(numUnderTwoSecs>0){    System.out.println("\tUnder two seconds:      " + numUnderTwoSecs);
        if(numUnderFiveSecs>0){   System.out.println("\tUnder five seconds:     " + numUnderFiveSecs);
        if(numUnderTenSecs>0){    System.out.println("\tUnder ten seconds:      " + numUnderTenSecs);
        if(numUnderTwentySecs>0){ System.out.println("\tUnder twenty seconds:   " + numUnderTwentySecs);
        if(numUnderThirtySecs>0){ System.out.println("\tUnder half of a minute: " + numUnderThirtySecs);
        if(numUnderMin>0){        System.out.println("\tUnder a minute:         " + numUnderMin);
        if(numOverMin>0){         System.out.println("\tOver a minute or more:  " + numOverMin);
        }}}}}}}}}}
    }

    public static void main(String[] args) throws FileNotFoundException{
        System.out.println("Average times:");
        double timingBegin = System.currentTimeMillis();

        timeSolvingFor("super_easy");
        timeSolvingFor("easy");
        timeSolvingFor("normal");
        timeSolvingFor("hard");
        timeSolvingFor("extreme");

        double overallTime = System.currentTimeMillis() - timingBegin;
        int hours = (int)(((overallTime/1000)/60)/60);
        int minutes =(int)((overallTime/1000)/60)%60;
        int seconds = (int)(overallTime/1000)%60;
        int milli = (int)(overallTime%1000);
        System.out.println("\nOverall time for all puzzles: " + hours + ":" + minutes + ":" + seconds + ":" + milli);
        System.out.println("Average puzzle took about " + ((overallTime / 1000) / 50000) + " seconds");
        if(totNumInstantaneous>0){   System.out.println("Instantaneous (<0.1s):  " + totNumInstantaneous);
        if(totNumUnderHalfSec>0){    System.out.println("Under half of a second: " + totNumUnderHalfSec);
        if(totNumUnderSec > 0){      System.out.println("Under a second:         " + totNumUnderSec);
        if(totNumUnderTwoSecs>0){    System.out.println("Under two seconds:      " + totNumUnderTwoSecs);
        if(totNumUnderFiveSecs>0){   System.out.println("Under five seconds:     " + totNumUnderFiveSecs);
        if(totNumUnderTenSecs>0){    System.out.println("Under ten seconds:      " + totNumUnderTenSecs);
        if(totNumUnderTwentySecs>0){ System.out.println("Under twenty seconds:   " + totNumUnderTwentySecs);
        if(totNumUnderThirtySecs>0){ System.out.println("Under half of a minute: " + totNumUnderThirtySecs);
        if(totNumUnderMin>0){        System.out.println("Under a minute:         " + totNumUnderMin);
        if(totNumOverMin>0){         System.out.println("Over a minute or more:  " + totNumOverMin + "\n");
        }}}}}}}}}}
    }
}