package DBExceptions;

public class FileMissingException extends SyntaxException {

    public FileMissingException(){

    }

    public String toString(){

        return "Selected file is missing";
    }
}

