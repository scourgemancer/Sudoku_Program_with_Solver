package model;

import backtracking.Backtracker;
import backtracking.Configuration;
import backtracking.SudokuConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
    public String textout;
    private static String helpmsg =
            "a|add r c number: Adds number to (r,c)\n" +
                    "c|clue: Adds one number as a clue\n" +
                    "d|display: Displays puzzle\n" +
                    "h|help: Prints this help message \n" +
                    "q|quit: Exits program \n" +
                    "r|remove r c: Removes number from (r,c) \n" +
                    "v|verify: Verifies puzzle's correctness \n" +
                    "g|guess: Guess the answer\n" +
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
     * Adds a number at (row, col), overwriting an existing number if already there.
     * @param r the row to add the number to
     * @param c the column to add the number to
     */
    public void addNumber(int r, int c, int num){
        if((r > -1 && c > -1 && r < 9 && c < 9)//in bounds
                && (num > 0 && num <= 9)){//valid number
            this.puzzle[r][c] = num;
            this.textout = num+" added at: ("+(int)(r+1)+", "+(int)(c+1)+")";
        }else{
            this.textout = "Error adding "+num+" at: ("+(int)(r+1)+", "+(int)(c+1)+")";
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
            this.textout = "Number removed from: ("+(int)(r+1)+", "+(int)(c+1)+")";
        }else{
            this.textout = "Error removing number from: ("+(int)(r+1)+", "+(int)(c+1)+")";
        }
        this.pos[0] = r;
        this.pos[1] = c;
        announceChange();
    }

    /**
     * Utility method - takes (r, c) and returns an array of the position of the
     * first duplicate number therein, or [-1, -1] if there are no duplicates.
     * @param r the row of the examined spot
     * @param c the column of the examined spot
     * @return array of two ints representing the (r, c) of the error, if there is one
     */
    private int[] checkInnerSquare(int r, int c){
        Set<Integer> neighbors = new HashSet<>();
        int[] errorSpot = new int[2];

        if(r < 3){//top three squares
            if(c < 3){//upper left corner
                for(int i=0; i < 3; i++){
                    for(int j=0; j < 3; j++){
                        if(neighbors.contains(puzzle[i][j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            }else if(c < 6){//upper middle
                for(int i=0; i < 3; i++){
                    for(int j=3; j < 6; j++){
                        if(neighbors.contains(puzzle[i][j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            }else{//upper right corner
                for(int i=0; i < 3; i++){
                    for(int j=6; j < 9; j++){
                        if(neighbors.contains(puzzle[i][j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            }
        }else if(r < 6){//center
            if(c < 3){//middle left
                for(int i=3; i < 6; i++){
                    for(int j=0; j < 3; j++){
                        if(neighbors.contains(puzzle[i][j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            }else if(c < 6){//middle center, the eye of sauron
                for(int i=3; i < 6; i++){
                    for(int j=3; j < 6; j++){
                        if(neighbors.contains(puzzle[i][j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            }else{//middle right
                for(int i=3; i < 6; i++){
                    for(int j=6; j < 9; j++){
                        if(neighbors.contains(puzzle[i][j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            }
        }else{//bottom three squares
            if(c < 3){//bottom left
                for(int i=6; i < 9; i++){
                    for(int j=0; j < 3; j++){
                        if(neighbors.contains(puzzle[i][j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            }else if(c < 6){//bottom center
                for(int i=6; i < 9; i++){
                    for(int j=3; j < 6; j++){
                        if(neighbors.contains(puzzle[i][j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            }else{//bottom right, finally done
                for(int i=6; i < 9; i++){
                    for(int j=6; j < 9; j++){
                        if(neighbors.contains(puzzle[i][j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            }
        }
        //no errors so return impossible array
        errorSpot[0] = -1;
        errorSpot[1] = -1;
        return errorSpot;
    }

    /** Checks if SudokuModel is valid */
    public boolean isValid(){
        //check that all of the inner squares have unique elements
        if(checkInnerSquare(1, 1)[0] != -1){
            textout = "Puzzle is not valid";
            announceChange();
            return false;
        }
        if(checkInnerSquare(1, 4)[0] != -1){
            textout = "Puzzle is not valid";
            announceChange();
            return false;
        }
        if(checkInnerSquare(1, 7)[0] != -1){
            textout = "Puzzle is not valid";
            announceChange();
            return false;
        }
        if(checkInnerSquare(4, 1)[0] != -1){
            textout = "Puzzle is not valid";
            announceChange();
            return false;
        }
        if(checkInnerSquare(4, 4)[0] != -1){
            textout = "Puzzle is not valid";
            announceChange();
            return false;
        }
        if(checkInnerSquare(4, 7)[0] != -1){
            textout = "Puzzle is not valid";
            announceChange();
            return false;
        }
        if(checkInnerSquare(7, 1)[0] != -1){
            textout = "Puzzle is not valid";
            announceChange();
            return false;
        }
        if(checkInnerSquare(7, 4)[0] != -1){
            textout = "Puzzle is not valid";
            announceChange();
            return false;
        }
        if(checkInnerSquare(7, 7)[0] != -1){
            textout = "Puzzle is not valid";
            announceChange();
            return false;
        }

        //check all of the rows for unique elements
        for(int r=0; r < 9; r++){
            Set<Integer> row = new HashSet<>();
            for(int c=0; c < 9; c++){
                if(row.contains(puzzle[r][c])){
                    textout = "Puzzle is not valid";
                    announceChange();
                    return false;
                }else{
                    if(puzzle[r][c] != 0) row.add(puzzle[r][c]);
                }
            }
        }

        //check all of the columns for unique elements
        for(int c=0; c < 9; c++){
            Set<Integer> col = new HashSet<>();
            for(int r=0; r < 9; r++){
                if(col.contains(puzzle[r][c])){
                    textout = "Puzzle is not valid";
                    announceChange();
                    return false;
                }
                if(puzzle[r][c] != 0) col.add(puzzle[r][c]);
            }
        }

        //the puzzle must be valid to get this far
        this.textout = "Puzzle is valid so far!";
        announceChange();
        return true;
    }

    /** Checks if SudokuModel is valid and complete */
    public void isGoal(){
        if(isValid()){
            for(int r=0; r < 9; r++){
                for(int c=0; c < 9; c++){
                    if(puzzle[r][c] == 0){
                        this.textout = "This is not the solution";
                        announceChange();
                        return;
                    }
                }
            }
            this.textout = "Congratulations, you've solved it!";
        }else{
            this.textout = "This is not the solution";
        }
        announceChange();
    }

    /**
     * This function either solves the current puzzle and updates the 2d
     * grid to reflect the answer or identifies there being no solution
     */
    public void solve() throws FileNotFoundException{
        Backtracker bt = new Backtracker();
        Optional<Configuration> result = bt.solve(new SudokuConfig(new SudokuModel(filename.replace(".txt", ""))));
        if(result.isPresent()){
            puzzle = new int[9][9];
            for(int r = 0; r < 9; r++){
                SudokuConfig solution = (SudokuConfig)result.get();
                System.arraycopy(solution.puzzle[r], 0, puzzle[r], 0, 9);
            }
            textout = "The puzzle's been solved!";
        }else{
            textout = "The current puzzle has no solution";
        }
        announceChange();
    }

    /** Adds a number if the model is currently valid or mentions the first wrong square */
    public void getHint(){
        if(isValid()){
            Backtracker bt = new Backtracker();
            Optional<Configuration> result = bt.solve(new SudokuConfig(this));
            if(result.isPresent()){
                SudokuConfig solution = (SudokuConfig) result.get();
                Boolean found = false;
                int ro = -1;
                int co = -1;
                int num = -1;

                int hints = (int)(Math.random() * 81) + 1; //the number of hints to skip before adding
                while(hints > 0){
                    for(int r = 0; r < 9; r++){
                        for(int c = 0; c < 9; c++){
                            if(solution.puzzle[r][c] != 0 && puzzle[r][c] == 0){
                                ro = r;
                                co = c;
                                num = solution.puzzle[r][c];
                                found = true;
                                hints--;
                                break;
                            }
                        }
                        if(found) break;
                    }
                    if(found) break;
                }
                if(ro != -1){
                    addNumber(ro, co, num);
                    textout = "Hint: added " + num + " to (" + Integer.toString(ro+1) + ", " + Integer.toString(co+1) + ")";
                }else{
                    textout = "Hint: no next step!";
                }
            }else{
                textout = "Hint: no next step!";
            }
            announceChange();
        }else{
            String errorPos = this.textout.substring(textout.indexOf('('), textout.indexOf(')')+1);
            this.textout = "Number at " + errorPos + " is incorrect";
            announceChange();
        }
    }

    /**
     * A utility method that indicates the model
     * has changed and notifies the observers.
     */
    public void announceChange() {
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString(){
        String result = "   1 2 3   4 5 6   7 8 9\n";
        result +=       "   ---------------------\n";
        for(int r=0; r < 9; r++){
            if(r%3 == 0 && r!=0) result += "  |------+-------+------\n";

            result += String.valueOf(r+1) + " |"; //row numbers

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
}