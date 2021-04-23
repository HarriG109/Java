package STAGExceptions;

public class IndexDoesntExistException extends Exception {

    public IndexDoesntExistException(){

    }

    public String toString(){
        return "Index does not exist in arraylist";
    }
}
