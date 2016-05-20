# Sudoku_Program_with_Solver
A simple sudoku program written in Java that has a Plain Text UI(from the terminal), a Graphical UI, and even a solver!

The program also comes with 10,000 puzzles for each of the five difficulty levels!

The graphical UI was done using JavaFX and the solver utilizes the backtracking algorithm.

When using the Plain Text UI, the sudoku is represented like this:
<pre>
   1 2 3   4 5 6   7 8 9
   ---------------------
1 |7 8   |       |   4  
2 |  9 1 | 8 4 5 |   3 6
3 |4     | 2   7 | 5   8
  |------+-------+------
4 |5   9 | 4   3 | 8    
5 |3     | 9     |     5
6 |  4 7 | 5 2   | 3 1 9
  |------+-------+------
7 |9 5   | 7     |     1
8 |  7   |   8 2 |   5 3
9 |  3 2 | 1 5   |   7 4
</pre>
with commands asking for the row and column numbers to perform operations.
