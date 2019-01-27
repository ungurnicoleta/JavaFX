package sample.Domain.ADTs;

import sample.Exception.MyException;

import java.util.ArrayList;

public interface IList<T1> {
    void add(T1 elem);
    void delete(T1 elem) throws MyException;
    int size();
    T1 get(int index);
    String toString();
    ArrayList<T1> toArray();
}
