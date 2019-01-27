package sample.Domain.ADTs;

import sample.Exception.MyException;

import java.util.Collection;
import java.util.Set;

public interface ILatchTable<V> {
    int add(V value);
    void update(int key, V value) throws MyException;
    boolean isDefined(int key);
    V lookUp(int key) throws MyException;
    void remove(int key) throws MyException;
    Collection<V> values();
    Set<Integer> keys();
    String toString();
}
