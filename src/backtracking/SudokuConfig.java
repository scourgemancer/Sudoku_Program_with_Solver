package backtracking;

import model.SudokuModel;

import java.io.File;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;

/**
 * The class represents a single configuration of a sudoku puzzle
 * and is used in the backtracking algorithm to solve the puzzle.
 * @author Timothy Geary
 */
public class SudokuConfig implements Configuration{
    //states
    private byte[] pos;
    public byte[] puzzle;

    /**
     * Constructs a new SudokuConfig from a provided filename
     * @param filename - the provided filename
     * @throws FileNotFoundException
     */
    public SudokuConfig(String filename, int lineNumber) throws FileNotFoundException{
        Scanner in = new Scanner( new File("puzzles/" + filename) );
        puzzle = new byte[81];

        for(int i=0; i < lineNumber-1; i++){
            in.nextLine();
        }

        String problemLine = in.nextLine();
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                puzzle[r*9+c] = (byte)Character.getNumericValue( problemLine.charAt(r*9 + c) );
            }
        }

        in.close();
        pos = new byte[2];
        pos[0] = 0;
        pos[1] = -1;
    }

    /** Constructs a Sudokuconfig from an existing model */
    public SudokuConfig(SudokuModel other){
        this.puzzle = new byte[81];
        System.arraycopy(other.puzzle, 0, this.puzzle, 0, 81);
        pos = new byte[2];
        pos[0] = 0;
        pos[1] = 0;
    }


    /** Constructs a SudokuConfig that's a copy of an existing SudokuConfig */
    public SudokuConfig(SudokuConfig other){
        this.pos = new byte[2];
        System.arraycopy(other.pos, 0, this.pos, 0, 2);
        this.puzzle = new byte[81];
        System.arraycopy(other.puzzle, 0, this.puzzle, 0, 81);
    }

    /**
     * Finds the numbers already within an inner square contiaining the given (r, c) point
     * @param r the row of the point being observed
     * @param c the column of the point being observed
     * @return a hashset of the bytes already in the inner square
     */
    private HashSet<Byte> getInnerSquare( byte r, byte c){
        HashSet<Byte> neighbors = new HashSet<>();

        if(r < 3){//top three squares
            if(c < 3){//upper left corner
                for(byte i = 0; i < 3; i++){
                    for(byte j = 0; j < 3; j++){
                        neighbors.add(puzzle[i*9+j]);
                    }
                }
            }else if(c < 6){//upper middle
                for(byte i = 0; i < 3; i++){
                    for(byte j = 3; j < 6; j++){
                        neighbors.add(puzzle[i*9+j]);
                    }
                }
            }else{//upper right corner
                for(byte i = 0; i < 3; i++){
                    for(byte j = 6; j < 9; j++){
                        neighbors.add(puzzle[i*9+j]);
                    }
                }
            }
        }else if(r < 6){//center
            if(c < 3){//middle left
                for(byte i = 3; i < 6; i++){
                    for(byte j = 0; j < 3; j++){
                        neighbors.add(puzzle[i*9+j]);
                    }
                }
            }else if(c < 6){//middle center, the eye of sauron
                for(byte i = 3; i < 6; i++){
                    for(byte j = 3; j < 6; j++){
                        neighbors.add(puzzle[i*9+j]);
                    }
                }
            }else{//middle right
                for(byte i = 3; i < 6; i++){
                    for(byte j = 6; j < 9; j++){
                        neighbors.add(puzzle[i*9+j]);
                    }
                }
            }
        }else{//bottom three squares
            if(c < 3){//bottom left
                for(byte i = 6; i < 9; i++){
                    for(byte j = 0; j < 3; j++){
                        neighbors.add(puzzle[i*9+j]);
                    }
                }
            }else if(c < 6){//bottom center
                for(byte i = 6; i < 9; i++){
                    for(byte j = 3; j < 6; j++){
                        neighbors.add(puzzle[i*9+j]);
                    }
                }
            }else{//bottom right, finally done
                for(byte i = 6; i < 9; i++){
                    for(byte j = 6; j < 9; j++){
                        neighbors.add(puzzle[i*9+j]);
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
     * @return array of two bytes representing the (r, c) of the error, if there is one
     */
    private byte[] checkInnerSquare(byte r, byte c) {
        Set<Byte> neighbors = new HashSet<>();
        byte[] errorSpot = new byte[2];

        if(r < 3){//top three squares
            if(c < 3){//upper left corner
                for(byte i = 0; i < 3; i++){
                    for(byte j = 0; j < 3; j++){
                        if(neighbors.contains(puzzle[i*9+j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i*9+j] != 0) neighbors.add(puzzle[i*9+j]);
                        }
                    }
                }
            }else if(c < 6){//upper middle
                for(byte i = 0; i < 3; i++){
                    for(byte j = 3; j < 6; j++){
                        if(neighbors.contains(puzzle[i*9+j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i*9+j] != 0) neighbors.add(puzzle[i*9+j]);
                        }
                    }
                }
            }else{//upper right corner
                for(byte i = 0; i < 3; i++){
                    for(byte j = 6; j < 9; j++){
                        if(neighbors.contains(puzzle[i*9+j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i*9+j] != 0) neighbors.add(puzzle[i*9+j]);
                        }
                    }
                }
            }
        }else if(r < 6){//center
            if(c < 3){//middle left
                for(byte i = 3; i < 6; i++){
                    for(byte j = 0; j < 3; j++){
                        if(neighbors.contains(puzzle[i*9+j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i*9+j] != 0) neighbors.add(puzzle[i*9+j]);
                        }
                    }
                }
            }else if(c < 6){//middle center, the eye of sauron
                for(byte i = 3; i < 6; i++){
                    for(byte j = 3; j < 6; j++){
                        if(neighbors.contains(puzzle[i*9+j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i*9+j] != 0) neighbors.add(puzzle[i*9+j]);
                        }
                    }
                }
            }else{//middle right
                for(byte i = 3; i < 6; i++){
                    for(byte j = 6; j < 9; j++){
                        if(neighbors.contains(puzzle[i*9+j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i*9+j] != 0) neighbors.add(puzzle[i*9+j]);
                        }
                    }
                }
            }
        }else{//bottom three squares
            if(c < 3){//bottom left
                for(byte i = 6; i < 9; i++){
                    for(byte j = 0; j < 3; j++){
                        if(neighbors.contains(puzzle[i*9+j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if (puzzle[i*9+j] != 0) neighbors.add(puzzle[i*9+j]);
                        }
                    }
                }
            }else if(c < 6){//bottom center
                for(byte i = 6; i < 9; i++){
                    for(byte j = 3; j < 6; j++){
                        if(neighbors.contains(puzzle[i*9+j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i*9+j] != 0) neighbors.add(puzzle[i*9+j]);
                        }
                    }
                }
            }else{//bottom right, finally done
                for(byte i = 6; i < 9; i++){
                    for(byte j = 6; j < 9; j++){
                        if(neighbors.contains(puzzle[i*9+j])){
                            errorSpot[0] = i;
                            errorSpot[1] = j;
                            return errorSpot;
                        }else{
                            if(puzzle[i*9+j] != 0) neighbors.add(puzzle[i*9+j]);
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
        //todo - maybe I could try having successors be for the square with the fewest possibilities, not just next
        //todo - maybe even try doing what I do, have a list of possibilities for each square and whittle down, it
        //todo -            might even help the above todo, just check each pre-made list && not compute (compare)
        //todo - maybe even keep track of how many of each number has been used and focus on the most used, or least
        //todo - maybe construct another function to get the possible values for a square, decide if optimize is
        //todo -            necessary then or if they can work together
        //todo - I believe that I can auto-fill squares by checking inner square and r+c while in the isValid section
        ArrayList<Configuration> successors = new ArrayList<>();
        pos[1]++;
        if(pos[1] >= 9){
            pos[1] = 0;
            pos[0]++;
        }
        if(pos[0] >= 9){
            return successors;
        }

        if(puzzle[pos[0]*9+pos[1]] != 0){ //check if the square has already been filled in
            SudokuConfig skip = new SudokuConfig(this);
            successors.add(skip);
        }else{ //otherwise we need to make a child for each digit //todo - try to optimize by not pursuing invalid nums
            for(byte i=1; i<10; i++){
                SudokuConfig addI = new SudokuConfig(this);
                addI.puzzle[pos[0] * 9 + pos[1]] = i;
                successors.add(addI);
            }
        }
        return successors;
    }

    @Override
    public boolean isValid(){
        if(checkInnerSquare(pos[0], pos[1])[0] != -1) return false;

        //check column for unique elements
        boolean[] col = new boolean[9];
        for(byte r=0; r < 9; r++){
            int num = puzzle[ r*9+pos[1] ]-1;
            if(num != -1){
                if(col[num]){
                    return false;
                }else{
                    col[num] = true;
                }
            }
        }

        //check row for unique elements
        boolean[] row = new boolean[9];
        for(byte c=0; c < 9; c++){
            int num = puzzle[ pos[0]*9+c ]-1;
            if(num != -1){
                if(row[num]){
                    return false;
                }else{
                    row[num] = true;
                }
            }
        }

        return true;
    }

    @Override
    public boolean isGoal(){
        //must be valid, so just check if the last filled square was the last box in the Sudoku
        if(pos[0]==8 && pos[1]==8) return true;
        return false;
    }
}