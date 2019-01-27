package sample.Domain.Statement;

import sample.Domain.Expressions.Expression;
import sample.Domain.ProgramState;
import sample.Exception.HeapException;
import sample.Exception.MyException;

public class NewStatement implements IStatement{
    private String varName;
    private Expression expr;

    public NewStatement(String var, Expression ex){
        this.varName = var;
        this.expr = ex;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, HeapException {

        int v = this.expr.evalExpr(state.getSymTable(), state.getHeap());
        int poz = state.getHeap().add(v);

        state.getSymTable().add(this.varName, poz);

        return null;
    }

    @Override
    public String toString(){
        return "new( "+ this.varName + "," + this.expr.toString() +" )";
    }
}
