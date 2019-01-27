package sample.Domain.Statement;

import sample.Domain.Expressions.Expression;
import sample.Domain.Expressions.NotExpression;
import sample.Domain.ProgramState;
import sample.Exception.*;

import java.io.IOException;

public class RepeatUntilStatement implements IStatement {
    private IStatement statement;
    private Expression expression;

    public RepeatUntilStatement(IStatement statement, Expression ex){
        this.statement = statement;
        this.expression = ex;
    }


    @Override
    public ProgramState execute(ProgramState state) throws MyException, SymTableException, OpenFileException, ReadFileException, CloseFileException, IOException, HeapException {
        IStatement newStmt = new CompStatement(
                statement,
                new WhileStatement(new NotExpression(expression),statement));
        state.getExeStack().push(newStmt);
        return null;
    }

    @Override
    public String toString(){
        return "repeat "+statement.toString() + " until" + expression.toString();
    }
}
