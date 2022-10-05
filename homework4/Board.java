import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    private int[][] board;
    private int n;
    private int blankRow;
    private int blankCol;

    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n][n];
        /* note:
        row and col index range from 0 to n - 1
        board number starts from 0 to n ^ 2 - 1
        */
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = tiles[i][j];
                // using 0 to designate the blank square
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
    }

    /* string representation of this board
    String representation example:
        3
        1 0 3
        4 2 5
        7 8 6
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // goal value in location(row, col)
    private int goalValue(int row, int col) {
        // goal value of location(row, col)
        if (row == n - 1 && col == n - 1) return 0;
        else return row * n + col + 1;
    }
    // location(row, col) of goal value
    private int[] goalLoc(int k) {
        // goal location of integer k;
        int[] res = new int[2];
        if (k == 0) {
            res[0] = n - 1;
            res[1] = n - 1;
        }
        else {
            res[0] = (k - 1) / n;
            res[1] = (k - 1) % n;
        }
        return res;
    }
    // number of tiles out of place
    public int hamming() {
        int dist = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // blank square does NOT count to hamming
                if (board[i][j] != 0 && board[i][j] != goalValue(i, j)) dist += 1;
            }
        }
        return dist;
    }
    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dist = 0;
        int[] tmp;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 0 represents the blank square
                if (board[i][j] != 0) {
                    // find goal location of board[i][j]
                    tmp = goalLoc(board[i][j]);
                    dist += Math.abs(tmp[0] - i) + Math.abs(tmp[1] - j);
                }
            }
        }
        return dist;
    }
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
    // does this board equal y?
    public boolean equals(Object y) {
        // Is y null?
        if (y == null) return false;
        // Is y an instance of Board?
        if (!(y instanceof Board)) return false;
        Board that = (Board) y;
        // Do they have the same size?
        if (n != that.dimension()) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != that.board[i][j]) return false;
            }
        }
        return true;
    }
    // swap loc(rowA, colA) with loc(rowB, colB)
    private Board swap(int rowA, int colA, int rowB, int colB) {
        // swap tiles A to B
        int[][] boardCopy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boardCopy[i][j] = board[i][j];
            }
        }
        int tmp = boardCopy[rowA][colA];
        boardCopy[rowA][colA] = boardCopy[rowB][colB];
        boardCopy[rowB][colB] = tmp;
        return new Board(boardCopy);
    }
    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborsList = new ArrayList<Board>();
        if (blankRow > 0) {
            Board up = swap(blankRow, blankCol, blankRow - 1, blankCol);
            neighborsList.add(up);
        }
        if (blankRow < n - 1) {
            Board down = swap(blankRow, blankCol, blankRow + 1, blankCol);
            neighborsList.add(down);
        }
        if (blankCol > 0) {
            Board left = swap(blankRow, blankCol, blankRow, blankCol - 1);
            neighborsList.add(left);
        }
        if (blankCol < n - 1) {
            Board right = swap(blankRow, blankCol, blankRow, blankCol + 1);
            neighborsList.add(right);
        }
        return neighborsList;
    }
    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // define a twin of board
        Board boardTwin;
        if (blankRow == 0) {
            // if blank is in row 0, then we swap (1,0) to (1,1)
            boardTwin = swap(1, 0, 1, 1);
        } else {
            // else, we swap (0,0) to (0,1)
            boardTwin = swap(0, 0, 0, 1);
        }
        return boardTwin;
    }

    public static void main(String[] args) {
        // unit test
        // input tiles
        StdOut.println("--- Testing input ---");
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        // read tiles to Board
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        // print string
        StdOut.println(initial);

        StdOut.println("--- Testing dimension ---");
        StdOut.println("The dimension of board is " + initial.dimension() + "\n");

        StdOut.println("--- Testing blank location ---");
        StdOut.println("The blank is at row = " + initial.blankRow + " and col = " + initial.blankCol + "\n");

        StdOut.println("--- Testing goal value of location ---");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                StdOut.print(initial.goalValue(i, j) + "  ");
            }
            StdOut.println();
        }

        StdOut.println("--- Testing hamming distance ---");
        StdOut.println("The hamming distance = " + initial.hamming() + "\n");
        // test loc method
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int num = i * n + j;
                int[] tmp = initial.goalLoc(num);
                StdOut.println("location of " + num + " = (" + tmp[0] + ", " + tmp[1] + ")");
            }
        }
        StdOut.println();

        StdOut.println("--- Testing Manhattan distance ---");
        StdOut.println("The Manhattan distance = " + initial.manhattan() + "\n");

        StdOut.println("--- Testing isGoal ---");
        StdOut.println("board is goal = " + initial.isGoal() + "\n");

        StdOut.println("--- Testing isEqual---");
        Board copy = initial.swap(0, 0, 0, 0);
        StdOut.println("initial equals copy = " + initial.equals(copy));
        Board swapBoard = initial.swap(0, 0, 0, 1);
        StdOut.println("initial equals swapBoard = " + initial.equals(swapBoard));
        StdOut.println();

        // test Iterator
        for (Board b : initial.neighbors()) {
            StdOut.println(b);
        }


    }
}
