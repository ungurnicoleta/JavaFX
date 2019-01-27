package sample.View;

import sample.Exception.MyException;
import sample.OldController.OldController;
import sample.Repository.Repository;

public abstract class Command {
    private String key, description;
    public Command(String key, String description) { this.key = key; this.description = description;}
    public abstract void execute()throws MyException;
    public String getKey(){return key;}
    public String getDescription(){return description;}
    public abstract OldController getController();
    public abstract Repository getRepository();
}