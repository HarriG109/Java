package DBExceptions;

public class ConversionException extends SyntaxException{

    public ConversionException(){

    }

    public String toString(){
        return " Command or Attribute is wrong type for operator";
    }
}
