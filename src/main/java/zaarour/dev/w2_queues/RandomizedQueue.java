package zaarour.dev.w2_queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2]; // initial capacity
        n = 0;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot enqueue null item");
        }
        if (n == items.length) {
            resize(2 * items.length); // double the array size if full
        }
        items[n++] = item;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }
        int randomIndex = StdRandom.uniformInt(0, n);
        return items[randomIndex];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }
        int randomIndex = StdRandom.uniformInt(0, n);
        Item item = items[randomIndex];
        items[randomIndex] = items[n - 1]; // replace removed item with last item
        items[n - 1] = null; // avoid loitering
        n--;
        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    // resize the array
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>();
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private int i = n;
        private Item[] shuffledItems;

        public RandomizedQueueIterator() {
            shuffledItems = (Item[]) new Object[n];
            System.arraycopy(items, 0, shuffledItems, 0, n);
            StdRandom.shuffle(shuffledItems);
        }

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return shuffledItems[--i];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args){}
}
