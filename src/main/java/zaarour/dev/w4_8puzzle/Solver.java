import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Solver} class provides methods to find a solution to the 8-puzzle problem
 * using the A* search algorithm.
 */
public class Solver {
    private final boolean solvable;
    private final int moves;
    private final Iterable<Board> solution;

    // Helper class for search nodes
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode prev;
        private final int priority;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.priority = moves + board.manhattan(); // Manhattan priority function
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Initial board cannot be null.");

        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        pq.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode lastNode = null;

        // A* algorithm
        while (true) {
            SearchNode minNode = pq.delMin();
            if (minNode.board.isGoal()) {
                solvable = true;
                moves = minNode.moves;
                lastNode = minNode;
                break;
            }

            SearchNode twinMinNode = twinPQ.delMin();
            if (twinMinNode.board.isGoal()) {
                solvable = false;
                moves = -1;
                break;
            }

            for (Board neighbor : minNode.board.neighbors()) {
                if (minNode.prev == null || !neighbor.equals(minNode.prev.board)) {
                    pq.insert(new SearchNode(neighbor, minNode.moves + 1, minNode));
                }
            }

            for (Board twinNeighbor : twinMinNode.board.neighbors()) {
                if (twinMinNode.prev == null || !twinNeighbor.equals(twinMinNode.prev.board)) {
                    twinPQ.insert(new SearchNode(twinNeighbor, twinMinNode.moves + 1, twinMinNode));
                }
            }
        }

        // If solvable, construct the solution
        if (solvable) {
            Stack<Board> solutionStack = new Stack<>();
            while (lastNode != null) {
                solutionStack.push(lastNode.board);
                lastNode = lastNode.prev;
            }
            solution = solutionStack;
        }
        else {
            solution = null;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
