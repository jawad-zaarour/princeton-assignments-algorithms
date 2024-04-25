package zaarour.dev.w3_collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The {@code BruteCollinearPoints} class examines sets of four points at a time to determine
 * which sets of four points form a line segment. This is a brute-force approach with a complexity
 * of &Theta(n^4).
 */
public class BruteCollinearPoints {

    private final LineSegment[] segments;

    /**
     * Creates a {@code BruteCollinearPoints} object that finds all line segments containing 4
     * points.
     *
     * @param points an array of points.
     * @throws IllegalArgumentException if the input array is null, contains any null points, or
     *                                  contains duplicates.
     */
    public BruteCollinearPoints(Point[] points) {
        validateNotNull(points);
        validatePoints(points);

        int n = points.length;
        Point[] pointsCopy = Arrays.copyOf(points, n);
        MergeX.sort(pointsCopy);
        validateNoDuplicates(pointsCopy);

        ArrayList<LineSegment> foundSegments = new ArrayList<>();

        for (int p = 0; p < n - 3; p++) {
            for (int q = p + 1; q < n - 2; q++) {
                for (int r = q + 1; r < n - 1; r++) {
                    for (int s = r + 1; s < n; s++) {
                        if (areCollinear(pointsCopy[p], pointsCopy[q], pointsCopy[r], pointsCopy[s]))
                        {
                            foundSegments.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
            }
        }

        segments = foundSegments.toArray(new LineSegment[0]);
    }

    /**
     * Ensures that the provided array of points is not null.
     *
     * @param points the array to check.
     * @throws IllegalArgumentException if the points array is null.
     */
    private static void validateNotNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
    }

    /**
     * Validates that no points in the array are null.
     *
     * @param points the array to check.
     * @throws IllegalArgumentException if any point in the array is null.
     */
    private static void validatePoints(Point[] points) {
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Null points detected in the array.");
            }
        }
    }

    /**
     * Checks for duplicate points in the sorted array.
     *
     * @param points the sorted array of points to check.
     * @throws IllegalArgumentException if any duplicate points are found.
     */
    private static void validateNoDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
    }

    /**
     * Determines if four points are collinear(same straight line), by comparing slopes between them.
     *
     * @param p first point
     * @param q second point
     * @param r third point
     * @param s fourth point
     * @return {@code true} if the four points are collinear, {@code false} otherwise.
     */
    private static boolean areCollinear(Point p, Point q, Point r, Point s) {
        return p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s);
    }

    /**
     * Returns the number of line segments found.
     *
     * @return the number of line segments.
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * Returns the line segments found.
     *
     * @return an array of line segments.
     */
    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
