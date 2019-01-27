package sample.Domain.Statement;

import sample.Domain.ProgramState;
import sample.Exception.*;

import java.io.IOException;

public class AwaitLatchStatement implements IStatement{
    private String varName;

    public AwaitLatchStatement(String varName){
        this.varName = varName;
    }


    @Override
    public ProgramState execute(ProgramState state) throws MyException, SymTableException, OpenFileException, ReadFileException, CloseFileException, IOException, HeapException {
        int foundSymTable = state.getSymTable().lookUp(varName);
        int foundLatchTable = state.getLatchTable().lookUp(foundSymTable);

        if(foundLatchTable != 0)
            state.getExeStack().push(this);

        return null;
    }

    @Override
    public String toString() {
        return "Await( " + varName + ")";
    }
}
