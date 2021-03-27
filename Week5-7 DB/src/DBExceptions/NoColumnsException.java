package DBExceptions;

public class NoColumnsException extends SyntaxException{

    public NoColumnsException(){

    }

    public String toString(){
        return "Table with no attributes";
    }
}
