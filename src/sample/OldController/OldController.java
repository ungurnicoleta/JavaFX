package sample.OldController;

import sample.Domain.ADTs.IDictionary;
import sample.Domain.ADTs.IFileTable;
import sample.Domain.ADTs.IHeap;
import sample.Domain.ADTs.Tuple;
import sample.Domain.ProgramState;
import sample.Domain.Statement.IStatement;
import sample.Exception.HeapException;
import sample.Exception.MyException;
import sample.Repository.IRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class OldController {
    private IRepository repo;
    public ExecutorService executor;

    public OldController(IRepository repo){this.repo = repo;}

    public void addProgram(ProgramState prg){
        this.repo.addProgram(prg);
    }


//    void oneStepForAllPrg(List<ProgramState> prgList) throws IOException, InterruptedException {
//        //prgList.forEach(prg ->repo.logPrgStateExec(prg));
//        for( ProgramState state : prgList)
//            this.repo.logPrgStateExec(state);
//
//        List<Callable<ProgramState>> callList = prgList.stream()
//                .map((ProgramState p) -> (Callable<ProgramState>)(() -> {return p.oneStep();}))
//                .collect(Collectors.toList());
//        List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
//                .map(future -> { try { return future.get();}
//                catch(Exception e) {
//                    System.out.println(e.getMessage());
//                }
//                    return null;}).filter(p -> p!=null).collect(Collectors.toList());
//
//        prgList.addAll(newPrgList);
//        //prgList.forEach(prg ->repo.logPrgStateExec(prg));
//
////        for( ProgramState state : newPrgList)
////            this.repo.logPrgStateExec(state);
//        prgList.forEach(prg -> {
//            try {
//                repo.logPrgStateExec(prg);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        this.repo.setProgramList(prgList);
//    }

    public void oneStep() throws InterruptedException, MyException,IOException, HeapException{
        executor = Executors.newFixedThreadPool(2);

        List<ProgramState> prgList=removeCompletedPrg(repo.getProgramList());

        if(prgList.size() > 0){
            oneStepForAllPrg(prgList);
            prgList=removeCompletedPrg(repo.getProgramList());
        }
        if(prgList.size() == 0)
            executor.shutdownNow();

        repo.setProgramList(prgList);
    }

    public void oneStepForAllPrg(List<ProgramState> prgList) throws  IOException, InterruptedException, MyException{
        //prgList.forEach(prg -> repo.logPrgStateExec(prg));

        for( ProgramState state : prgList)
            repo.logPrgStateExec(state);

        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());


        List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> { try { return future.get();}
                catch(Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
                }).filter(p -> p!=null).collect(Collectors.toList());

        prgList.addAll(newPrgList);
        //prgList.forEach(prg ->repo.logPrgStateExec(prg));

        for( ProgramState state : prgList)
            repo.logPrgStateExec(state);


        repo.setProgramList(prgList);
    }



    public void     allSteps() throws InterruptedException, MyException, IOException{
        executor = Executors.newFixedThreadPool(2);

        List<ProgramState> prgList = removeCompletedPrg(this.repo.getProgramList());
        while(prgList.size() > 0){
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(this.repo.getProgramList());
        }
        executor.shutdownNow();

        this.repo.setProgramList(prgList);
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList){
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }


    public ProgramState getPrgStateById(int id){
        for (ProgramState prgState:this.repo.getProgramList()) {
            if(prgState.getId() == id)
                return prgState;
        }
        return null;
    }

    public List<Tuple<String,Integer>> getSymTable(int prgStateId)throws MyException{
        ProgramState state = getPrgStateById(prgStateId);
        IDictionary<String,Integer> symTable = state.getSymTable();

        List<Tuple<String,Integer>> list = new ArrayList<>();
        for (String key:symTable.keys())
        {
            list.add(new Tuple<>(key,symTable.lookUp(key)));
        }
        return list;
    }

    public List<Integer> getOutput(int prgStateId){
        ProgramState state = getPrgStateById(prgStateId);
        return state.getOut().toArray();

    }

    public List<Tuple<Integer,String>> getFileTable(int prgStateId){
        ArrayList<Tuple<Integer,String>> fileTable = new ArrayList<>();
        ProgramState state = getPrgStateById(prgStateId);
        IFileTable<Integer,Tuple<String, BufferedReader>> fT = state.getFileTable();

        for (Integer key:fT.keys())
        {
            fileTable.add(new Tuple<Integer,String>(key,fT.lookUp(key).getFirst()));
        }

        return fileTable;
    }

    public ArrayList<IStatement> getExeStack(int prgStateId){
        ProgramState state = getPrgStateById(prgStateId);
        return state.getExeStack().elements();

    }

    public List<Tuple<Integer,Integer>> getHeap(int prgStateId)throws HeapException{
        ProgramState state = getPrgStateById(prgStateId);
        IHeap<Integer> heap = state.getHeap();

        List<Tuple<Integer,Integer>> list = new ArrayList<>();
        for (Integer key:heap.keys()) {
            list.add(new Tuple<>(key,heap.lookUp(key)));
        }
        return list;
    }


    public List<Tuple<Integer,Integer>> getLatchTable(int prgStateId) throws  MyException {
        ProgramState state = getPrgStateById(prgStateId);

        List<Tuple<Integer,Integer>> list = new ArrayList<>();
        for(Integer key: state.getLatchTable().keys())
            list.add(new Tuple<>(key, state.getLatchTable().lookUp(key)));

        return list;
    }


}

