import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    private final Stack<Board> solutions;
    // private Queue<Board> solutionStackTwin;
    private Node solutionNode = null;
    // private final boolean solvable;
    private boolean solved, solvedTwin;

    // define method for search node of the game
    private static class Node implements Comparable<Node> {
        Board board;
        // the number of moves made to reach the board
        int moves;
        // previous search node
        Node prevNode;
        int manDist;

        public Node(Board board, int moves, Node prevNode) {
            this.board = board;
            this.moves = moves;
            // previous Node
            this.prevNode = prevNode;
            manDist = board.manhattan();
        }

        public int compareTo(Node that) {
            /*
            define order:
            compare priority order
            if priority equals: we compare hamming distance
            */
            if (this.priority() == that.priority()) {
                return this.manDist - that.manDist;
            }
            else return this.priority() - that.priority();
        }

        public int priority() {
            return manDist + moves;
        }

        public Board getBoard() {
            return this.board;
        }

        public int getMoves() {
            return this.moves;
        }

        public Node prev() {
            return this.prevNode;
        }
    }


    public Solver(Board initial) {
        /*
        Strategy:
        1. Start with initial and one twin (modified by swapping a pair)
        2. put initial node and twin node into corresponding MinPQ
        3. while solved and twinSolved false:
            3.1 pop Node from pq,
            3.2. If current board is Goal, mark solved and break
            3.3 Else, check its neighbors, if not equal to previous Node, insert into pq
            3.4* apply previous steps to twin case
         4. if initial is solved, we have a solution; if twin is solved, then initial is not solvable
         */

        if (initial == null) throw new IllegalArgumentException("argument is null");
        // initial new MinPQ for initial and its twin(swap);
        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> pqTwin = new MinPQ<>();

        // initial solutions Stack;
        solutions = new Stack<>();

        // define an initial search node;
        Board twin = initial.twin();
        Node initialNode = new Node(initial, 0, null);
        Node initialNodeTwin = new Node(twin, 0, null);

        // push search node (original board) into MinPQ;
        pq.insert(initialNode);
        pqTwin.insert(initialNodeTwin);

        // start while loop
        while (!(solved || solvedTwin)) {
            /* ------- For initial Board ------------ */
            // pop the minimum priority Node as current Node;
            Node cur = pq.delMin();
            // get the current Board in the current Node;
            Board curBoard = cur.getBoard();
            // get the number of moves and save as curMoves;
            int curMoves = cur.getMoves();
            // get previous Board, there is no previous Board for initial node
            Board prevBoard = null;
            if (cur.prev() != null) {
                // otherwise we same the Board in previous Node as prevBoard
                prevBoard = cur.prev().getBoard();
            }
            // test if current board equals to goal
            if (curBoard.isGoal()) {
                // then the puzzle is solved, we break the while loop;
                solved = true;
                solutionNode = cur;
                break;
            }

            // otherwise we iterate push the neighbors of current Board into MinPQ;
            else {
                for (Board next : curBoard.neighbors()) {
                    // skip the initial case (without prev) or the neighbor equals previous Board
                    if (cur.prev() != null && next.equals(prevBoard)) continue;
                    Node neighbor = new Node(next, curMoves + 1, cur);
                    pq.insert(neighbor);
                }
            }

            /* ------- For twin Board ------------ */
            // pop from twin MinPQ
            Node curTwin = pqTwin.delMin();
            // get the current Board
            Board curBoardTwin = curTwin.getBoard();
            // get the number of moves
            int curMovesTwin = curTwin.getMoves();

            // get previous Board
            Board prevBoardTwin = null;
            if (curTwin.prev() != null) {
                prevBoardTwin = curTwin.prev().getBoard();
            }
            // test if current Board is goal
            if (curBoardTwin.isGoal()) {
                // then the twin Board is solved, we break the while loop;
                solvedTwin = true;
                break;
            }

            else {
                for (Board nextTwin : curBoardTwin.neighbors()) {
                    // skip the initial case (without prev) or the neighbor equals previous Board
                    if (curTwin.prev() != null && nextTwin.equals(prevBoardTwin)) continue;
                    Node neighbor = new Node(nextTwin, curMovesTwin + 1, curTwin);
                    pqTwin.insert(neighbor);
                }
            }

        }
        /* -----After while loop ------- */

    }

    public boolean isSolvable() {
        return !solvedTwin;
    }

    public int moves() {
        if (!isSolvable()) return -1;
        return solutionNode.getMoves();
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Node cur = solutionNode;
        while (cur != null) {
            solutions.push(cur.getBoard());
            cur = cur.prev();
        }
        return solutions;
    }

    public static void main(String[] args) {

        StdOut.println("--- Testing input the Board ---");
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println(initial);

        StdOut.println("--- Testing Solver ---");
        Solver solver = new Solver(initial);

        StdOut.println("--- Testing isSolvable and solutions ---");
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Board is solvable");
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
