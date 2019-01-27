package sample;

import sample.View.Command;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RunProgram {
    public void show(Command command) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RunProgramController.fxml"));
        Parent selectProgramWindow = loader.load();

        ControllerRunProgram controller = loader.getController();
        controller.setCommand(command);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select Program");
        stage.setScene(new Scene(selectProgramWindow));
        stage.showAndWait();
    }
}