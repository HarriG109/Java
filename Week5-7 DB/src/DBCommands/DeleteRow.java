package DBCommands;

import DBExceptions.ColumnDoesntExistException;
import DBExceptions.FileMissingException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DeleteRow extends DBcmd {

    public ArrayList<Integer> deleteRows = new ArrayList<Integer>();

    public DeleteRow(String filePath, String[] commandArray)
            throws FileMissingException, ColumnDoesntExistException{

        int whereIndex, i;

        //Read in the file and store as data
        readInFile(filePath, commandArray[2]);

        //Get index of WHERE command
        whereIndex = commandExistsIndex(commandArray, "WHERE");
        //Increment to get the start of the condition
        deleteRow(commandArray, whereIndex + 1);

        //Write new altered dataset to txt file
        wipeAndWritetoFile(filePath, commandArray[2], dataset);
    }

    //************************************** Manipulating rows  *************************************************

    //Method to alter data for the rows we need
    public void deleteRow(String[] commandArray, int whereIndex) throws ColumnDoesntExistException {

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
        deleteRows();
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

        //Initialise the alter row arraylist (NOTE: Always keep top row)
        deleteRows.add(0);
        for (k = 1; k < dataset.size(); k++) {
            deleteRows.add(startVal);
        }

        //Walk through rows until a match is found
        while (j < dataset.size()) {

            //If match then flip bit in updateRows
            if (noApostrophe.equals(dataset.get(j).get(colWhereIndex))) {
                //Needs to be 1 minus to account for top row
                deleteRows.set(j, endVal);
            }
            j++;
        }
    }

    //Method to remove rows
    public void deleteRows(){

        int i;

        //Loop through rows altering value if bit is set to 1
        for (i = dataset.size() - 1 ; i >= 0; i--) {
            //Minus one to exclude top row
            if (deleteRows.get(i) == 1) {
                dataset.remove(i);
            }
        }
    }
}
