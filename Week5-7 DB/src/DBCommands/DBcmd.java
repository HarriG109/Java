package DBCommands;

import DBExceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DBcmd {

    public String returnText;
    public String[] rows;
    public ArrayList<ArrayList<String>> dataset = new ArrayList<ArrayList<String>>();
    public ArrayList<String> incomingRows = new ArrayList<String>();

    public DBcmd(String filePath, String[] tokenArray, BufferedWriter socketWriter)
            throws FolderExistsException, FolderMissingException, FileExistsException, FileMissingException,
            UsingDatabaseException, NoColumnsException, DiffInNumOfColsException, ColumnDoesntExistException {
        processCMD(filePath, tokenArray, socketWriter);
    }

    public DBcmd() {
    }

    public void processCMD(String filePath, String[] commandArray, BufferedWriter socketWriter)
            throws FolderExistsException, FolderMissingException, FileExistsException, FileMissingException,
            UsingDatabaseException, NoColumnsException, DiffInNumOfColsException, ColumnDoesntExistException {

        if(commandArray[0].equals("CREATE")){

            //CREATE DATABASE Class
            if(commandArray[1].equals("DATABASE")){

                //Instantiate object
                CreateDB newDB = new CreateDB(filePath, commandArray[2], socketWriter);
            }

            //CREATE TABLE Class
            else if(commandArray[1].equals("TABLE")){

                //Instantiate object
                CreateTB newTB = new CreateTB(filePath, commandArray, socketWriter);
            }
        }

        //USE Class
        else if(commandArray[0].equals("USE")) {

            //Instantiate object
            UseDB useDB = new UseDB(commandArray[1]);
        }

        //INSERT Class
        else if(commandArray[0].equals("INSERT")){

            if(commandArray[1].equals("INTO")){

                //Instantiate object
                InsertRow newRow = new InsertRow(filePath, commandArray, socketWriter);
            }
        }

        //SELECT Class
        else if(commandArray[0].equals("SELECT")){

            //Instantiate object
            SelectRow getRow = new SelectRow(filePath, commandArray);
            setReturnString(getRow.returnSelectString());
        }

        //DROP Class
        else if(commandArray[0].equals("DROP")){

            if(commandArray[1].equals("TABLE")){

                //Instantiate object
                DropTB dTB = new DropTB(filePath, commandArray[2]);
            }
            if(commandArray[1].equals("DATABASE")){

                //Instantiate object
                DropDB dDB = new DropDB(filePath, commandArray[2]);
            }
        }

        //UPDATE class
        else if(commandArray[0].equals("UPDATE")){

            //Instantiate object
            UpdateRow uRow = new UpdateRow(filePath, commandArray);
        }

        //DELETE class
        else if(commandArray[0].equals("DELETE")){

            //Instantiate object
            DeleteRow dRow = new DeleteRow(filePath, commandArray);
        }
    }

    //Method to return string
    public String returnString(){
        return returnText;
    }

    //Method to set return String
    public void setReturnString(String text){
        returnText = text;
    }

    //Method to remove apostrophe's
    public String removeApostrophe(String element){
        return element.replace("'", "");
    }

    //Method to remove comma's
    public String removeComma(String element){
        return element.replace(",", "");
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

    //*******************************************************************************************************//

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
