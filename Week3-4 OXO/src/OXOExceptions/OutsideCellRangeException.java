package OXOExceptions;

public class OutsideCellRangeException extends OXOMoveException {

    int p;
    RowOrColumn rc;

    public OutsideCellRangeException(int position, RowOrColumn type){
        p = position;
        rc = type;
    }

    public String toString(){
        return "Cell number for " + rc + " out of range, board is not of size " + (p + 1) + "x" + (p + 1) + ", try again!";
    }
}
