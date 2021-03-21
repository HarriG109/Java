package OXOExceptions;

public class InvalidIdentifierException extends OXOMoveException{
    public InvalidIdentifierException(){

    }

    public String toString(){
        return "Unexpected char entered: ";
    }
}
