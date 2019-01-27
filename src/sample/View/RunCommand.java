package sample.View;

import sample.Domain.ADTs.*;
import sample.Domain.ProgramState;
import sample.Domain.Statement.IStatement;
import sample.Exception.MyException;
import sample.OldController.OldController;
import sample.Repository.Repository;

import java.io.BufferedReader;


public class RunCommand extends Command {

    private Repository repository;
    private OldController controller;

    public RunCommand(String key, String description,IStatement statement,String logPath) {
        super(key, description);
        Heap<Integer> heap = new Heap<>();
        MyList<Integer> out = new MyList<>();
        MyStack<IStatement> exeStack = new MyStack<>();
        Dictionary<String, Integer> symTable = new Dictionary<>();
        FileTable<Integer, Tuple<String, BufferedReader>> fileTable = new FileTable<>();
        ILatchTable<Integer> latchTable = new LatchTable<>();
        ProgramState prgState = new ProgramState(1,exeStack, symTable, out, fileTable, heap, latchTable, statement);

        this.repository = new Repository(logPath,prgState);
        this.controller = new OldController(repository);
    }

    @Override
    public void execute() throws MyException {
        try{
            controller.allSteps(); }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public String toString(){
        return super.getDescription();
    }

    @Override
    public Repository getRepository(){
        return this.repository;
    }

    @Override
    public OldController getController(){
        return this.controller;
    }
}
