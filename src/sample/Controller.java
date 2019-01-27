package sample;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sample.View.Command;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class Controller implements Initializable{
    public ListView<Command> listPrograms = new ListView<>();

    @FXML
    public Button exitButton;
    public Button openButton;

    public void exitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void openProgramButtonAction() throws IOException{

        Command selectedCommand = listPrograms.getSelectionModel().getSelectedItem();

        RunProgram runProgramWindow = new RunProgram();
        runProgramWindow.show(selectedCommand);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addProgram(Command command){
        listPrograms.getItems().add(command);
    }
}
