package sample.Domain.Statement;


import sample.Domain.ADTs.Dictionary;
import sample.Domain.ADTs.IDictionary;
import sample.Domain.ADTs.IStack;
import sample.Domain.ADTs.MyStack;
import sample.Domain.ProgramState;
import sample.Exception.*;

import java.io.IOException;


public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement stmt){
        this.statement = stmt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, SymTableException, OpenFileException, ReadFileException, CloseFileException, IOException, HeapException {
        IStack<IStatement> exeStack = new MyStack<>();
        //exeStack.push(this.statement);
        IDictionary<String, Integer> symTable = new Dictionary <>();
        for (String key : state.getSymTable().keys()){
            Integer val = state.getSymTable().lookUp(key);
            symTable.add(key, val);
       }
        ProgramState prg = new ProgramState(state.getId()*10, exeStack, symTable, state.getOut(), state.getFileTable(), state.getHeap(),state.getLatchTable(), this.statement);
        return prg;
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }
}
