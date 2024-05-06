import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The {@code Board} class represents an n-by-n board with sliding tiles. 
 * It provides methods to create, manipulate, and analyze the board.
 * 
 * The goal of the 8-puzzle is to rearrange the tiles so that they are in row-major order, 
 * using as few moves as possible. Tiles can be slid either horizontally or vertically 
 * into the blank square.
 * 
 * This class includes methods to calculate Hamming and Manhattan distances, 
 * check if the board is in the goal state, determine solvability, 
 * and find neighboring boards.
 */
public class Board {

    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if
        (
                tiles == null ||
                        tiles.length != tiles[0].length ||
                        tiles.length < 2 ||
                        tiles.length >= 128
        ) {
            throw new IllegalArgumentException(
                    "Invalid input: must be a square matrix with size between 2 and 127");
        }

        this.n = tiles.length;
        this.tiles = new int[n][n];

        // Copy the tiles into the board
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }


    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDistance = 0;
        int goalValue = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != goalValue && tiles[i][j] != 0) {
                    hammingDistance++;
                }
                goalValue++;
            }
        }
        return hammingDistance;
    }


    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = tiles[i][j];
                if (value != 0) {
                    int goalRow = (value - 1) / n;
                    int goalCol = (value - 1) % n;
                    manhattanDistance += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board otherBoard = (Board) y;
        if (this.n != otherBoard.n) return false;
        return Arrays.deepEquals(this.tiles, otherBoard.tiles);
    }


    // string representation of this board
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(" ").append(tiles[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // Initialize an ArrayList to store neighbors
        List<Board> neighbors = new ArrayList<>();

        // Find the location of the blank tile (0)
        int blankRow = 0;
        int blankCol = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    break;
                }
            }
        }

        // Check all four possible moves for the blank tile (up, down, left, right)
        // and create a new board for each valid move

        // Up move
        if (blankRow > 0) {
            Board newBoard = new Board(copyBoard(tiles));
            newBoard.tiles[blankRow][blankCol] = newBoard.tiles[blankRow - 1][blankCol];
            newBoard.tiles[blankRow - 1][blankCol] = 0;
            neighbors.add(newBoard);
        }

        // Down move
        if (blankRow < n - 1) {
            Board newBoard = new Board(copyBoard(tiles));
            newBoard.tiles[blankRow][blankCol] = newBoard.tiles[blankRow + 1][blankCol];
            newBoard.tiles[blankRow + 1][blankCol] = 0;
            neighbors.add(newBoard);
        }

        // Left move
        if (blankCol > 0) {
            Board newBoard = new Board(copyBoard(tiles));
            newBoard.tiles[blankRow][blankCol] = newBoard.tiles[blankRow][blankCol - 1];
            newBoard.tiles[blankRow][blankCol - 1] = 0;
            neighbors.add(newBoard);
        }

        // Right move
        if (blankCol < n - 1) {
            Board newBoard = new Board(copyBoard(tiles));
            newBoard.tiles[blankRow][blankCol] = newBoard.tiles[blankRow][blankCol + 1];
            newBoard.tiles[blankRow][blankCol + 1] = 0;
            neighbors.add(newBoard);
        }

        return neighbors;
    }

    // Helper method to create a copy of the board
    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = copyBoard(tiles);

        // Find the first non-blank tile
        int row = 0;
        int col = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (twinTiles[i][j] != 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        // Find the second non-blank tile (ensure it's not in the same row)
        int secondRow = (row + 1) % n;
        int secondCol = 0;
        if (twinTiles[secondRow][0] == 0) {
            secondCol = 1;
        }

        // Swap the positions of the two tiles
        int temp = twinTiles[row][col];
        twinTiles[row][col] = twinTiles[secondRow][secondCol];
        twinTiles[secondRow][secondCol] = temp;

        return new Board(twinTiles);
    }


    public static void main(String[] args) {

    }

}
