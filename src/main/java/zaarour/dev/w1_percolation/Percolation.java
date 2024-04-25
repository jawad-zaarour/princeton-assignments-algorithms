package zaarour.dev.w1_percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * The Percolation class models a percolation system using an n-by-n grid of sites.
 * Each site can be either open or blocked. A full site is an open site that can be connected
 * to an open site in the top row via a chain of neighboring (left, right, up, down) open sites.
 * The system percolates iff top and bottom are connected by open sites.
 *
 */
public class Percolation {

    private final WeightedQuickUnionUF grid;
    private final WeightedQuickUnionUF full;
    private final int order;
    private final int virtualTop;
    private final int virtualBottom;
    private int openSites;
    private final boolean[] openNodes;

    /**
     * Initializes a Percolation object with an n-by-n grid, where all sites are initially blocked.
     *
     * @param n the size of the grid (n-by-n)
     * @throws IllegalArgumentException if n is less than or equal to zero
     */
    public Percolation(int n) {
        requireArgument(n > 0);
        this.grid = new WeightedQuickUnionUF(n * n + 2);
        this.full = new WeightedQuickUnionUF(n * n + 1);
        this.order = n;
        this.openSites = 0;
        this.virtualTop = arrayIndex(n, n) + 1;
        this.virtualBottom = arrayIndex(n, n) + 2;
        this.openNodes = new boolean[n * n];
    }

    /**
     * Opens the site at the specified row and column if it is not already open.
     *
     * @param row the row of the site to be opened
     * @param col the column of the site to be opened
     */
    public void open(int row, int col) {
        validateIndices(row, col);

        if (!isOpen(row, col)) {

            int index = arrayIndex(row, col);
            openNodes[index] = true;
            openSites++;

            // Connect with virtual top or bottom if applicable
            if (row == 1) {
                grid.union(virtualTop, index);
                full.union(virtualTop, index);
            }
            if (row == order) {
                grid.union(virtualBottom, index);
            }

            // Connect with neighboring open sites
            int[][] neighbors = {
                    {row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}
            };
            for (int[] neighbor : neighbors) {
                int r = neighbor[0];
                int c = neighbor[1];
                if (isValidIndex(r, c) && isOpen(r, c)) {
                    grid.union(arrayIndex(r, c), index);
                    full.union(arrayIndex(r, c), index);
                }
            }
        }
    }

    /**
     * Checks if the site at the specified row and column is open.
     *
     * @param row the row of the site
     * @param col the column of the site
     * @return true if the site is open, false otherwise
     */
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return openNodes[arrayIndex(row, col)];
    }

    /**
     * Checks if the site at the specified row and column is full.
     *
     * @param row the row of the site
     * @param col the column of the site
     * @return true if the site is full, false otherwise
     */
    public boolean isFull(int row, int col) {
        int index = arrayIndex(row, col);
        return isOpen(row, col) && grid.find(index) == grid.find(virtualTop);
    }

    /**
     * Returns the number of open sites in the grid.
     *
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * Checks if the system percolates.
     *
     * @return true if the system percolates, false otherwise
     */
    public boolean percolates() {
        return grid.find(virtualTop) == grid.find(virtualBottom);
    }

    private int arrayIndex(int row, int col) {
        validateIndices(row, col);
        return (row - 1) * order + (col - 1);
    }

    private void validateIndices(int row, int col) {
        if (!isValidIndex(row, col))
            throw new IllegalArgumentException(
                    "Invalid indices provided: [" + row + ", " + col + "]."
            );
    }

    private boolean isValidIndex(int row, int col) {
        return row > 0 && col > 0 && row <= order && col <= order;
    }

    private void requireArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException("Grid order must be greater than 0");
        }
    }
}
