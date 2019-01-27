package sample;

import javafx.fxml.Initializable;
import sample.Domain.ADTs.Tuple;
import sample.Domain.ProgramState;
import sample.View.Command;
import sample.Domain.Statement.*;


import sample.Exception.HeapException;
import sample.Exception.MyException;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;

public class ControllerRunProgram implements Initializable {
    private Command command;
    private ProgramState currentPrgState;
    private Integer PrgStateId;
    public ListView<Integer> programStateList = new ListView<>();
    public ListView<String> exeStackList = new ListView<>();
    public ListView<Integer> outputlist = new ListView<>();

    public TableView<Tuple<Integer,Integer>> heapTable = new TableView<>();
    public TableColumn<Tuple<Integer,Integer>,Integer> addressHeap = new TableColumn<>();
    public TableColumn<Tuple<Integer,Integer>,Integer> valueHeap = new TableColumn<>();

    public TableView<Tuple<Integer,String>> fileTable = new TableView<>();
    public TableColumn<Tuple<Integer,String>,Integer> identifier = new TableColumn<>();
    public TableColumn<Tuple<Integer,String>,String> fileName = new TableColumn<>();

    public TableView<Tuple<String,Integer>> symTable = new TableView<>();
    public TableColumn<Tuple<String,Integer>,String> variableSym = new TableColumn<>();
    public TableColumn<Tuple<String,Integer>,Integer> valueSym = new TableColumn<>();


    public TableView<Tuple<Integer,Integer>> latchTable = new TableView<>();
    public TableColumn<Tuple<Integer,Integer>,Integer> locationLatchColumn = new TableColumn<>();
    public TableColumn<Tuple<Integer,Integer>,Integer> valueLatchColumn = new TableColumn<>();


    public TextField nrOfProgramsField;
    public TextField currentProgram;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valueHeap.setCellValueFactory(new PropertyValueFactory<Tuple<Integer,Integer>,Integer>("second"));
        addressHeap.setCellValueFactory(new PropertyValueFactory<Tuple<Integer,Integer>,Integer>("first"));

        variableSym.setCellValueFactory(new PropertyValueFactory<Tuple<String,Integer>,String>("first"));
        valueSym.setCellValueFactory(new PropertyValueFactory<Tuple<String,Integer>,Integer>("second"));

        identifier.setCellValueFactory(new PropertyValueFactory<Tuple<Integer,String>,Integer>("first"));
        fileName.setCellValueFactory(new PropertyValueFactory<Tuple<Integer,String>,String>("second"));

        locationLatchColumn.setCellValueFactory(new PropertyValueFactory<Tuple<Integer,Integer>,Integer>("first"));
        valueLatchColumn.setCellValueFactory(new PropertyValueFactory<Tuple<Integer,Integer>,Integer>("second"));
    }

    public void setCommand(Command command){
        this.command = command;
        this.currentPrgState = command.getRepository().getProgramList().get(0);
        this.PrgStateId = currentPrgState.getId();
        try{
            refreshAll(PrgStateId);
        }
        catch (Exception ex){
            showErrorMessage(ex.getMessage());
        }

    }
    public Command getCommand() { return command;}

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Interpreter");
        alert.setHeaderText("Interpreter info");
        alert.setContentText(message);
        alert.showAndWait().ifPresent(rs -> {});
    }

    public void onMouseClickedItemListView(){
        int prgStateId;
        try{
            if(programStateList.getSelectionModel().getSelectedItem() != null)
            {
                PrgStateId = programStateList.getSelectionModel().getSelectedItem();
                currentPrgState = command.getController().getPrgStateById(PrgStateId);
                currentProgram.setText(Integer.toString(PrgStateId));
                refreshAll(PrgStateId);
            }
        }
        catch (Exception ex){
            showErrorMessage("Please select a program from the list!");
        }
    }


    public void oneStep(){
        command.getController().executor = Executors.newFixedThreadPool(6);
        List<ProgramState> prgList = command.getController().removeCompletedPrg(command.getRepository().getProgramList());

        try{
            if(prgList.size() > 0) {
                command.getController().oneStepForAllPrg(prgList);
            }
            else
                command.getController().executor.shutdown();
        }catch (Exception ex){
            showErrorMessage(ex.getMessage());
        }
        command.getRepository().setProgramList(prgList);
        try{
            currentPrgState = command.getRepository().getProgramList().get(0);
            PrgStateId = currentPrgState.getId();
            refreshAll(PrgStateId);
        }
        catch(Exception ex){
            showErrorMessage("Execution finished!");
        }

    }

    private void clearAll(){
        exeStackList.getItems().clear();
    }

    private void refreshAll(int prgStateId) throws MyException,HeapException{
        currentProgram.setText(Integer.toString(PrgStateId));
        refreshNrPrograms();
        refreshPrgStateList();
        refreshOutput(prgStateId);
        refreshSymTable(prgStateId);
        refreshHeap(prgStateId);
        refreshExeStack(prgStateId);
        refreshFileTable(prgStateId);
        refreshLatchTable(prgStateId);

    }


    private void refreshFileTable(int prgStateId){
        fileTable.getItems().clear();

        List<Tuple<Integer,String>> fT = command.getController().getFileTable(prgStateId);
        fileTable.getItems().addAll(fT);
    }

    private void refreshOutput(int prgsStateId){
        outputlist.getItems().clear();
        List<Integer> out = currentPrgState.getOut().toArray();
        outputlist.getItems().addAll(out);
        System.out.println(out);
    }

    private void refreshNrPrograms(){
        nrOfProgramsField.clear();
        int nr = command.getRepository().getProgramList().size();
        nrOfProgramsField.setText(Integer.toString(nr));
    }

    private void refreshExeStack(int prgStateId){
        exeStackList.getItems().clear();
        ArrayList<IStatement> exeStack = command.getController().getExeStack(prgStateId);
        for (int i=exeStack.size()-1;i>=0;i--) {
            exeStackList.getItems().add(exeStack.get(i).toString());
        }
        System.out.println(exeStack);
    }

    private void refreshSymTable(int prgStateId) throws MyException {
        symTable.getItems().clear();
        List<Tuple<String,Integer>> symTableList = command.getController().getSymTable(prgStateId);
        symTable.getItems().setAll(symTableList);
        System.out.println(symTableList);
    }

    private void refreshPrgStateList(){
        programStateList.getItems().clear();
        List<ProgramState> programStates = command.getRepository().getProgramList();
        for (ProgramState state:programStates) {
            programStateList.getItems().add(state.getId());
        }
    }

    private void refreshHeap(int prgStateId) throws HeapException{
        heapTable.getItems().clear();
        List<Tuple<Integer,Integer>> heap = command.getController().getHeap(prgStateId);
        heapTable.getItems().setAll(heap);
    }

    private void refreshLatchTable(int prgStateId) throws MyException{
        latchTable.getItems().clear();

        List<Tuple<Integer,Integer>> list = command.getController().getLatchTable(prgStateId);
        latchTable.getItems().setAll(list);
    }

}
