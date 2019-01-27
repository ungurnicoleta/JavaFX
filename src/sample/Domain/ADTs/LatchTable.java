package sample.Domain.ADTs;

import sample.Exception.MyException;

import java.util.*;

public class LatchTable<V> implements ILatchTable<V> {

    private int firstFree; //the current free position
    private Map<Integer, V> heap;
    private int capacity =10;
    private int[] nextFree = new int[capacity]; //vector holding the next free positions

    public LatchTable(){
        heap = new HashMap<>();
        firstFree = 1;
        for(int i=0; i < capacity; i++)
            nextFree[i] = i+1;
        nextFree[capacity - 1] = -1;
    }

    private void realloc(){
        nextFree = Arrays.copyOf(nextFree,capacity*2);
        for(int i = capacity - 1; i< capacity*2; i++)
            nextFree[i] = i+1;
        nextFree[capacity*2 - 1] = - 1;
        firstFree = capacity - 1;
        capacity = capacity * 2;
    }

    @Override
    public boolean isDefined(int key){
        return this.heap.containsKey(key);
    }

    @Override
    public int add(V value){
        if(firstFree == -1)
            realloc();
        heap.put(firstFree,value);
        int poz = firstFree;
        firstFree = nextFree[firstFree];
        return poz;
    }

    @Override
    public void update(int key, V value)throws MyException {
        if(!isDefined(key))
            throw new MyException("Invalid address");
        this.heap.put(key, value);
    }

    @Override
    public void remove(int poz) throws MyException {
        if(poz < 0)
            throw new MyException("The position cannot be negative!");
        heap.remove(poz);
        int current_free = firstFree;
        firstFree = poz;
        nextFree[firstFree] = current_free;
    }

    @Override
    public V lookUp(int key) throws MyException
    {
        if(!isDefined(key))
            throw new MyException("Invalid address");
        return this.heap.get(key);
    }

    @Override
    public String toString(){
        return heap.toString();
    }

    @Override
    public Collection<V> values(){
        return this.heap.values();
    }

    @Override
    public Set<Integer> keys(){
        return this.heap.keySet();
    }
}
