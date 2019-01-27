package sample.Repository;

import sample.Domain.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void addProgram(ProgramState newProgram);
    //ProgramState getCurrentPrg();
    //ProgramState getCurrentPrg1();
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> prgList);
    void logPrgStateExec(ProgramState prg) throws IOException;
}
