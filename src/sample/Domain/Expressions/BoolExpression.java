package sample.Domain.Expressions;

import sample.Domain.ADTs.IDictionary;
import sample.Domain.ADTs.IHeap;
import sample.Exception.HeapException;
import sample.Exception.MyException;

public class BoolExpression extends Expression {
    private String operator;
    private Expression exp1;
    private Expression exp2;

    public BoolExpression(String op, Expression e1, Expression e2){
        this.operator = op;
        this.exp1 = e1;
        this.exp2 = e2;
    }

    @Override
    public String toString(){
        return this.exp1.toString() + this.operator + this.exp2.toString();
    }

    @Override
    public int evalExpr(IDictionary<String,Integer> sym, IHeap<Integer> heap) throws MyException, HeapException {
        int lhs = this.exp1.evalExpr(sym,heap);
        int rhs = this.exp2.evalExpr(sym,heap);
        switch (this.operator){
            case "<" : return lhs < rhs ? 1:0;
            case "<=" : return lhs <= rhs ? 1:0;
            case "==" :  return lhs == rhs ? 1:0;
            case "!=" :  return lhs != rhs ? 1:0;
            case ">" :  return lhs > rhs ? 1:0;
            case ">=" :  return lhs >= rhs ? 1:0;
        }
        return 0;
    }
}
