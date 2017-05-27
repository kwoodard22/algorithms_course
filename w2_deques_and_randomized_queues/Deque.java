/*
Write a generic data type for a deque and a randomized queue. The goal of this
assignment is to implement elementary data structures using arrays and linked 
lists, and to introduce you to generics and iterators.

Dequeue. A double-ended queue or deque (pronounced "deck") is a generalization
of a stack and a queue that supports adding and removing items from either the 
front or the back of the data structure. Create a generic data type Deque that 
implements the following API:

Do not call library functions except those in StdIn, StdOut, StdRandom, 
java.lang, java.util.Iterator, and java.util.NoSuchElementException.

With an array implementation:
you can have both overflow and underflow
you should set deleted elements to null 
With a linked-list implementation:
you can have underflow
overflow is a global out-of-memory condition
there is no reason to set deleted elements to null


*/

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node previous;
    }
    private Node first;
    private Node last;
    private int N;
    
    // Construct an empty deque
    public Deque() {
         first = null;
         last = null;
         N = 0;
    }
    
    // Is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }
    
    // Return number of items on the deque
    public int size() {
        return N;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        if (isEmpty()) {
            createFirstNode(item);
        } else {
            Node oldFirst = first;
            Node n = new Node();
            n.item = item;
            n.next = oldFirst;
            n.previous = null;
            first = n;
            oldFirst.previous = first;
            N++;
        }
    }
    // add the item to the end
    public void addLast(Item item) {
        if (isEmpty()) {
            createFirstNode(item);
        } else {
            Node oldLast = last;
            Node n = new Node();
            n.item = item;
            n.next = null;
            n.previous = oldLast;
            last = n;
            oldLast.next = last;
            N++;
        }
    
    }
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            // raise error
        } else {
            Node oldFirst = first;
            Node n = oldFirst.next
            n.previous = null;
            first = n;
            N--;
        }
    }
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            // raise error
        } else {
            Node oldLast = last;
            Node n = oldLast.previous;
            n.next = null;
            last = n;
            N--;
    }
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
    
        for (Iterator i= new IteratorTest(); i.hasNext(); ) {
            Integer mynumber = (Integer)i.next();
            System.out.println("Next Number is: " + mynumber);
        }
    }

    
    
    }
     // unit testing (optional)
    //public static void main(String[] args) {}
    
    /*************************
    ** Private utility methods
    *************************/
    private void createFirstNode(Item item) {
        Node n = new Node();
        n.item = item;
        n.next = null;
        n.previous = null;
        first = n;
        last = n;
        N++;
    }
    
}