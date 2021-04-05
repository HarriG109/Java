package DBCommands;

import DBExceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DBcmd {

    //Text to return to console
    public String returnText;
    //Array to collect rows from table before converting into below arraylists
    public String[] rows;
    //Arraylist to store the data from specific commands
    public ArrayList<ArrayList<String>> dataset = new ArrayList<ArrayList<String>>();
    //Arraylist to store the incoming rows from a specified table
    public ArrayList<String> incomingRows = new ArrayList<String>();

    public DBcmd(String filePath, String[] tokenArray, BufferedWriter socketWriter)
            throws FolderExistsException, FolderMissingException, FileExistsException, FileMissingException,
            UsingDatabaseException, NoColumnsException, DiffInNumOfColsException, ColumnDoesntExistException,
            ConversionException {
        processCMD(filePath, tokenArray, socketWriter);
    }

    //Constructor needed for extends
    public DBcmd() {
    }

    //Method to process the incoming command
    public void processCMD(String filePath, String[] commandArray, BufferedWriter socketWriter)
            throws FolderExistsException, FolderMissingException, FileExistsException, FileMissingException,
            UsingDatabaseException, NoColumnsException, DiffInNumOfColsException, ColumnDoesntExistException,
            ConversionException {

        if(commandArray[0].equalsIgnoreCase("CREATE")){

            //CREATE DATABASE Class
            if(commandArray[1].equalsIgnoreCase("DATABASE")){

                //Instantiate object
                CreateDB newDB = new CreateDB(commandArray[2], socketWriter);
            }

            //CREATE TABLE Class
            else if(commandArray[1].equalsIgnoreCase("TABLE")){

                //Instantiate object
                CreateTB newTB = new CreateTB(filePath, commandArray, socketWriter);
            }
        }

        //USE Class
        else if(commandArray[0].equalsIgnoreCase("USE")) {

            //Instantiate object
            UseDB useDB = new UseDB(commandArray[1]);
        }

        //INSERT Class
        else if(commandArray[0].equalsIgnoreCase("INSERT")){

            if(commandArray[1].equalsIgnoreCase("INTO")){

                //Instantiate object
                InsertRow newRow = new InsertRow(filePath, commandArray, socketWriter);
            }
        }

        //SELECT Class
        else if(commandArray[0].equalsIgnoreCase("SELECT")){

            //Instantiate object
            SelectRow getRow = new SelectRow(filePath, commandArray);
            setReturnString(getRow.returnSelectString());
        }

        //DROP Class
        else if(commandArray[0].equalsIgnoreCase("DROP")){

            if(commandArray[1].equalsIgnoreCase("TABLE")){

                //Instantiate object
                DropTB dTB = new DropTB(filePath, commandArray[2]);
            }
            if(commandArray[1].equalsIgnoreCase("DATABASE")){

                //Instantiate object
                DropDB dDB = new DropDB(commandArray[2]);
            }
        }

        //UPDATE class
        else if(commandArray[0].equalsIgnoreCase("UPDATE")){

            //Instantiate object
            UpdateRow uRow = new UpdateRow(filePath, commandArray);
        }

        //DELETE class
        else if(commandArray[0].equalsIgnoreCase("DELETE")){

            //Instantiate object
            DeleteRow dRow = new DeleteRow(filePath, commandArray);
        }

        //ALTER class
        else if(commandArray[0].equalsIgnoreCase("ALTER")){

            //Instantiate object
            AlterTB aTB = new AlterTB(filePath, commandArray);
        }
    }

    //Method to return string for console
    public String returnString(){
        return returnText;
    }

    //Method to set return String for console
    public void setReturnString(String text){
        returnText = text;
    }

    //Method to remove apostrophe's from string
    public String removeApostrophe(String element){
        return element.replace("'", "");
    }

    //Method to remove comma's from string
    public String removeComma(String element){
        return element.replace(",", "");
    }

    //Method to return command element number of array which matches symbol
    public int whichArrayElementEqualTo(String[] commandArray, String text){
        int k = 0;

        while(k < commandArray.length){
            if(commandArray[k].equals(text)){
                return k;
            }
            k++;
        }
        return -1;
    }

    //Method to return index of command
    public int commandExistsIndex(String[] commandArray, String command) {
        int i = 0;

        while (i < commandArray.length) {
            if (commandArray[i].equalsIgnoreCase(command)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    //************************************* Storing of data **************************************************//

    //Method to read in file
    public void readInFile(String filePath, String name) throws FileMissingException {

        BufferedReader br;

        try {
            File selectTB = new File(filePath + File.separator + name + ".tab");

            if (selectTB.exists()) {
                br = new BufferedReader(new FileReader(filePath + File.separator + name + ".tab"));

                //Store data
                storeData(dataset, br);

                //Balance data for if there's more columns than data
                balanceData(dataset);

                br.close();
            } else {
                FileMissingException fme = new FileMissingException();
                throw fme;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method to store data from read in file
    public void storeData(ArrayList<ArrayList<String>> ArrayList, BufferedReader br) {

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

    //Method to balance out data if an alter is used which adds a blank column
    public void balanceData(ArrayList<ArrayList<String>> ArrayList){

        ArrayList<Integer> colCount = new ArrayList<Integer>();
        int i, j, colsToAdd;

        for(i = 0; i < ArrayList.size(); i++){
            colCount.add(ArrayList.get(i).size());
        }

        for(i = 0; i < ArrayList.size(); i++){

            colsToAdd = colCount.get(0) - colCount.get(i);

            while(colsToAdd != 0){

                ArrayList.get(i).add(" ");
                colsToAdd--;
            }
        }
    }

    //*******************************************************************************************************//

    //*************************************** Data Manipulation ************************************************//

    //Method to return the column index when a 'name' is found
    public int colNum(String name) {

        int j = 0;

        //Walk through data columns at index until a match is found
        while (j < dataset.get(0).size()) {

            //Check name exists in column headers
            if (name.equals(dataset.get(0).get(j))) {
                //Return index name was found at
                return j;
            }
            j++;
        }
        return -1;
    }

    //Method to wipe the table and write dataset to file
    public void wipeAndWritetoFile(String filePath, String name, ArrayList<ArrayList<String>> dataset){

        BufferedWriter bw;
        int i, j;

        try {

            bw = new BufferedWriter(new FileWriter(filePath + File.separator + name + ".tab"));

            for(i = 0; i < dataset.size(); i++) {
                for (j = 0; j < dataset.get(i).size(); j++) {
                    bw.write(dataset.get(i).get(j));
                    bw.write("\t");
                }
                bw.write("\n");
            }

            bw.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
