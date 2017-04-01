package backtracking;

import model.SudokuModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * The class represents a single configuration of a sudoku puzzle
 * and is used in the backtracking algorithm to solve the puzzle.
 * @author Timothy Geary
 */
public class SudokuConfig implements Configuration{
    //states
    private int[] pos;
    public int[][] puzzle;
    //private ArrayList<ArrayList<HashSet<Integer>>> possibilities;

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
        pos[1] = -1;
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
     * Finds the numbers already within an inner square contiaining the given (r, c) point
     * @param r the row of the point being observed
     * @param c the column of the point being observed
     * @return a hashset of the integers already in the inner square
     */
    private HashSet<Integer> getInnerSquare( int r, int c){
        HashSet<Integer> neighbors = new HashSet<>();

        if(r < 3){//top three squares
            if(c < 3){//upper left corner
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        neighbors.add(puzzle[i][j]);
                    }
                }
            }else if(c < 6){//upper middle
                for(int i = 0; i < 3; i++){
                    for(int j = 3; j < 6; j++){
                        neighbors.add(puzzle[i][j]);
                    }
                }
            }else{//upper right corner
                for(int i = 0; i < 3; i++){
                    for(int j = 6; j < 9; j++){
                        neighbors.add(puzzle[i][j]);
                    }
                }
            }
        }else if(r < 6){//center
            if(c < 3){//middle left
                for(int i = 3; i < 6; i++){
                    for(int j = 0; j < 3; j++){
                        neighbors.add(puzzle[i][j]);
                    }
                }
            }else if(c < 6){//middle center, the eye of sauron
                for(int i = 3; i < 6; i++){
                    for(int j = 3; j < 6; j++){
                        neighbors.add(puzzle[i][j]);
                    }
                }
            }else{//middle right
                for(int i = 3; i < 6; i++){
                    for(int j = 6; j < 9; j++){
                        neighbors.add(puzzle[i][j]);
                    }
                }
            }
        }else{//bottom three squares
            if(c < 3){//bottom left
                for(int i = 6; i < 9; i++){
                    for(int j = 0; j < 3; j++){
                        neighbors.add(puzzle[i][j]);
                    }
                }
            }else if(c < 6){//bottom center
                for(int i = 6; i < 9; i++){
                    for(int j = 3; j < 6; j++){
                        neighbors.add(puzzle[i][j]);
                    }
                }
            }else{//bottom right, finally done
                for(int i = 6; i < 9; i++){
                    for(int j = 6; j < 9; j++){
                        neighbors.add(puzzle[i][j]);
                    }
                }
            }
        }
        neighbors.remove(0);//remove the zeroes that were added
        return neighbors;
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

        if(r < 3){//top three squares
            if(c < 3){//upper left corner
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
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
                for(int i = 0; i < 3; i++){
                    for(int j = 3; j < 6; j++){
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
                for(int i = 0; i < 3; i++){
                    for(int j = 6; j < 9; j++){
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
                for(int i = 3; i < 6; i++){
                    for(int j = 0; j < 3; j++){
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
                for(int i = 3; i < 6; i++){
                    for(int j = 3; j < 6; j++){
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
                for(int i = 3; i < 6; i++){
                    for(int j = 6; j < 9; j++){
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
                for(int i = 6; i < 9; i++){
                    for(int j = 0; j < 3; j++){
                        if(neighbors.contains(puzzle[i][j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if (puzzle[i][j] != 0) neighbors.add(puzzle[i][j]);
                        }
                    }
                }
            }else if(c < 6){//bottom center
                for(int i = 6; i < 9; i++){
                    for(int j = 3; j < 6; j++){
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
                for(int i = 6; i < 9; i++){
                    for(int j = 6; j < 9; j++){
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

    /**
     * Fills in any spaces on the sudoku that harbor hints from partially
     * completed rows, columns, inner squares, or a combination thereof.
     * It does so by giving each space all nine possibilities and whittles
     * those down using available information and fills them if they yield
     * only one possibility. Makes the brute force attempt slightly intelligent.
     */
   public void optimize(){
        //initialize the possibilities 2d array
        ArrayList<ArrayList<HashSet<Integer>>> possibilities = new ArrayList<>();
        for(int r=0; r < 9; r++){
            possibilities.add( r, new ArrayList<>() );
            for(int c=0; c < 9; c++){
                possibilities.get(r).add( c, new HashSet<>() );
                if(puzzle[r][c] == 0){
                    for (int i = 1; i < 10; i++) {
                        possibilities.get(r).get(c).add(i);
                    }
                }else{
                    possibilities.get(r).get(c).add(puzzle[r][c]);
                }
            }
        }

        //begin whittling down possibilities for each space
        boolean optimized;
        do{//keep looping if numbers were added from the last loop since they could provide more clues
            optimized = false;
            for(int r=0; r < 9; r++){
                for(int c=0; c < 9; c++){
                    if(possibilities.get(r).get(c).size() > 1){//isn't already decided
                        for(int i=0; i < 9; i++){//check the row
                            possibilities.get(r).get(c).remove(puzzle[r][i]);
                        }
                        for(int i=0; i < 9; i++){//check the column
                            possibilities.get(r).get(c).remove(puzzle[i][c]);
                        }
                        possibilities.get(r).get(c).removeAll(getInnerSquare(r, c));//check the inner square

                        if(possibilities.get(r).get(c).size() == 1) {//only one possibility, so fill it
                            optimized = true;
                            for(int i=0; i < 9; i++)
                                if(possibilities.get(r).get(c).contains(i)) puzzle[r][c] = i;
                        }
                    }
                }
            }
        }while(optimized);
        do{//check if the space is the only option for a value in their row/col/square
            optimized = false;
            for(int r=0; r < 9; r++){
                for(int c=0; c < 9; c++){
                    if(possibilities.get(r).get(c).size() > 1){
                        HashSet<Integer> copy = new HashSet<>( possibilities.get(r).get(c) );
                        for(int i=0; i < 9; i++){//check the row
                            copy.removeAll(possibilities.get(r).get(i));
                        }
                        for(int i=0; i < 9; i++){//check the column
                            copy.removeAll(possibilities.get(i).get(c));
                        }
                        //todo - check the inner square

                        if(copy.size() == 1) {//only one possibility, so fill it
                            optimized = true;
                            for(int i=0; i < 9; i++){
                                possibilities.get(r).get(c).clear();
                                possibilities.get(r).get(c).addAll(copy);
                                if(copy.contains(i)){
                                    puzzle[r][c] = i;
                                }
                            }
                        }
                    }
                }
            }
        }while(optimized);
        //this.possibilities = possibilities;
    }

    @Override
    public ArrayList<Configuration> getSuccessors(){
        //optimize(); //this was the optimal place to put this without restructuring anything
        ArrayList<Configuration> successors = new ArrayList<>();
        pos[1]++;
        if(pos[1] >= 9){
            pos[1] = 0;
            pos[0]++;
        }
        if(pos[0] >= 9){
            return successors;
        }

        switch (puzzle[pos[0]][pos[1]]) {
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
                /** //this is for when attempting optimization of backtracking
                for(Integer number : possibilities.get(pos[0]).get(pos[1])){
                    SudokuConfig addNum = new SudokuConfig(this);
                    addNum.puzzle[pos[0]][pos[1]] = number;
                    successors.add(addNum);
                }*/
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

        //check for zeroes above the last placed position
        /**for(int r=0; r < pos[0]; r++){//todo - this shouldn't be necessary
            for(int c=0; c < 9; c++){
                if(puzzle[r][c] == 0) return false;
            }
        }*/

        return true;
    }

    @Override
    public boolean isGoal(){ //must be valid, so just check if it's filled
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