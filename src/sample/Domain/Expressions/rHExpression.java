package sample.Domain.Expressions;

import sample.Domain.ADTs.IDictionary;
import sample.Domain.ADTs.IHeap;
import sample.Exception.*;

public class rHExpression extends Expression {
    private String varName;

    public rHExpression(String varName) {
        this.varName = varName;
    }

    @Override
    public int evalExpr(IDictionary<String, Integer> symTable, IHeap<Integer> hp) throws MyException, HeapException {
        int poz = symTable.lookUp(this.varName);
        return hp.lookUp(poz);
    }

    @Override
    public String toString() {
        return "rH( "+ this.varName + " )";
    }
}
