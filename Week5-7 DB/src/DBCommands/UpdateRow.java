package DBCommands;

import DBExceptions.ColumnDoesntExistException;
import DBExceptions.FileMissingException;
import DBExceptions.NoColumnsException;

import java.io.*;
import java.util.ArrayList;

public class UpdateRow extends DBcmd {

    public ArrayList<Integer> updateRows = new ArrayList<Integer>();

    public UpdateRow(String filePath, String[] commandArray)
            throws FileMissingException, ColumnDoesntExistException {

        int i, j;
        int whereIndex;

        //Read in the file and store as data
        readInFile(filePath, commandArray[1]);

        //Get index of WHERE command
        whereIndex = commandExistsIndex(commandArray, "WHERE");
        //Increment to get the start of the condition
        alterRow(commandArray, whereIndex + 1);

        //Write new altered dataset to txt file
        wipeAndWritetoFile(filePath, commandArray[1], dataset);
    }

    //************************************** Manipulating rows  *************************************************//

    //Method to alter data for the rows we need
    public void alterRow(String[] commandArray, int whereIndex) throws ColumnDoesntExistException {

        int colWhereIndex, i;

        //Index of dataset column which matches name specified in WHERE command
        colWhereIndex = colNum(commandArray[whereIndex]);

        if(colWhereIndex == -1){
            ColumnDoesntExistException cdee = new ColumnDoesntExistException();
            throw cdee;
        }

        //Increment whereIndex
        whereIndex++;

        //Assess operator
        if(commandArray[whereIndex].equals("==")) {
            handleEqualsandNotOperator(commandArray, whereIndex, colWhereIndex, false);
        }
        else if(commandArray[whereIndex].equals("!=")) {
            handleEqualsandNotOperator(commandArray, whereIndex, colWhereIndex, true);
        }
        //TODO: All operators

        //Edit the needed rows
        editRows(commandArray);
    }

    //Method to handle '==','!=' operators
    private void handleEqualsandNotOperator(String[] commandArray, int whereIndex, int colWhereIndex, boolean notEquals){

        int k, j = 1, startVal, endVal;
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

        //Initialise the alter row arraylist (NOTE: Never edit top row)
        updateRows.add(0);
        for (k = 1; k < dataset.size(); k++) {
            updateRows.add(startVal);
        }

        //Walk through rows until a match is found
        while (j < dataset.size()) {

            //If match then flip bit in updateRows
            if (noApostrophe.equals(dataset.get(j).get(colWhereIndex))) {
                //Needs to be 1 minus to account for top row
                updateRows.set(j, endVal);
            }
            j++;
        }
    }

    //Method to remove rows
    public void editRows(String[] commandArray) throws ColumnDoesntExistException{

        int i, colIndex, valIndex;
        String noApostrophe;

        //Index of SET column matched with dataset column if it exists
        colIndex = colNum(commandArray[commandExistsIndex(commandArray, "SET") + 1]);
        if(colIndex == -1){
            ColumnDoesntExistException cdee = new ColumnDoesntExistException();
            throw cdee;
        }

        //Create non-apostophe value to go into file
        noApostrophe= removeApostrophe(commandArray[commandExistsIndex(commandArray, "SET") + 3]);

        //Loop through rows altering value if bit is set to 1
        for (i = 0 ; i < dataset.size(); i++) {

            if (updateRows.get(i) == 1) {
                dataset.get(i).set(colIndex, noApostrophe);
            }
        }
    }

    //***********************************************************************************************************//
}
