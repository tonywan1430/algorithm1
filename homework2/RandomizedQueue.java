import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int n;

    public RandomizedQueue() {
        // this leads to an unavoidable compiler warning;
        array = (Item[]) new Object[1];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++) {
            temp[i] = array[i];
        }
        array = temp;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("item should not be null");
        if (n == array.length) resize(2 * n);
        array[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("queue is under flow");
        int k = StdRandom.uniformInt(0, n);
        Item temp = array[k];
        array[k] = array[n - 1];
        array[n - 1] = null;
        n--;
        if ((n > 0) && (n == array.length / 4)) resize(array.length / 2);
        return temp;
    }

    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException("queue is under flow");
        int k = StdRandom.uniformInt(0, n);
        return array[k];
    }

    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        private int index = 0;
        private Item[] arrayCopy;

        public RandomArrayIterator() {
            arrayCopy = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                arrayCopy[i] = array[i];
            }
            // shuffle array copy
            StdRandom.shuffle(arrayCopy);
        }

        public boolean hasNext() {
            return index < arrayCopy.length;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("no next");
            return arrayCopy[index++];
        }

        public void remove() {
            throw new UnsupportedOperationException("remove not supported.");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rqueue = new RandomizedQueue<Integer>();
        StdOut.println("----test initialization----");
        StdOut.println(rqueue.isEmpty());
        StdOut.println(rqueue.size());

        StdOut.println("----test: enqueue----");
        int num = 10;
        for (int i = 0; i < num; i++) {
            rqueue.enqueue(i);
        }
        StdOut.println(rqueue.isEmpty());
        StdOut.println(rqueue.size());

        StdOut.println("----test: dequeue----");
        while (!rqueue.isEmpty()) {
            StdOut.println("dequeueing " + rqueue.dequeue() + " size is " + rqueue.size());
        }
        StdOut.println("----test: sample----");
        for (int i = 0; i < num; i++) {
            rqueue.enqueue(i);
        }
        int[] stats = new int[rqueue.size()];
        int num2 = 10000;
        for (int i = 0; i < num2; i++) {
            int s = (int) rqueue.sample();
            stats[s] += 1;
        }
        for (int i = 0; i < stats.length; i++) {
            StdOut.println(i + " appears " + stats[i]);
        }

        StdOut.println("----test for Iterator----");
        for (Object a : rqueue) {
            StdOut.println(a);
        }

//        StdOut.println("----test for dequeue from empty----");
//        while (!rqueue.isEmpty()) {
//            rqueue.dequeue();
//        }
//        try {
//            rqueue.dequeue();
//        } catch (Exception error) {
//            StdOut.println(error);
//        }
    }


}
