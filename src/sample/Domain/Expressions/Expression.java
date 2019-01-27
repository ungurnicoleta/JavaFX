package sample.Domain.Expressions;

import sample.Domain.ADTs.IDictionary;
import sample.Domain.ADTs.IHeap;
import sample.Exception.*;

public abstract class Expression {
    public abstract int evalExpr(IDictionary<String,Integer> symTable, IHeap<Integer> hp) throws MyException, HeapException;
    public abstract String toString();
}
