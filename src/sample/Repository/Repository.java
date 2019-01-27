package sample.Repository;

import sample.Domain.ProgramState;
import sample.Exception.HeapException;
import sample.Exception.MyException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Repository implements IRepository {
    private List<ProgramState> repo;
    private String logFilePath;

    public Repository(String logFilePath, ProgramState prg){
        this.repo =  new ArrayList<>();
        this.logFilePath = logFilePath;
        this.addProgram(prg);
    }

    @Override
    public void addProgram(ProgramState newProgram) {
        this.repo.add(newProgram);
    }


    public List<ProgramState> getProgramList() {
        return this.repo;
    }


    public void setProgramList(List<ProgramState> prgList){
        this.repo = prgList;
    }

    public void logPrgStateExec(ProgramState prg) throws IOException{
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            logFile.write(prg.toFileString());
            logFile.close();
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }
        catch (HeapException h) {
            System.out.println("Heap exception!!");
        }
    }
}
