import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // sites: n by n matrix,
    // contains 1 if cell is open, 0 if blocked
    // sites row or col index range: [0, n - 1]
    private boolean[][] sites;
    private final int n;
    private int countOpen;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;
    public Percolation(int n) {
        // creates n-by-n boolean grid, with all sites initially blocked
        // false as blocked, true as open
        if (n <= 0) throw new IllegalArgumentException("n should be positive");
        this.n = n;
        countOpen = 0;
        sites = new boolean[n][n];
        // length of uf array is n * n + 1, index range [0, n * n + 1]
        // uf: without vitual bottom to aviod backwash
        uf = new WeightedQuickUnionUF(n * n + 1);
        // uf: using vitual bottom to test percolates
        uf2 = new WeightedQuickUnionUF(n * n + 2);
        // n * n stands for virtual top node;
        // n * n + 1 stands for virtual bottom node;
    }

    private void validation(int row, int col) {
        // test if [row, col] is valid index
        // valid bounds: [1, n]
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException("index out of bounds");
        }
    }

    private int findIndex(int row, int col) {
        // find the index in UF given row and col
        validation(row, col);
        return (row - 1) * this.n + (col - 1);
    }

    public void open(int row, int col) {
        // validation test
        validation(row, col);
        // if [row, col] is already open, return;
        if (isOpen(row, col)) return;
        // open [row, col] and countOpen++
        sites[row - 1][col - 1] = true;
        countOpen++;
        // connect to virtual top (n * n) if open cell in the first row
        if (row == 1) {
            uf.union(findIndex(row, col), n * n);
            uf2.union(findIndex(row, col), n * n);
        }
        // connect to virtual bottom (n * n + 1) if open cell in the last row only in uf2
        if (row == n) {
            uf2.union(findIndex(row, col), n * n + 1);
        }
        // union upper neighbor (if exists and open)
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(findIndex(row, col), findIndex(row - 1, col));
            uf2.union(findIndex(row, col), findIndex(row - 1, col));
        }
        // union lower neighbor (if exists and open)
        if (row < n && isOpen(row + 1, col)) {
            uf.union(findIndex(row, col), findIndex(row + 1, col));
            uf2.union(findIndex(row, col), findIndex(row + 1, col));
        }
        // union left neighbor (if exists and open)
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(findIndex(row, col), findIndex(row, col - 1));
            uf2.union(findIndex(row, col), findIndex(row, col - 1));
        }
        // union right neighbor (if exists and open)
        if (col < n && isOpen(row, col + 1)) {
            uf.union(findIndex(row, col), findIndex(row, col + 1));
            uf2.union(findIndex(row, col), findIndex(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        // validation test
        validation(row, col);
        return sites[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        // validation test
        validation(row, col);
        // check if (row, col) is connected to virtual top;
        return (uf.find(findIndex(row, col)) == uf.find(n * n));
    }

    public int numberOfOpenSites() {
        return countOpen;
    }

    public boolean percolates() {
        // check if virtual bottoom is connected to virtual top;
        return (uf2.find(n * n + 1) == uf2.find(n * n));
    }

    public static void main(String[] args) {
//        StdOut.println("use percolation API");
//        int n = Integer.parseInt(args[0]);
//        Percolation p = new Percolation(n);
//        while (!p.percolates()) {
//            int row = StdRandom.uniformInt(1, n + 1);
//            int col = StdRandom.uniformInt(1, n + 1);
//            if (p.isOpen(row, col)) continue;
//            p.open(row, col);
//            StdOut.println("number of sites is " + p.numberOfOpenSites());
//        }
//        StdOut.println("final number of sites is " + p.numberOfOpenSites());
        int n = StdIn.readInt();
        Percolation p = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            p.open(row, col);
        }
        StdOut.println("number of sites is " + p.numberOfOpenSites());
        StdOut.println("percolates() = " + p.percolates());
        // test if (testRow, testCol) is full

        int testRow = 9;
        int testCol = 1;
        StdOut.println("isFull( " + testRow + ", " + testCol + ") = " + p.isFull(testRow, testCol));

    }


}
