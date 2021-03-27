package DBExceptions;

public class UsingDatabaseException extends SyntaxException{

    public UsingDatabaseException(){

    }

    public String toString(){
        return "USE detected, command can not be ran while database is in use";
    }
}
