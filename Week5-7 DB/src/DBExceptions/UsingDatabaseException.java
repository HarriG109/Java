package DBExceptions;

public class UsingDatabaseException extends SyntaxException{

    public UsingDatabaseException(){

    }

    public String toString(){
        return "Current database is in use";
    }
}
