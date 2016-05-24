package backtracking;

import model.SudokuModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * The class represents a single configuration of a sudoku puzzle and
 * is used in the backtracking algorithm to solve the puzzle.
 * @author Timothy Geary
 */
public class SudokuConfig implements Configuration{
    //states
    private int[] pos;
    public int[][] puzzle;

    /**
     * Constructs a new SudokuConfig from a provided filename
     * @param filename - the provided filename
     * @throws FileNotFoundException
     */
    public SudokuConfig(String filename, int lineNumber) throws FileNotFoundException{
        Scanner in = new Scanner( new File("puzzles/" + filename) );
        puzzle = new int[9][9];

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

    /** Constructs a Sudokuconfig from an existing model */
    public SudokuConfig(SudokuModel other){
        this.puzzle = new int[9][9];
        for(int r=0; r < 9; r++){
            System.arraycopy(other.puzzle[r], 0, this.puzzle[r], 0, 9);
        }
        pos = new int[2];
        pos[0] = 0;
        pos[1] = 0;
    }


    /** Constructs a SudokuConfig that's a copy of an existing SudokuConfig */
    public SudokuConfig(SudokuConfig other){
        this.pos = new int[2];
        System.arraycopy(other.pos, 0, this.pos, 0, 2);
        this.puzzle = new int[9][9];
        for(int r=0; r < 9; r++){
            System.arraycopy(other.puzzle[r], 0, this.puzzle[r], 0, 9);
        }
    }

    /**
     * Utility method - takes (r, c) and returns an array of the position of the
     * first duplicate number therein, or [-1, -1] if there are no duplicates.
     * @param r the row of the examined spot
     * @param c the column of the examined spot
     * @return array of two ints representing the (r, c) of the error, if there is one
     */
    private int[] checkInnerSquare(int r, int c) {
        Set<Integer> neighbors = new HashSet<>();
        int[] errorSpot = new int[2];

        if (r < 3) {//top three squares
            if (c < 3) {//upper left corner
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (neighbors.contains(puzzle[i][j])) {
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        } else {
                            if (puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            } else if (c < 6) {//upper middle
                for (int i = 0; i < 3; i++) {
                    for (int j = 3; j < 6; j++) {
                        if (neighbors.contains(puzzle[i][j])) {
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        } else {
                            if (puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            } else {//upper right corner
                for (int i = 0; i < 3; i++) {
                    for (int j = 6; j < 9; j++) {
                        if (neighbors.contains(puzzle[i][j])) {
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        } else {
                            if (puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            }
        } else if (r < 6) {//center
            if (c < 3) {//middle left
                for (int i = 3; i < 6; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (neighbors.contains(puzzle[i][j])) {
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        } else {
                            if (puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            } else if (c < 6) {//middle center, the eye of sauron
                for (int i = 3; i < 6; i++) {
                    for (int j = 3; j < 6; j++) {
                        if (neighbors.contains(puzzle[i][j])) {
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        } else {
                            if (puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            } else {//middle right
                for (int i = 3; i < 6; i++) {
                    for (int j = 6; j < 9; j++) {
                        if (neighbors.contains(puzzle[i][j])) {
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        } else {
                            if (puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            }
        } else {//bottom three squares
            if (c < 3) {//bottom left
                for (int i = 6; i < 9; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (neighbors.contains(puzzle[i][j])) {
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        } else {
                            if (puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            } else if (c < 6) {//bottom center
                for (int i = 6; i < 9; i++) {
                    for (int j = 3; j < 6; j++) {
                        if (neighbors.contains(puzzle[i][j])) {
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        } else {
                            if (puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            } else {//bottom right, finally done
                for (int i = 6; i < 9; i++) {
                    for (int j = 6; j < 9; j++) {
                        if (neighbors.contains(puzzle[i][j])) {
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        } else {
                            if (puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
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

    @Override
    public ArrayList<Configuration> getSuccessors(){
        ArrayList<Configuration> successors = new ArrayList<>();
        pos[1]++;
        if(pos[1] >= 9){
            pos[1] = 0;
            pos[0]++;
        }
        if(pos[0] >= 9){
            return successors;
        }

        switch(puzzle[pos[0]][pos[1]]) {
            case 0:
                SudokuConfig addOne = new SudokuConfig(this);
                addOne.puzzle[pos[0]][pos[1]] = 1;
                successors.add(addOne);
                SudokuConfig addTwo = new SudokuConfig(this);
                addTwo.puzzle[pos[0]][pos[1]] = 2;
                successors.add(addTwo);
                SudokuConfig addThree = new SudokuConfig(this);
                addThree.puzzle[pos[0]][pos[1]] = 3;
                successors.add(addThree);
                SudokuConfig addFour = new SudokuConfig(this);
                addFour.puzzle[pos[0]][pos[1]] = 4;
                successors.add(addFour);
                SudokuConfig addFive = new SudokuConfig(this);
                addFive.puzzle[pos[0]][pos[1]] = 5;
                successors.add(addFive);
                SudokuConfig addSix = new SudokuConfig(this);
                addSix.puzzle[pos[0]][pos[1]] = 6;
                successors.add(addSix);
                SudokuConfig addSeven = new SudokuConfig(this);
                addSeven.puzzle[pos[0]][pos[1]] = 7;
                successors.add(addSeven);
                SudokuConfig addEight = new SudokuConfig(this);
                addEight.puzzle[pos[0]][pos[1]] = 8;
                successors.add(addEight);
                SudokuConfig addNine = new SudokuConfig(this);
                addNine.puzzle[pos[0]][pos[1]] = 9;
                successors.add(addNine);
            default:
                SudokuConfig skip = new SudokuConfig(this);
                successors.add(skip);
        }
        return successors;
    }

    @Override
    public boolean isValid(){
        if(checkInnerSquare(pos[0], pos[1])[0] != -1) return false;

        //check column for unique elements
        Set<Integer> col = new HashSet<>();
        for(int r=0; r < 9; r++){
            if(col.contains(puzzle[r][pos[1]])) return false;
            if(puzzle[r][pos[1]] != 0) col.add(puzzle[r][pos[1]]);
        }

        //check row for unique elements
        Set<Integer> row = new HashSet<>();
        for(int c=0; c < 9; c++){
            if(row.contains(puzzle[pos[0]][c])) return false;
            if(puzzle[pos[0]][c] != 0) row.add(puzzle[pos[0]][c]);
        }

        return true;
    }

    @Override
    public boolean isGoal(){ //must be valid, so just check if it's filled
        // <optimize>
        //This optimizes by filling in partially complete areas
        boolean optimized;
        do{//this loop continues for as long as new numbers are added to the puzzle
            optimized = false;
            //check columns for partial completeness
            for(int r=0; r < 9; r++){
                Set<Integer> nums = new HashSet<>();
                for(int c=0; c < 9; c++){
                    nums.add(puzzle[r][c]);
                }
                switch(nums.size()){
                    case 8:
                        int missing = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9;
                        for(int present : nums){
                            missing -= present;
                        }
                        for(int c=0; c < 9; c++){
                            if(puzzle[r][c] == 0) puzzle[r][c] = missing;
                        }
                        optimized = true;
                        break;
                    case 7:
                        //todo
                        break;
                    case 6:
                        //todo
                        break;
                }
            }
            //check rows for partial completeness
            for(int c=0; c < 9; c++){
                Set<Integer> nums = new HashSet<>();
                for(int r=0; r < 9; r++){
                    nums.add(puzzle[r][c]);
                }
                switch(nums.size()){
                    case 8:
                        int missing = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9;
                        for(int present : nums){
                            missing -= present;
                        }
                        for(int r=0; r < 9; r++){
                            if(puzzle[r][c] == 0) puzzle[r][c] = missing;
                        }
                        optimized = true;
                        break;
                    case 7:
                        //todo
                        break;
                    case 6:
                        //todo
                        break;
                }
            }
            //check inner squares for partial completeness

            //todo

        }while(optimized);//todo - set it so that optimized = true if an integer was added
        // </optimize>
        for(int r=0; r < 9; r++){
            for(int c=0; c < 9; c++){
                if(puzzle[r][c] == 0){
                    return false;
                }
            }
        }
        return true;
    }
}