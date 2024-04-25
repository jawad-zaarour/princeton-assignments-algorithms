package zaarour.dev.w2_queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque is a generalization of a stack and a queue
 * that supports adding and removing items from either the front or the back of the data structure.
 *
 * @param <Item> the type of elements in the deque
 */
public class Deque<Item> implements Iterable<Item> {

    private int n;          // size of the deque
    private Node first;     // beginning of deque
    private Node last;      // end of deque

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Null item");
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = first;
        if (isEmpty()) {
            last = newNode; // if deque is empty, first and last point to the same node
        }
        else {
            first.prev = newNode;
        }
        first = newNode;
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Null item");
        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = last;
        if (isEmpty()) {
            first = newNode; // if deque is empty, first and last point to the same node
        }
        else {
            last.next = newNode;
        }
        last = newNode;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        first = first.next;
        if (first == null)
            last = null; // if deque becomes empty after removal
        else
            first.prev = null;
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        last = last.prev;
        if (last == null)
            first = null; // if deque becomes empty after removal
        else
            last.next = null;
        n--;
        return item;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No more items to return");
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supported");
        }
    }

    public static void main(String[] args){}

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

}

