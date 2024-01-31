import java.util.*;
/**
 * Write a description of class MyHashTable here.
 *
 * @author Daniel
 * @version Jan 24, 2024
 */
public class MyHashTable<K,V>
{
    private int size;
    private int tableSize;
    private HashNode<K,V>[] array; 
    private double loadFactor = 0.7; 

    /**
     * Constructor for objects of class MyHashTable
     */
    public MyHashTable()
    {
        size = 0;
        tableSize = 10;
        array = (HashNode<K,V>[])new HashNode[tableSize];
    }

    /**
     * Places a value in corresponding hash index
     *
     * @param key holds the index to the correlating value and value represents
     * the data at that index
     */
    public void put(K key, V value)
    {
        int index = hash(key);
        HashNode<K,V> node = new HashNode<K,V>(key, value);
        if (searchBucket(index, key) == null) {
            addToBucket(index, node);
            size++;
            if (size > loadFactor*tableSize) {
                this.expandHashTable();
        }
        } else {
            searchBucket(index, key).setValue(value);
        }
        
    }
    
    /**
     * returns the data at the index of the key
     *
     * @param  the index of the corresponding value
     * @return  the value at the index of the key in the hash table/array
     */
    public V get(K key)
    {
        int index = hash(key);
        if (array[index] == null) {
            return null;
        } else {
            return searchBucket(index, key).getValue();
        }
    }
    
    /**
     * removes the Hashnode and the index of the key
     *
     * @param  key/index of the node that will get removed
     * @return  returns the value at the index of the key in the hash table 
     * that is getting removed
     */
    public V remove(K key)
    {
        int index = hash(key);
        HashNode<K,V> previous = searchBucket(index,key);
        if (previous == null) {
            return null;
        } else {
            removeFromBucket(index, previous);
            return previous.getValue();
        }
    }
    
    private void addToBucket(int index, HashNode<K,V> node) {
        if (array[index] == null) {
            array[index] = node;
        } else {
            HashNode<K,V> head = array[index];
            array[index] = node;
            array[index].setNext(head);
        }
    }
    
    private HashNode<K,V> searchBucket(int index, K key) {
        HashNode<K,V> current = array[index];
        
        if (current != null) {
            while (!(current.getKey().equals(key))) {
                if (current.getNext() == null) {
                    return null;
                } else {
                    current = current.getNext();
                }
            }
        }
        return current;
    }
    
    private void removeFromBucket(int index, HashNode<K,V> previous) {
        if (array[index] == previous) {
            array[index] = array[index].getNext();
            size--;
        } else {
            HashNode<K,V> current = array[index];
            
            while (current.getNext() != previous) {
                current = current.getNext();
            }
            if (current.getNext() == previous) {
                 current.setNext(previous.getNext()); 
                 size--;
            }
        }
    }
    
    /**
     * Method size
     *
     * @return returns the size or the hash table
    */
    public int size() {
        return size;
    }
    
    /**
     * @return returns whether or not the hash table is empty
     */
    public boolean isEmpty() {
        return (size == 0);
    }
    
    /**
     * @return Returns the array/hash table
     */
    public String toString() {
        return Arrays.toString(array);
    }
    
    private int hash(K key) {
        return Math.abs(key.hashCode()%array.length);
    }
    
    private void expandHashTable() {
        HashNode<K,V>[] prevArray = array;
        array = (HashNode<K,V>[]) new HashNode[tableSize * 2];
        int newTableSize = tableSize * 2; 
        for (int i = 0; i < prevArray.length; i++) { 
            if (prevArray[i] != null) {
                HashNode<K,V> current = prevArray[i];
                HashNode<K, V> nextHashNode = current.getNext();
                
                while (current != null) {
                    nextHashNode = current.getNext();
                    int hashIndex = Math.abs(current.getHashValue()%newTableSize);
                    if (array[hashIndex] == null) {
                        array[hashIndex] = current;
                        current.setNext(null);
                    } else {
                        current.setNext(array[hashIndex]);
                        array[hashIndex] = current;
                    }
                    current = nextHashNode;
                }
            }
        }
    }
}

class HashNode<K,V>
{
    // instance variables - replace the example below with your own
    private V value;
    private K key;
    private HashNode next;
    private int hashValue;

    /**
     * Constructor for objects of class HashNode
     */
    public HashNode(K key, V value)
    {
        this.value = value;
        this.key = key;
        this.hashValue = key.hashCode();
    }
    
    public V getValue() {
        return value;
    }
    
    public K getKey() {
        return key;
    }
    
    public HashNode<K,V> getNext()
    {
        return next;
    }
    
    public int getHashValue(){
        return hashValue;
    }
    
    public void setValue(V a) {
        value = a;
    }
    
    public void setKey(K a) {
        key = a;
    }
    
    public void setNext(HashNode<K,V> a) {
        next = a;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public String toString()
    {
        String toReturn = key + "->" + value;
        HashNode<K,V> current = this;
        
        while (current.getNext() != null) {
            current = current.getNext();
            toReturn += "|" + current.getKey() + "->" + current.getValue();
        }
        return toReturn;
    }
}