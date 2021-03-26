package DBExceptions;

public class FolderMissingException extends SyntaxException {

    public FolderMissingException(){

    }

    public String toString(){
        return "Folder already exists";
    }
}
