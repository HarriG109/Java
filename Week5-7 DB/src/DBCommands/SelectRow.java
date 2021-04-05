package DBCommands;

import DBExceptions.ColumnDoesntExistException;
import DBExceptions.ConversionException;
import DBExceptions.FileMissingException;
import java.util.*;

public class SelectRow extends DBcmd {

    public String[] columns;
    //Arraylist of kept columns -> 0 means not kept -> 1 means kept
    public ArrayList<Integer> keepColumns = new ArrayList<Integer>();
    public ArrayList<Integer> keepRows = new ArrayList<Integer>();

    public SelectRow(String filePath, String[] commandArray)
            throws FileMissingException, ColumnDoesntExistException, ConversionException {

        int whereIndex, i;

        readInFile(filePath, returnName(commandArray));

        //Filter rows by where clause
        whereIndex = commandExistsIndex(commandArray, "WHERE");
        if(whereIndex != -1) {
            outputRows(commandArray, whereIndex + 1);
        }

        //Filter and output columns
        outputCols(columns, commandArray);
    }

    //Method to return name of table selected
    public String returnName(String[] commandArray) {
        int i = 0;

        while (i < commandArray.length) {
            if (commandArray[i].equalsIgnoreCase("FROM")) {
                return commandArray[i + 1];
            }
            i++;
        }
        return null;
    }

    //************************************** Manipulating rows  *************************************************//

    //Method to alter data for the rows we need
    public void outputRows(String[] commandArray, int whereIndex)
            throws ColumnDoesntExistException, ConversionException {

        int colIndex;

        if(!commandArray[whereIndex].equals("(")) {

            //Index of dataset column which matches name specified in command
            colIndex = colNum(commandArray[whereIndex]);
            if(colIndex == -1){
                ColumnDoesntExistException cdee = new ColumnDoesntExistException();
                throw cdee;
            }

            //Increment whereIndex
            whereIndex++;

            //Assess operator
            if (commandArray[whereIndex].equals("==")) {
                handleEqualsandNotOperator(commandArray, whereIndex, colIndex, false);
            } else if (commandArray[whereIndex].equals("!=")) {
                handleEqualsandNotOperator(commandArray, whereIndex, colIndex, true);
            }
            else if (commandArray[whereIndex].equals(">=") || commandArray[whereIndex].equals("<=")
                    || commandArray[whereIndex].equals(">") || commandArray[whereIndex].equals("<")) {
                handleInequality(commandArray, whereIndex, colIndex, commandArray[whereIndex]);
            }
            else if (commandArray[whereIndex].equalsIgnoreCase("LIKE")) {
                handleLikeOperator(commandArray, whereIndex, colIndex);
            }

            removeRows();
        }
    }

    //Method to handle Like operator
    private void handleLikeOperator(String[] commandArray, int whereIndex, int colIndex)
            throws ConversionException{

        int k, j = 1;
        String tableValue, commandValue;

        //Increment whereIndex
        whereIndex++;

        //Convert command if it can be, else throw exception
        if(commandArray[whereIndex].charAt(0) == '\'') {
            commandValue = removeApostrophe(commandArray[whereIndex]);
        }
        else{
            ConversionException ce = new ConversionException();
            throw ce;
        }

        //Initialise the keep row arraylist (NOTE: Always keep top row)
        keepRows.add(1);
        for (k = 1; k < dataset.size(); k++) {
            keepRows.add(0);
        }

        //Walk through rows until a match is found
        while (j < dataset.size()) {

            //Check a conversion can be made
            if(!dataset.get(j).get(colIndex).matches("^-?\\d*\\.{0,1}\\d+$") &&
                    !dataset.get(j).get(colIndex).equals("true") &&
                    !dataset.get(j).get(colIndex).equals("false")) {
                tableValue = dataset.get(j).get(colIndex);
            }
            else{
                ConversionException ce = new ConversionException();
                throw ce;
            }

            //If match then flip bit in keepRows
            if (tableValue.contains(commandValue)) {
                keepRows.set(j, 1);
            }
            j++;
        }
    }

    //Method to handle inequality operators
    private void handleInequality(String[] commandArray, int whereIndex, int colIndex, String inequality)
            throws ConversionException{

        int k, j = 1;
        float tableValue, commandValue;

        //Increment whereIndex
        whereIndex++;

        //Convert command if it can be, else throw exception
        if(commandArray[whereIndex].matches("^-?\\d*\\.{0,1}\\d+$")) {
            commandValue = Float.parseFloat(commandArray[whereIndex]);
        }
        else{
            ConversionException ce = new ConversionException();
            throw ce;
        }

        //Initialise the keep row arraylist (NOTE: Always keep top row)
        keepRows.add(1);
        for (k = 1; k < dataset.size(); k++) {
            keepRows.add(0);
        }

        //Walk through rows until a match is found
        while (j < dataset.size()) {

            //Check a conversion can be made
            if(dataset.get(j).get(colIndex).matches("^-?\\d*\\.{0,1}\\d+$")) {
                tableValue = Float.parseFloat(dataset.get(j).get(colIndex));
            }
            else{
                ConversionException ce = new ConversionException();
                throw ce;
            }

            if(inequality.equals(">=")){
                //If match then flip bit in keepRows
                if (tableValue >= commandValue) {
                    keepRows.set(j, 1);
                }
            }
            else if(inequality.equals("<=")){
                //If match then flip bit in keepRows
                if (tableValue <= commandValue) {
                    keepRows.set(j, 1);
                }
            }
            else if(inequality.equals(">")){
                //If match then flip bit in keepRows
                if (tableValue > commandValue) {
                    keepRows.set(j, 1);
                }
            }
            else if(inequality.equals("<")){
                //If match then flip bit in keepRows
                if (tableValue < commandValue) {
                    keepRows.set(j, 1);
                }
            }
            j++;
        }
    }

    //Method to handle '==','!=' operators
    private void handleEqualsandNotOperator(String[] commandArray, int whereIndex, int colIndex, boolean notEquals){

        int k, j = 0, startVal, endVal;
        String noApostrophe;

        if(notEquals == true){
            startVal = 1;
            endVal = 0;
        }
        else{
            startVal = 0;
            endVal = 1;
        }

        //Increment whereIndex
        whereIndex++;

        noApostrophe = removeApostrophe(commandArray[whereIndex]);

        //Initialise the keep row arraylist (NOTE: Always keep top row)
        keepRows.add(1);
        for (k = 1; k < dataset.size(); k++) {
            keepRows.add(startVal);
        }

        //Walk through rows until a match is found
        while (j < dataset.size()) {

            //If match then flip bit in keepRows
            if (noApostrophe.equals(dataset.get(j).get(colIndex))) {
                keepRows.set(j, endVal);
            }
            j++;
        }
    }

    //Method to remove rows
    public void removeRows(){

        int i;

        //Loop through rows and remove backwards as the index changes as you go
        for (i = keepRows.size() - 1; i >= 0; i--) {
            if (keepRows.get(i) == 0) {
                dataset.remove(i);
            }
        }
    }

    //***********************************************************************************************************//

    //************************************** Manipulating columns ***********************************************//
    //Method to output a new dataset of columns
    public void outputCols(String[] columns, String[] commandArray) throws ColumnDoesntExistException{

        int i = 1, j, k, matches;

        if (commandArray[1].equals("*")) {
            return;
        }
        else {

            //Initialise the keep column arraylist
            for (k = 0; k < dataset.get(0).size(); k++) {
                keepColumns.add(0);
            }

            //Walk through commands
            while (!commandArray[i].equalsIgnoreCase("FROM")) {

                matches = 0;

                //Walk through column headers until a match is found
                for (j = 0; j < dataset.get(0).size(); j++) {

                    //If match then flip bit in keepColumns
                    if (removeComma(commandArray[i]).equals(dataset.get(0).get(j))) {
                        matches++;
                        keepColumns.set(j, 1);
                    }
                }
                if (matches == 0) {
                    ColumnDoesntExistException cdee = new ColumnDoesntExistException();
                    throw cdee;
                }
                i++;
            }
        }
        //Remove columns where bits are 0;
        removeColumns();
    }


    //Method to remove columns
    public void removeColumns(){

        int i, j;

        //Loop through rows
        for(i = 0; i < dataset.size(); i++){

            //Loop through columns removing those which match bit = 0
            //Note you have to go backwards else the index changes as it removes!
            for (j = keepColumns.size() - 1; j >= 0; j--) {
                if (keepColumns.get(j) == 0) {
                    dataset.get(i).remove(j);
                }
            }
        }
    }

    //*************************************************************************************************************//

    //Method to return string to console
    public String returnSelectString(){

        //With brackets and commas
        int i;
        StringBuilder sb = new StringBuilder();

        for (i = 0; i < dataset.size(); i++) {
            sb.append(dataset.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }
}
