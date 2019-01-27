package sample.Domain.Expressions;

import sample.Domain.ADTs.IDictionary;
import sample.Domain.ADTs.IHeap;
import sample.Exception.HeapException;
import sample.Exception.MyException;

public class NotExpression extends Expression {
    private Expression exp;

    public NotExpression(Expression expression){
        this.exp = expression;
    }

    @Override
    public int evalExpr(IDictionary<String, Integer> symTable, IHeap<Integer> hp) throws MyException, HeapException {
        int value = exp.evalExpr(symTable,hp);
        if(value == 0)
            return 1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return "!"+exp.toString();
    }
}
