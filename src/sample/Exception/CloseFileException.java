package sample.Exception;

public class CloseFileException extends Exception {
    private String message;

    public CloseFileException(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return "CloseFileException " + this.message;
    }
}