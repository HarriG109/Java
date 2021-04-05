package DBCommands;

import DBExceptions.DiffInNumOfColsException;
import DBExceptions.FileMissingException;
import DBExceptions.NoColumnsException;
import DBParse.DBParser;
import java.io.*;

public class InsertRow extends DBcmd {

    public String[] columns;
    public File newTB;
    public String fullFilePath;
    BufferedWriter bw = null;
    BufferedReader br = null;

    public InsertRow(String filePath, String[] commandArray, BufferedWriter socketWriter)
            throws FileMissingException, NoColumnsException, DiffInNumOfColsException {

        //Get number of columns specified
        int numInsertCols = whichArrayElementEqualTo(commandArray, ")") -
                whichArrayElementEqualTo(commandArray, "(") - 1;

        //Get full filepath from commands
        fullFilePath = filePath + File.separator + commandArray[2] + ".tab";

        //Try writing to file
        try {
            newTB = new File(fullFilePath);
            br = new BufferedReader(new FileReader(fullFilePath));

            //Check for existing file
            if (newTB.exists()) {
                if(idNum(fullFilePath) != 0) {
                    if(getColumnAmount(br) == numInsertCols) {
                        br.close();
                        writeToFile(fullFilePath, newTB, commandArray);
                    }
                    else{
                        DiffInNumOfColsException dnce = new DiffInNumOfColsException();
                        throw dnce;
                    }
                }
                else{
                    NoColumnsException nce = new NoColumnsException();
                    throw nce;
                }
            } else {
                FileMissingException fme = new FileMissingException();
                throw fme;
            }
            socketWriter.flush();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Method to get column number
    public int getColumnAmount(BufferedReader br) {

        String rowStr;

        try {
            //Get into string
            rowStr = br.readLine();

            //Split into 1D array
            columns = rowStr.split("\t");

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return number of elements -1 for id column
        return columns.length - 1;
    }


    //Method to write to created file
    public void writeToFile(String fullFilePath, File newTB, String[] commandArray){

        //Find starting position of first bracket and take 1st subsequent element
        int i = whichArrayElementEqualTo(commandArray, "(") + 1;
        int id;

        try {
            if(newTB.isFile()) {
                //Use append option in the filewriter to create new lines
                FileWriter fw = new FileWriter(fullFilePath, true);
                bw = new BufferedWriter(fw);

                //Return ID number
                id = idNum(fullFilePath);

                //Create ID column
                bw.write("\n");
                bw.write(String.valueOf(id));
                bw.write("\t");

                //Loop through from instance of open bracket to instance of close bracket outputting specified variables
                while (i < whichArrayElementEqualTo(commandArray, ")")) {

                    //Remove commas and apostrophes prior to outputting
                    bw.write(removeApostrophe(removeComma(commandArray[i])));
                    bw.write("\t");
                    i++;
                }
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe);
        }
        finally {
            try{
                //Close buffered writer
                if(bw!=null) {
                    bw.close();
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
                System.out.println("Error in closing the BufferedWriter" + ex);
            }
        }
    }

    //Method to get number of rows for id
    public int idNum(String fullFilePath){

        int lines = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fullFilePath));
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return lines;
    }
}
