package DBExceptions;

public class FolderExistsException extends SyntaxException{

    public FolderExistsException(){

    }

    public String toString(){
        return "Folder already exists";
    }
}
