package sample.Domain.Statement;

import sample.Domain.Expressions.Expression;
import sample.Domain.ProgramState;
import sample.Exception.*;

import java.io.IOException;

public class NewLatch implements IStatement {
    private String varName;
    private Expression expr;

    public NewLatch(String varName, Expression expr){
        this.varName = varName;
        this.expr = expr;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) throws MyException, SymTableException, OpenFileException, ReadFileException, CloseFileException, IOException, HeapException {
        int number = expr.evalExpr(state.getSymTable(), state.getHeap());

        int newLocation = state.getLatchTable().add(number);
        if(state.getSymTable().isDefined(varName))
            state.getSymTable().update(varName,newLocation);
        else
            state.getSymTable().add(varName,newLocation);

        return null;
    }

    @Override
    public String toString() {
        return "newLatch(" + varName + ", " + expr.toString() + ")";
    }
}
