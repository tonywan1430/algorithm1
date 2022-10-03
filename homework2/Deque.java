import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> prev;
    }

    // instance variables
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    // constructor
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // test if deque is empty;
    public boolean isEmpty() {
        return n == 0;
    }

    // return the size of deque;
    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("item should not be null");
        Node<Item> newfirst = new Node<Item>();
        newfirst.item = item;
        newfirst.prev = null;
        if (isEmpty()) {
            newfirst.next = null;
            first = newfirst;
            last = newfirst;
        } else {
            newfirst.next = first;
            first.prev = newfirst;
            first = newfirst;
        }
        n++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("item should not be null");
        Node<Item> newlast = new Node<Item>();
        newlast.item = item;
        newlast.next = null;
        if (isEmpty()) {
            newlast.prev = null;
            first = newlast;
            last = newlast;
        } else {
            last.next = newlast;
            newlast.prev = last;
            last = newlast;
        }
        n++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("deque underflow");
        Item item = first.item;
        first = first.next;
        n--;
        // set last to null if we remove the last node
        // in this case, first is null so we do NOT need to set first.prev = null;
        if (isEmpty()) last = null;
        // first is NOT null, so we set first.prev = null;
        else first.prev = null;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("deque underflow");
        Item item = last.item;
        last = last.prev;
        n--;
        // set last to null if we remove the last node
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current;
        public ListIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("no next");
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove not supported.");
        }

    }

    public static void main(String[] args) {
        // initial a new deque
        StdOut.println("----test for initialization----");
        Deque<Integer> myDeque = new Deque<Integer>();
        StdOut.println(myDeque.isEmpty());
        StdOut.println("size of myDeque is " + myDeque.size());

        // add first;
        StdOut.println("----test for adding first----");
        int num = 10;
        for (int i = 0; i < num; i++) {
            myDeque.addFirst(i);
        }
        StdOut.println(myDeque.isEmpty());
        StdOut.println("size of myDeque is " + myDeque.size());

        // remove first;
        StdOut.println("----test for adding first and removing first----");
        while (!myDeque.isEmpty()) {
            StdOut.println(myDeque.removeFirst());
        }

        // add first and remove last;
        StdOut.println("----test for adding first and removing last----");
        for (int i = 0; i < num; i++) {
            myDeque.addFirst(i);
        }
        while (!myDeque.isEmpty()) {
            StdOut.println(myDeque.removeLast());
        }

        // add last and remove last;
        StdOut.println("----test for adding last and removing last----");
        for (int i = 0; i < num; i++) {
            myDeque.addLast(i);
        }
        while (!myDeque.isEmpty()) {
            StdOut.println(myDeque.removeLast());
        }

        // add last and remove first;
        StdOut.println("----test for adding last and removing first----");
        for (int i = 0; i < num; i++) {
            myDeque.addLast(i);
        }
        while (!myDeque.isEmpty()) {
            StdOut.println(myDeque.removeFirst());
        }

        StdOut.println("----test for Iterator----");
        for (int i = 0; i < num; i++) {
            myDeque.addFirst(i);
        }
        for (Object a : myDeque) {
            StdOut.println(a);
        }
//        // test add null
//        StdOut.println("----test for enqueueing null----");
//        try {
//            myDeque.addFirst(null);
//        } catch (Exception error) {
//            StdOut.println(error);
//        }
//        // test remove first from an empty deque
//        StdOut.println("----test for removing from first when empty----");
//        while (!myDeque.isEmpty()) {
//            myDeque.removeFirst();
//        }
//        try {
//            myDeque.removeFirst();
//        } catch (Exception error) {
//            StdOut.println(error);
//        }
//        // test remove last from an empty deque
//        StdOut.println("----test for removing from last when empty----");
//        try {
//            myDeque.removeLast();
//        } catch (Exception error) {
//            StdOut.println(error);
//        }


    }


}
