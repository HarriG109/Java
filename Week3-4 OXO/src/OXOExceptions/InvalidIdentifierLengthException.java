package OXOExceptions;

public class InvalidIdentifierLengthException extends OXOMoveException{

    int length;

    public InvalidIdentifierLengthException(int length){
        this.length = length;
    }

    public String toString(){
        return "Command is too long or short to be a co-ordinate, length is: " + length + " , try again!";
    }
}
