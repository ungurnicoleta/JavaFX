package sample.Domain.ADTs;

import java.util.ArrayList;

public interface IStack<T1> {
    void push(T1 elem);
    T1 pop();
    boolean isEmpty();
    int size();
    String toString();
    ArrayList<T1> elements();
}
