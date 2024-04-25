package zaarour.dev;

import org.junit.jupiter.api.Test;
import zaarour.dev.w1_percolation.Percolation;

import static org.junit.jupiter.api.Assertions.*;

public class PercolationTest {

    @Test
    void testOpenWithInvalidArguments() {
        Percolation percolation = new Percolation(10);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.open(-1, 5)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.open(11, 5)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.open(0, 5)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.open(5, -1)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.open(5, 11)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.open(5, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.open(Integer.MIN_VALUE, Integer.MIN_VALUE)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.open(Integer.MAX_VALUE, Integer.MAX_VALUE))
        );
    }

    @Test
    void testIsOpenMethodWithInvalidArguments() {
        Percolation percolation = new Percolation(10);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isOpen(-1, 5)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isOpen(11, 5)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isOpen(0, 5)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isOpen(5, -1)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isOpen(5, 11)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isOpen(5, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isOpen(Integer.MIN_VALUE, Integer.MIN_VALUE)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isOpen(Integer.MAX_VALUE, Integer.MAX_VALUE))
        );
    }

    @Test
    void testIsFullMethodWithInvalidArguments() {
        Percolation percolation = new Percolation(10);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isFull(-1, 5)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isFull(11, 5)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isFull(0, 5)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isFull(5, -1)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isFull(5, 11)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isFull(5, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isFull(Integer.MIN_VALUE, Integer.MIN_VALUE)),
                () -> assertThrows(IllegalArgumentException.class, () -> percolation.isFull(Integer.MAX_VALUE, Integer.MAX_VALUE))
        );
    }

    @Test
    public void testPercolates() {
        Percolation percolation = new Percolation(5);
        assertFalse(percolation.percolates());
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(4, 1);
        percolation.open(5, 1);

        assertEquals(5, percolation.numberOfOpenSites());
        assertTrue(percolation.isOpen(1, 1));
        assertTrue(percolation.percolates());
        assertTrue(percolation.isFull(4, 1));
    }

}