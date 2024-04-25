package zaarour.dev.w1_percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Performs Monte Carlo simulation to estimate the percolation threshold.
 * <p>
 * This class conducts a computational experiment to estimate the percolation threshold:
 * <ol>
 *   <li>Initializes all sites as blocked.</li>
 *   <li>Repeats randomly opening sites until the system percolates.</li>
 * </ol>
 * </p>
 * <p>
 * Estimates the percolation threshold by averaging over multiple trials.
 * Computes the sample mean and standard deviation for accuracy.
 * </p>
 *
 */
public class PercolationStats {
    private final int order;
    private final int trials;
    private final double[] thresholds;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        validate(n, trials);

        this.order = n;
        this.trials = trials;
        this.thresholds = new double[trials];

        for (int t = 0; t < trials; ++t) {
            Percolation percolation = new Percolation(n);

            int openSitesCount = 0;
            while (!percolation.percolates()) {
                openRandomNode(percolation);
                openSitesCount++;
            }

            this.thresholds[t] = (double) openSitesCount / (double) (n * n);
            System.out.printf(
                    "Trial %3d/%-3d completed. Percolation Threshold (p*): %.5f, Open Sites: %,d\n",
                    t + 1, trials, this.thresholds[t], openSitesCount);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(this.trials);
    }


    // Open random nodes
    private void openRandomNode(Percolation percolation) {
        int c, r;
        do {
            c = StdRandom.uniformInt(1, this.order + 1);
            r = StdRandom.uniformInt(1, this.order + 1);
        } while (percolation.isOpen(c, r));
        percolation.open(c, r);
    }

    private static void validate(int order, int trials) {
        if (order <= 0 || trials <= 0) {
            throw new IllegalArgumentException("order and trials should be greater than 0");
        }
    }

    public static void main(String[] args) {
        int N = 4096;
        int trials = 100;

        Stopwatch timeW = new Stopwatch();

        PercolationStats ps = new PercolationStats(N, trials);

        StdOut.println("mean:\t\t\t\t\t= " + ps.mean());
        StdOut.println("stddev:\t\t\t\t\t= " + ps.stddev());

        double var = ps.confidenceLo();
        StdOut.println("95% confidence interval = " + var + ", " + ps.confidenceHi());

        double time = timeW.elapsedTime();
        StdOut.println("The total time is:\t" + time);
    }
}
