package sample.Domain.Statement;

import sample.Domain.Expressions.Expression;
import sample.Domain.ProgramState;
import sample.Exception.*;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseStatement implements IStatement {
    private Expression expFileId;

    public CloseStatement(Expression expr){
        this.expFileId = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws CloseFileException, MyException, IOException, HeapException{
        int val = this.expFileId.evalExpr(state.getSymTable(), state.getHeap());

        if(!state.getFileTable().isDefined(val))
            throw new CloseFileException("The file you are trying to close is not open!");

        BufferedReader buffer = state.getFileTable().lookUp(val).getSecond();

        buffer.close();
        state.getFileTable().remove(val);

        System.out.println(toString() + "\n");
        System.out.println(state.toString());
        return null;
    }

    @Override
    public String toString() {
        return "CloseStatement(" +
                 this.expFileId.toString() +")";
    }
}
