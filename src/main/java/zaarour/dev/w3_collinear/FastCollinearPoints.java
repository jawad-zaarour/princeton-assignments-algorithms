package zaarour.dev.w3_collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The {@code FastCollinearPoints} class finds all line segments containing four or more points
 * on the same straight line. This implementation uses a sorting-based approach.
 * It first sorts the array of points, and then for each point, it sorts the rest of the points
 * based on the slopes they make with it. A combination of at least four collinear points is then
 * considered to form a line segment.
 *
 */
public class FastCollinearPoints {

    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

    /**
     * Creates a {@code FastCollinearPoints} object and computes all line segments containing 4 or
     * more points.
     *
     * @param points the array of points
     * @throws IllegalArgumentException if the input array is {@code null}, contains {@code null}
     *                                  points,
     *                                  or has duplicate points.
     */
    public FastCollinearPoints(Point[] points) {
        validateNotNull(points);
        validatePoints(points);

        int n = points.length;
        Point[] pointsCopy = Arrays.copyOf(points, n);
        MergeX.sort(pointsCopy);
        validateNoDuplicates(pointsCopy);

        for (int i = 0; i < n - 3; i++) {
            Point p = pointsCopy[i];
            Point[] pointsSortedBySlope = Arrays.copyOf(pointsCopy, n);

            // Sort points according to the slopes they make with p
            MergeX.sort(pointsSortedBySlope, p.slopeOrder());

            int j = 1;
            while (j < pointsSortedBySlope.length) {
                ArrayList<Point> candidates = new ArrayList<>();
                final double SLOPE_REF = p.slopeTo(pointsSortedBySlope[j]);
                do {
                    candidates.add(pointsSortedBySlope[j++]);
                } while (j < pointsSortedBySlope.length
                        && p.slopeTo(pointsSortedBySlope[j]) == SLOPE_REF);

                if (candidates.size() >= 3 && p.compareTo(candidates.get(0)) < 0) {
                    Point last = candidates.get(candidates.size() - 1);
                    lineSegments.add(new LineSegment(p, last));
                }
            }
        }
    }

    private static void validateNotNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
    }

    private static void validatePoints(Point[] points) {
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Null points detected in the array.");
            }
        }
    }

    private static void validateNoDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
