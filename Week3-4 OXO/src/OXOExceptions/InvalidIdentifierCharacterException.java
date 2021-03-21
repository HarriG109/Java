package OXOExceptions;

public class InvalidIdentifierCharacterException extends OXOMoveException{

    char c;
    RowOrColumn rc;

    public InvalidIdentifierCharacterException(char character, RowOrColumn type){
        c = character;
        rc = type;
    }

    public String toString(){
        return "Unexpected char " + c + " found where " + rc + " should be, try again!";
    }
}
