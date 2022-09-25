import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n;
    private int trials;
    private double[] sitesCounts;
    private double conf_95 = 1.96;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("n and trials should be positive");
        this.trials = trials;
        this.sitesCounts = new double[trials];
        this.n = n;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                if (!p.isOpen(row, col)) p.open(row, col);
            }
            sitesCounts[i] = (double) p.numberOfOpenSites() / (this.n * this.n);
        }
    }

    public double mean() {
        return StdStats.mean(sitesCounts);
    }

    public double stddev() {
        return StdStats.stddev(sitesCounts);
    }

    public double confidenceLo() {
        return mean() - conf_95 * stddev() / Math.sqrt(this.trials);
    }

    public double confidenceHi() {
        return mean() + conf_95 * stddev() / Math.sqrt(this.trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats myStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + myStats.mean());
        StdOut.println("stddev                  = " + myStats.stddev());
        StdOut.println("95% confidence interval = [" + myStats.confidenceLo() + ", " + myStats.confidenceHi() + "]");

    }
}
