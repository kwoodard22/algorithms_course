/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque
 *  Dependencies: Iterator
 *
 *  Creates Deque - adds and removes an item from either end
 *
 ******************************************************************************/

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node<Item> {
        private Item item;
        private Node next;
        private Node previous;
    }
    private Node<Item> first;
    private Node<Item> last;
    private int num = 0;
    
    // Construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }
    
    // Is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }
    
    // Return number of items on the deque
    public int size() {
        return num;
    }
    
    // Add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new java.lang.NullPointerException("Item cannot be null");
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        
        if (isEmpty()) { 
            last = first;
        }
        else {
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        num++;
    }
    
    // Add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new java.lang.NullPointerException("Item cannot be null");
        Node<Item> oldLast = first;
        last = new Node<Item>();
        last.item = item;
            
        if (isEmpty()) { 
            first = last;
        }
        else {
            last.next = null;
            last.previous = oldLast;
            oldLast.next = last;
        }
        num++;
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Deque is empty");
        Item item = first.item;
            
        if (size() == 1) {
            first = null;
            last = null;
        } 
        else {
            first = first.next;
            first.previous = null;
        }
        num--;
        return item;
    }
    
    // Remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Deque is empty");
        Item item = last.item;
            
        if (size() == 1) {
            first = null;
            last = null;
        } 
        else {
            last = last.previous;
            last.next = null;
        }
        num--;
        return item;
    }
    
    // Return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;
        
        @Override
        public boolean hasNext() { return current != null; }
        
        @Override
        public void remove() { 
            throw new java.lang.UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    // Unit testing (optional)
    public static void main(String[] args) {
       
    }
}