package DBExceptions;

public class DiffInNumOfColsException extends SyntaxException {

    public DiffInNumOfColsException(){

    }

    public String toString(){
        return "Number of values don't match number of attributes";
    }
}
