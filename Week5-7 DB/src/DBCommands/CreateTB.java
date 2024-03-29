package DBCommands;

import DBExceptions.FileExistsException;

import java.io.*;

public class CreateTB extends DBcmd {

    public File newTB;
    public String fullFilePath;
    BufferedWriter bw = null;
    int j = 0;

    public CreateTB(String filePath, String[] commandArray, BufferedWriter socketWriter) throws FileExistsException {


        fullFilePath = filePath + File.separator + commandArray[2] + ".tab";

        createFile(fullFilePath);

        if(commandArray.length > 3) {
            writeToFile(fullFilePath, newTB, commandArray, socketWriter);
        }
    }

    //Method to write to created file
    public void writeToFile(String fullFilePath, File newTB, String[] commandArray, BufferedWriter socketWriter){

        //Find starting position of first bracket and take 1st subsequent element
        int i = whichArrayElementEqualTo(commandArray, "(") + 1;

        try {
            if(newTB.isFile()) {
                FileWriter fw = new FileWriter(fullFilePath);
                bw = new BufferedWriter(fw);

                //Create ID column
                bw.write("id");
                bw.write("\t");

                //Loop through from instance of open bracket to instance of close bracket outputting specified variables
                while (i < whichArrayElementEqualTo(commandArray, ")")) {

                    //Remove commas prior to outputting
                    bw.write(removeComma(commandArray[i]));
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


    //Create the table file using filepath and name from command
    public void createFile(String fullFilePath) throws FileExistsException {
        try {
            newTB = new File(fullFilePath);

            if (!newTB.exists()) {
                newTB.createNewFile();
            } else {
                FileExistsException fme = new FileExistsException();
                throw fme;
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe);
        }
    }

    //Method to remove comma's
    public String removeComma(String element){
        return element.replace(",", "");
    }
}
