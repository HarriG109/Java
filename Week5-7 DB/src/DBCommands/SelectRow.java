package DBCommands;

import DBExceptions.ColumnDoesntExistException;
import DBExceptions.FileMissingException;
import DBExceptions.NoColumnsException;
import java.io.*;
import java.util.*;

public class SelectRow {

    public String[] columns;
    public String[] rows;
    public BufferedReader br = null;
    public ArrayList<ArrayList<String>> dataset = new ArrayList<ArrayList<String>>();
    public ArrayList<String> incomingRows = new ArrayList<String>();
    //Arraylist of kept columns -> 0 means not kept -> 1 means kept
    public ArrayList<Integer> keepColumns = new ArrayList<Integer>();
    public ArrayList<Integer> keepRows = new ArrayList<Integer>();

    public SelectRow(String filePath, String[] commandArray)
            throws FileMissingException, NoColumnsException, ColumnDoesntExistException {

        int i, whereIndex;

        //Read in the file and store as data
        if (returnName(commandArray) != null) {
            readInFile(filePath, returnName(commandArray));
        }

        //Filter rows by where clause
        whereIndex = commandExistsIndex(commandArray, "WHERE");
        if(whereIndex != -1) {
            outputRows(commandArray, whereIndex + 1);
        }

        //Filter and output columns
        outputCols(columns, commandArray);

        for (i = 0; i < dataset.size(); i++) {
            System.out.println(dataset.get(i));
        }

        /*Convert to string to print back to console;
        selectText=returnSelectString();*/
    }

    //************************************* Storing of data **************************************************//

    //Method to return name of table selected
    public String returnName(String[] commandArray) {
        int i = 0;

        while (i < commandArray.length) {
            if (commandArray[i].equals("FROM")) {
                return commandArray[i + 1];
            }
            i++;
        }
        return null;
    }

    //Method to read in file
    public void readInFile(String filePath, String name) throws FileMissingException, NoColumnsException {

        try {
            File selectTB = new File(filePath + File.separator + name + ".tab");

            if (selectTB.exists()) {
                br = new BufferedReader(new FileReader(filePath + File.separator + name + ".tab"));

                //Store data
                storeData(dataset, br);

                br.close();
            } else {
                FileMissingException fme = new FileMissingException();
                throw fme;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method to store data
    public void storeData(ArrayList<ArrayList<String>> ArrayList, BufferedReader br) {

        int j;
        String rowStr;

        try {
            //Get into string
            rowStr = br.readLine();

            //Loop through lines
            while (rowStr != null) {

                //Split into 1D array
                rows = rowStr.split("\t");

                //Clear arraylist every loop
                incomingRows = new ArrayList<>(Arrays.asList(rows));

                ArrayList.add(incomingRows);

                rowStr = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //***********************************************************************************************************//

    //************************************** Manipulating rows  *************************************************//
    //Method to return index of command
    public int commandExistsIndex(String[] commandArray, String command) {
        int i = 0;

        while (i < commandArray.length) {
            if (commandArray[i].equals(command)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    //Method to alter data for the rows we need
    public void outputRows(String[] commandArray, int whereIndex) {

        int k;

        //Increment index
        whereIndex++;

        if(commandArray[whereIndex].equals("==")){

            //Initialise the keep column arraylist
            for (k = 0; k < dataset.size(); k++) {
                keepRows.add(0);
            }
            /*
            colIndex = conditionDataIndex(commandArray[], );
*/
            /*Walk through column headers until a match is found
            while (j < dataset.get(0).size()) {

                matches = 0;

                //If match then flip bit in keepColumns
                if (commandArray[i].equals(dataset.get(0).get(j))) {
                    matches++;
                    keepColumns.set(j, 1);
                }
                if(matches == 0){
                    ColumnDoesntExistException cdee = new ColumnDoesntExistException();
                    throw cdee;
                }
                j++;
            }*/
        }
    }

    //Method to return the index of the data arraylist
    public int conditionDataIndex(String[] commandArray, String name) throws ColumnDoesntExistException {

        int j = 0;

        //Walk through data column headers until a match is found
        while (j < dataset.get(0).size()) {

            //Check name exists in column headers
            if (name.equals(dataset.get(0).get(j))) {
                //Return index name was found at
                return j;
            }
            j++;
        }
        //If nothing is found throw exception
        ColumnDoesntExistException cdee = new ColumnDoesntExistException();
        throw cdee;
    }

    //Method to return name of table selected
    public String returnColName(String[] commandArray) {
        int i = 0;

        while (i < commandArray.length) {
            if (commandArray[i].equals("WHERE")) {
                return commandArray[i + 1];
            }
            i++;
        }
        return null;
    }

    //***********************************************************************************************************//

    //************************************** Manipulating columns ***********************************************//
    //Method to output a new dataset of columns
    public void outputCols(String[] columns, String[] commandArray) throws ColumnDoesntExistException{

        int i = 1, j = 0, k, matches;

        //Initialise the keep column arraylist
        for (k = 0; k < dataset.get(0).size(); k++) {
            keepColumns.add(0);
        }

        if (commandArray[1].equals("*")) {
            return;
        } else {

            //Walk through commands
            while (!commandArray[i].equals("FROM")) {

                //Walk through column headers until a match is found
                while (j < dataset.get(0).size()) {

                    matches = 0;

                    //If match then flip bit in keepColumns
                    if (commandArray[i].equals(dataset.get(0).get(j))) {
                        matches++;
                        keepColumns.set(j, 1);
                    }
                    if(matches == 0){
                        ColumnDoesntExistException cdee = new ColumnDoesntExistException();
                        throw cdee;
                    }
                    j++;
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
}
