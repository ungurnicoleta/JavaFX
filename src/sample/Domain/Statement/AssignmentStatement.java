package sample.Domain.Statement;

import sample.Domain.ADTs.IDictionary;
import sample.Domain.ADTs.IHeap;
import sample.Domain.Expressions.ConstantExpression;
import sample.Domain.Expressions.Expression;
import sample.Domain.ProgramState;
import sample.Exception.*;

public class AssignmentStatement implements IStatement {
    private String id;
    private Expression expr;

    public AssignmentStatement(char c, ConstantExpression constantExpression, CompStatement compStatement){}

    public AssignmentStatement(String id, Expression expr){
        this.id = id;
        this.expr = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, HeapException {
        IDictionary<String,Integer> symTable = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();
        int val = this.expr.evalExpr(symTable, heap);
        if(symTable.isDefined(id))
            symTable.update(id, val);
        else
            symTable.add(id, val);

        System.out.print(toString() + "\n");
        System.out.println(state.toString());

        return null;
    }

    @Override
    public String toString() {
        return this.id + " = " + this.expr.toString();
    }
}
