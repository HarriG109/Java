package DBExceptions;

public class FileExistsException extends SyntaxException{

    public FileExistsException(){

    }

    public String toString(){
        return "File already exists";
    }
}
