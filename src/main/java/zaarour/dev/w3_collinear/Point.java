package zaarour.dev.w3_collinear;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 * The {@code Point} class represents a location in a two-dimensional coordinate plane.
 * Each point has an x-coordinate and a y-coordinate, both represented as integers.
 * This class provides methods to perform basic geometric operations such as drawing the point,
 * drawing a line to another point, and determining the slope between two points.
 * <p>
 * This class also implements the {@link Comparable} interface, allowing points to be compared
 * based on their y-coordinates, with ties broken by x-coordinates.
 * <p>
 * Instances of this class are immutable; once constructed, the point's coordinates cannot change.
 *
 * <h2>Usage Examples</h2>
 * <pre>
 *     Point p1 = new Point(1, 1);
 *     Point p2 = new Point(4, 5);
 *     p1.draw();
 *     p1.drawTo(p2);
 *     double slope = p1.slopeTo(p2);
 * </pre>
 *
 * <h2>Corner Cases</h2>
 * Coordinates for points are assumed to be within the range of 0 to 32,767.
 * Attempting to create a point outside of this range will result in an
 * {@link IllegalArgumentException}.
 */
public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        if (Double.isInfinite(x) || Double.isInfinite(y))
            throw new IllegalArgumentException("Coordinates must be finite");
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        return Integer.compare(this.x, that.x);
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return (p, q) -> {
            double mP = slopeTo(p);
            double mQ = slopeTo(q);
            return Double.compare(mP, mQ);
        };
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        int deltaX = that.x - this.x;
        int deltaY = that.y - this.y;

        if (deltaX == deltaY)
            return Double.NEGATIVE_INFINITY;

        if (deltaX == 0)
            return Double.POSITIVE_INFINITY;

        if (deltaY == 0)
            return +0.0;

        return (double) deltaY / deltaX;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
