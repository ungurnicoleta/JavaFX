package sample.Domain.Statement;

import sample.Domain.ADTs.IDictionary;
import sample.Domain.ADTs.IHeap;
import sample.Domain.ADTs.IList;
import sample.Domain.Expressions.Expression;
import sample.Domain.ProgramState;
import sample.Exception.HeapException;
import sample.Exception.MyException;

public class PrintStatement implements IStatement{

    private Expression expr;
    public PrintStatement() {}

    public PrintStatement(Expression expr) { this.expr = expr; }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, HeapException {

        IList<Integer> out = state.getOut();
        IDictionary<String,Integer> symTable = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();

        out.add(this.expr.evalExpr(symTable,heap));

        System.out.println(toString() + "\n");
        System.out.println(state.toString());

        return null;
    }

    @Override
    public String toString() {
        return "Print: (" + this.expr.toString() + ")";
    }
}
