package sample.Domain.Statement;

import sample.Domain.ADTs.IDictionary;
import sample.Domain.ADTs.IHeap;
import sample.Domain.ADTs.IStack;
import sample.Domain.Expressions.Expression;
import sample.Domain.ProgramState;
import sample.Exception.*;

public class IfStatement implements IStatement {

    private Expression expr;
    private IStatement thenS;
    private IStatement elseS;


    public IfStatement(Expression expr, IStatement thenS, IStatement elseS) {
        this.expr = expr;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, HeapException {
        IStack<IStatement> exeStack = state.getExeStack();
        IDictionary<String,Integer> symTable = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();

        if (this.expr.evalExpr(symTable, heap) != 0)
                exeStack.push(thenS);
            else
                exeStack.push(elseS);

        System.out.println(toString() + "\n");
        System.out.println(state.toString());

        return null;
    }

    @Override
    public String toString() {
        return "IF(" + this.expr.toString() + ") THEN(" + this.thenS.toString()
                + ")ELSE(" + this.elseS.toString() + ")";
    }


}
