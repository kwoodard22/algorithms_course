/******************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue
 *  Dependencies: Iterator, StdRandom
 *
 *  Creates a Randomized Queue - adds or removes a random item
 *
 ******************************************************************************/
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int num = 0;
    private Item[] queue;
    
    // Construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }
    
    // Is the queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }       
    
    // Return the number of items on the queue
    public int size() {
        return num;
    }                       
    
    // Add the item
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        if (num == queue.length) resize(2 * queue.length);
        queue[num++] = item;
    }    
    
    // Remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue is empty");
        int index = StdRandom.uniform(num);
        Item random = queue[index];
        
        if (index != num - 1) queue[index] = queue[num - 1];
        queue[--num] = null;
        if (num >= 1 && num == queue.length / 4) resize(queue.length / 2);
        return random;
    }                   
    
    // Return (but do not remove) a random item
    public Item sample() {
        if (size() == 0) throw new java.util.NoSuchElementException("Queue is empty");
        return queue[StdRandom.uniform(num)];
    }
    
    // Return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        // Store Items in a ResizingArray
        private int size = num;
        private final Item[] copy;
        
        private RandomizedQueueIterator() {
            copy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                copy[i] = queue[i];
            }
        }
        
        @Override
        public boolean hasNext() { 
            return size > 0;
        }
        
        @Override
        public void remove() { 
            throw new java.lang.UnsupportedOperationException();
        }
        
        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            
            int randomIndex = StdRandom.uniform(size);
            Item randomItem = copy[randomIndex];
            if (randomIndex != size - 1) {
                copy[randomIndex] = copy[size - 1];
            }
            
            copy[--size] = null;
            return randomItem;
        }
    }
    
    // Unit testing (optional)
    public static void main(String[] args) {
    }
        
    /**************************
    * Private utility methods
    **************************/
    private void resize(int capacity) {
        Item[] copy = new Item[capacity];
        for (int i = 0; i < num; i++)
            copy[i] = queue[i];
        queue = copy;
    }    
}