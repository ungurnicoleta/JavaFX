package sample.Domain.Expressions;

import sample.Domain.ADTs.IDictionary;
import sample.Domain.ADTs.IHeap;
import sample.Exception.MyException;

public class VarExpression extends Expression{
    private String id;

    public VarExpression(String id){
        this.id = id;
    }

    @Override
    public int evalExpr(IDictionary<String, Integer> symTable, IHeap<Integer> hp) throws MyException {
        return symTable.lookUp(this.id);
    }

    @Override
    public String toString() {
        return this.id;
    }

    public String getId() {
        return this.id;
    }
}
