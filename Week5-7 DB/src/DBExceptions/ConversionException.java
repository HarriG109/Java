package DBExceptions;

public class ConversionException extends SyntaxException{

    public ConversionException(){

    }

    public String toString(){
        return " Attribute cannot be converted to number";
    }
}
