package model;

import backtracking.Backtracker;
import backtracking.Configuration;
import backtracking.SudokuConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
//todo - make an isGoal method for submitting an answer
/**
 * This is the model for the entire program, it holds all of the
 * functionality of the program. The different views each use the
 * same model and display the content in different ways, as per
 * the model-view-control design pattern.
 * @author Timothy Geary
 */
public class SudokuModel extends Observable{
    //states
    public String filename;
    public int lineNumber;
    public int[] pos;
    public int[][] puzzle;
    public String textout = "";
    private static String helpmsg =
            "a|add r c number: Adds number to (r,c)\n" +
                    "c|clue: Adds one number as a clue" +
                    "d|display: Displays safe\n" +
                    "h|help: Prints this help message \n" +
                    "q|quit: Exits program \n" +
                    "r|remove r c: Removes number from (r,c) \n" +
                    "v|verify: Verifies safe correctness \n" +
                    "s|solve: Solves the sudoku puzzle";

    public SudokuModel(String difficulty) throws FileNotFoundException{
        filename = difficulty.toLowerCase() + ".txt";
        Scanner in = new Scanner( new File("puzzles/" + filename) );
        puzzle = new int[9][9];

        lineNumber = (int)(Math.random() * 10000) + 1;
        for(int i=0; i < lineNumber-1; i++){
            in.nextLine();
        }

        String problemLine = in.nextLine();
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                puzzle[r][c] = Character.getNumericValue( problemLine.charAt(r*9 + c) );
            }
        }

        in.close();
        pos = new int[2];
        pos[0] = 0;
        pos[1] = 0;
    }

    /** Utility functions for help and display messages */
    public void setHelpMsg() { this.textout = helpmsg; announceChange();}

    public void printIt() {this.textout = ""; announceChange();}

    /**
     * Utility method - takes (r, c) and returns its eight neighbors in the inner square
     * @param r the row of the examined spot
     * @param c the column of the examined spot
     * @return array of inner cube neighbors
     */
    private ArrayList<Integer> getInnerSquare(int r, int c){
        ArrayList<Integer> neighbors = new ArrayList<>();

        //todo nine nested if statements
        //todo - maybe change to checkInnerSquare and return a boolean instead
        //todo - make other methods for checking rows and columns

        return neighbors;
    }

    /**
     * Adds a number at (row, col), overwriting an existing number if already there.
     * @param r the row to add the number to
     * @param c the column to add the number to
     */
    public void addNumber(int r, int c, int num){
        if((r > -1 && c > -1 && r < 9 && c < 9)//in bounds
                && (num > 0 && num <= 9)){//valid number
            this.puzzle[r][c] = num;
            this.textout = num+" added at: ("+r+", "+c+")";
        }else{
            this.textout = "Error adding "+num+" at: ("+r+", "+c+")";
        }
        this.pos[0] = r;
        this.pos[1] = c;
        announceChange();
    }

    /**
     * Removes a number from (row, col).
     * @param r the row to remove the number from
     * @param c the column to remove the number from
     */
    public void removeNumber(int r, int c){
        if((r > -1 && c > -1 && r < 9 && c < 9)//in bounds
                && (puzzle[r][c] > 0) ){//is a guessed number
            this.puzzle[r][c] = 0;
            this.textout = "Number removed from: ("+r+", "+c+")";
        }else{
            this.textout = "Error removing number from: ("+r+", "+c+")";
        }
        this.pos[0] = r;
        this.pos[1] = c;
        announceChange();
    }

    /** Checks if SudokuModel is valid */
    public void isValid(){
        for(int r = 0; r < 9; r++){
            for (int c = 0; c < 9; c++) {
                //todo check inner squares and then each row/col
                //todo check inner squares if added by row, vice versa
            }
        }//todo - the spot that has an error is reported if existent
        /**
         * this.textout = "Error verifying at: ("+r+", "+c+")";
         * this.pos[0] = r;
         * this.pos[1] = c;
         * announceChange();
         * return;
         */
        this.textout = "Puzzle is valid so far!";
        announceChange();
    }

    @Override
    public String toString(){
        String result = "   0 1 2   3 4 5   6 7 8\n";
        result +=       "   ---------------------\n";
        for(int r=0; r < 9; r++){
            if(r%3 == 0 && r!=0) result += "  |------+-------+------\n";

            result += String.valueOf(r) + " |"; //row numbers

            for(int c = 0; c < 9; c++){
                if(c%3 == 0 && c!=0) result += "| ";
                if(puzzle[r][c] != 0){
                    result += String.valueOf(puzzle[r][c]);
                }else{
                    result += " ";
                }
                if (c != 8) result += " ";
            }
            result += "\n";
        }
        return result;
    }

    /**
     * This function either solves the current puzzle and updates the 2d
     *  grid to reflect the answer or identifies there being no solution
     */
    public void solve() throws FileNotFoundException{
        Backtracker bt = new Backtracker();//todo finish after backtracking
        /**Optional<Configuration> result = bt.solve(new SafeConfig(new LasersModel(filename)));
        Path path = Paths.get(filename);
        String file = path.getFileName().toString();
        if(result.isPresent()){
            puzzle = new int[row][col];
            for(int r = 0; r < row; r++){
                SafeConfig solution = (SafeConfig)result.get();
                System.arraycopy(solution.safe[r], 0, model[r], 0, col);
            }
            textout = file + " solved!";
        }else{
            textout = file + " has no solution";
        }*/
        announceChange();
    }

    /**
     * Adds a laser if the model is currently a subset of the solution
     * configuration path or says that the safe isn't if it's not
     */
    public void getHint(){
        Backtracker bt = new Backtracker();
        //todo - also get it to add a random hint
        //todo - you could pass 81-(a random number from 0 to 81; inclusive) other hints before adding
        /**Optional<Configuration> result = bt.solve(new SudokuConfig(this));
        if(result.isPresent()){
            SudokuConfig solution = (SudokuConfig) result.get();
            Boolean found = false;
            int ro = row+20; int co = row+20;
            for(int r=0; r<row; r++){
                for(int c=0; c<col; c++){
                    if(solution.get(r, c) == 'L' && get(r, c) != 'L'){
                        ro = r;
                        co = c;
                        found = true;
                        break;
                    }
                }
                if(found) break;
            }
            if(ro != row+20){
                addLaser(ro, co);
                textout = "Hint: added laser to (" + Integer.toString(ro) + ", " + Integer.toString(co) + ")";
            }else{
                textout = "Hint: no next step!";
            }
        }else{
            textout = "Hint: no next step!";
        }*/
        announceChange();
    }

    /**
     * A utility method that indicates the model
     * has changed and notifies the observers.
     */
    public void announceChange() {
        setChanged();
        notifyObservers();
    }
}