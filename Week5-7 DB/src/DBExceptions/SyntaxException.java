package DBExceptions;

public class SyntaxException extends Exception {

    public SyntaxException(){
    }

    public String toString(){
        return "Invalid query";
    }
}
