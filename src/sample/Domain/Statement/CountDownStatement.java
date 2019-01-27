package sample.Domain.Statement;

import sample.Domain.ProgramState;
import sample.Exception.*;

import java.io.IOException;

public class CountDownStatement implements IStatement{
    private String varName;

    public CountDownStatement(String varName){
        this.varName = varName;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) throws MyException, SymTableException, OpenFileException, ReadFileException, CloseFileException, IOException, HeapException {
        int foundSymTable = state.getSymTable().lookUp(varName);
        int foundLatchTable = state.getLatchTable().lookUp(foundSymTable);

        if(foundLatchTable > 0) {
            state.getLatchTable().update(foundSymTable, foundLatchTable - 1);
            state.getOut().add(state.getId());
        }
        return null;
    }

    @Override
    public String toString() {
        return "countDown(" + varName +")";
    }
}
