package sample.Domain;

import sample.Domain.ADTs.*;
import sample.Domain.Statement.IStatement;
import sample.Exception.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Stack;

public class ProgramState {
    private Integer id;
    private IStack<IStatement> exeStack;
    private IDictionary<String,Integer> symTable;
    private IList<Integer> out;
    private IStatement originalProgram;
    private IFileTable<Integer, Tuple<String, BufferedReader>> fileTable;
    private IHeap<Integer> heap;
    private ILatchTable<Integer> latchTable;


    public ProgramState(Integer id,
                        IStack<IStatement> exeStack,
                        IDictionary<String,Integer> symTable,
                        IList<Integer> out,IFileTable<Integer,
                        Tuple<String, BufferedReader>> fileTable,
                        IHeap<Integer> heap,ILatchTable<Integer> latchTable,
                        IStatement originalProgram){
        this.id = id;
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.latchTable = latchTable;
        this.originalProgram = originalProgram;
        this.exeStack.push(originalProgram);
    }
    public Integer getId() {return this.id;}

    public IStack<IStatement> getExeStack() {
        return this.exeStack;
    }

    public void setExeStack(IStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public IDictionary<String, Integer> getSymTable() {
        return this.symTable;
    }

    public void setSymTable(IDictionary<String, Integer> symTable) {
        this.symTable = symTable;
    }

    public IList<Integer> getOut() {
        return this.out;
    }

    public void setOut(IList<Integer> out) {
        this.out = out;
    }

    public IStatement getOriginalProgram() {
        return this.originalProgram;
    }

    public void setOriginalProgram(IStatement originalProgram) {
        this.originalProgram = originalProgram;
    }

    public IFileTable<Integer, Tuple<String,BufferedReader>> getFileTable() {return this.fileTable;}

    public void setFileTable(IFileTable fileTable) { this.fileTable = fileTable; }

    public IHeap<Integer> getHeap() { return heap; }

    public void setHeap(IHeap<Integer> heap) { this.heap = heap; }

    public boolean isNotCompleted(){
        return !this.exeStack.isEmpty();
    }

    public ILatchTable<Integer> getLatchTable() {return this.latchTable;}

    public ProgramState oneStep() throws MyException, SymTableException, ReadFileException, CloseFileException, IOException, OpenFileException, HeapException {
        //IStack<IStatement> exeStack = currentProgramState.getExeStack();

        if(this.getExeStack().isEmpty())
            throw new MyException("Exe stack is empty");
        IStatement crtStmt = exeStack.pop();

        return crtStmt.execute(this);
    }

    public String toString(){
        return "ID: " + this.id.toString()+
                "\nExeStack: " + this.exeStack.toString() +
                "\n\nSymTable: " + this.symTable.toString() +
                "\n\nPrint Output Table: \n" + this.out.toString() +
                "\nFileTable: " + this.fileTable.toString()+
                "\nLatchTable: " + latchTable.toString() +
                "\nHeapTable: " + this.heap.toString()+"\n\n\n";
    }

    public String toFileString() throws MyException, HeapException {
        Stack<IStatement> stack2 = new Stack<>();
        String output = "ID: " + this.id + "\n";
        output = output + "ExeStack:\n";
        IStatement stmt;
        while(!this.exeStack.isEmpty()){
            stmt = this.exeStack.pop();
            stack2.push(stmt);
            output= output +  stmt.toString() + "\n";
        }

        while(!stack2.isEmpty()){
            stmt = stack2.pop();
            this.exeStack.push(stmt);
        }
        output += "SymTable:\n";

        for(String key : this.symTable.keys())
            output = output + key + "-->" + this.symTable.lookUp(key) + "\n";

        output = output + "Out:\n";

        for (int i=0;i<this.out.size();i++)
            output = output + this.out.get(i) + "\n";

        output += "FileTable: \n";

        for(int key: fileTable.keys())
            output = output + key + "-->" + fileTable.lookUp(key).getFirst() + "\n";

        output += "Heap: \n";

        for(int key: this.heap.keys())
            output = output + key + "-->" + this.heap.lookUp(key) + "\n";

        output += "Latch: \n";

        for(int key: latchTable.keys())
            output = output + key + "-->" + latchTable.lookUp(key) + "\n";
        output = output + "\n\n";
        return output;
    }
}
