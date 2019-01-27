package sample.Domain.Statement;

import sample.Domain.ProgramState;
import sample.Exception.*;

import java.io.IOException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException, SymTableException, OpenFileException, ReadFileException, CloseFileException, IOException, HeapException;
    String toString();
}
