package sample.Domain.Statement;

import sample.Domain.ADTs.IStack;
import sample.Domain.ProgramState;
import sample.Exception.MyException;

public class CompStatement implements IStatement{
    private IStatement first;
    private IStatement second;

    public CompStatement() {}

    public CompStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<IStatement> exeStack = state.getExeStack();
        exeStack.push(this.second);
        exeStack.push(this.first);

        System.out.print(toString()+ "\n");
        System.out.println(state.toString());

        return null;
    }

    @Override
    public String toString() {
        return  "(" + this.first.toString() + ";" + this.second.toString()+")";
    }
}
