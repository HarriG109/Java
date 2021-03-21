package OXOExceptions;

public class CellAlreadyTakenException extends OXOMoveException {

    public CellAlreadyTakenException(){

    }

    public String toString(){
        return "Cell already taken, try again!";
    }
}
