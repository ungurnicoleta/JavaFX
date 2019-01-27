package sample.Domain.Statement;

import sample.Domain.Expressions.Expression;
import sample.Domain.ProgramState;
import sample.Exception.*;

import java.io.IOException;

public class wHStatement implements IStatement {
    private String varName;
    private Expression expr;

    public  wHStatement(String varName, Expression expr){
        this.varName = varName;
        this.expr = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, SymTableException, OpenFileException, ReadFileException, CloseFileException, IOException, HeapException {
        int heapAddress = state.getSymTable().lookUp(this.varName);
        int newValue = this.expr.evalExpr(state.getSymTable(),state.getHeap());

        if(!state.getHeap().isDefined(heapAddress))
            throw new HeapException("Invalid address!");
        else
            state.getHeap().update(heapAddress,newValue);

        return null;
    }

    @Override
    public String toString() {
        return "wH( " + this.varName + "," + this.expr + " )";
    }
}
