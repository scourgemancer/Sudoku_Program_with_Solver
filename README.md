# Sudoku_Program_with_Solver
A simple sudoku program written in Java that has a Plain Text UI(from the terminal), a Graphical UI, and even a solver!

The program also comes with 10,000 puzzles for each of the five difficulty levels!
The five levels of difficulty are super easy, easy, normal, hard, and extreme!

The graphical UI was done in JavaFX and the solver utilized the backtracking algorithm, which was replaced by a much faster logical solver.

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

The program takes one argument, gui or ptui, depending on if you want to use the graphical UI or the plain text UI.
If running the plain text UI, an additional argument must be provided with the desired difficulty option.
The difficulty options should be provided as either super_easy, easy, normal, hard, or extreme.
