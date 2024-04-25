package zaarour.dev;

import org.junit.jupiter.api.Test;
import zaarour.dev.w2_queues.Deque;
import zaarour.dev.w2_queues.RandomizedQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class QueueTest {

    @Test
    public void testDeque() {
        Deque<Integer> deque = new Deque<>();

        // Test size and isEmpty with empty deque
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());

        // Test addFirst and size
        deque.addFirst(1);
        assertEquals(1, deque.size());
        deque.addFirst(2);
        assertEquals(2, deque.size());

        // Test addLast and size
        deque.addLast(3);
        assertEquals(3, deque.size());
        deque.addLast(4);
        assertEquals(4, deque.size());

        // Test removeFirst and isEmpty after some removals
        assertEquals(2, deque.removeFirst());
        assertEquals(3, deque.size());
        assertEquals(1, deque.removeFirst());
        assertEquals(2, deque.size());

        // Test removeLast and isEmpty
        assertEquals(4, deque.removeLast());
        assertEquals(1, deque.size());
        assertEquals(3, deque.removeLast());
        assertTrue(deque.isEmpty());

        // Test exceptions
        assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
        assertThrows(IllegalArgumentException.class, () -> deque.addLast(null));

        // Test iterator with elements
        deque.addFirst(5);
        deque.addLast(6);
        deque.addLast(7);
        Iterator<Integer> iterator = deque.iterator();

        int[] expected = {5, 6, 7};
        int i = 0;
        while (iterator.hasNext()) {
            assertEquals(expected[i++], iterator.next());
            assertThrows(UnsupportedOperationException.class, iterator::remove);
        }

    }

    @Test
    public void testRandomizedQueue() {
        // Test enqueue and size
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < 100; i++) {
            queue.enqueue(i);
            assertEquals(i + 1, queue.size());
        }

        // Test dequeue and randomness
        int[] counts = new int[100];
        for (int i = 0; i < 100; i++) {
            int dequeued = queue.dequeue();
            counts[dequeued]++;
        }

        // Test isEmpty after dequeue
        assertTrue(queue.isEmpty());

        // Test exceptions
        assertThrows(IllegalArgumentException.class, () -> queue.enqueue(null));
        assertThrows(NoSuchElementException.class, queue::dequeue);
        assertThrows(NoSuchElementException.class, queue::sample);

        // Assert that each element was dequeued with roughly equal probability (within a reasonable margin)
        for (int i = 0; i < 100; i++) {
            assertTrue(Math.abs(counts[i] - 10) <= 15, "Element " + i + " not dequeued with roughly equal probability");
        }

        // Test sample without removing
        queue.enqueue(101);
        int sample1 = queue.sample();
        int sample2 = queue.sample();

        // Sample shouldn't modify the queue
        assertEquals(queue.size(), 1);
        assertTrue(sample1 == 101 || sample2 == 101, "Sample should return element 101");

        // Test resize behavior
        for (int i = 0; i < 100; i++) {
            queue.enqueue(i);
        }
        assertTrue(queue.size() > 2, "Queue should have resized after exceeding initial capacity");

    }

}
