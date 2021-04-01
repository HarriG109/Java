package DBExceptions;

public class ColumnDoesntExistException extends SyntaxException {

    public ColumnDoesntExistException(){

    }

    public String toString(){
        return "Column mentioned does not exist";
    }
}
