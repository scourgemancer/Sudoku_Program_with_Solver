package backtracking;

import model.SudokuModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

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
        //todo - extremely important and mostly identical to the model's once finished
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
     * Utility method - takes (r, c) and returns its eight neighbors in the inner square
     * @param r the row of the examined spot
     * @param c the column of the examined spot
     * @return array of inner cube neighbors
     */
    private ArrayList<Integer> getInnerSquare(int r, int c){
        ArrayList<Integer> neighbors = new ArrayList<>();

        //todo - change to checkInnerSquare, return boolean and make checkRow and CkeckCol methods

        return neighbors;
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
        return false;//todo - different from model, just check the newly added spot
    }

    @Override
    public boolean isGoal(){
        return false;//todo - must be valid so just check for no empty spots
    }
}