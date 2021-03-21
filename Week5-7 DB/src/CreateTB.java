import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CreateTB {

    public File newTB;
    public String fullFilePath;
    BufferedWriter bw = null;

    CreateTB(String filePath, String[] commandArray, BufferedWriter socketWriter, int index){

        fullFilePath = filePath + File.separator + commandArray[2] + ".tab";

        createFile(fullFilePath, socketWriter);

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

                //Printing commands for checking
                /*while (j < commandArray.length){
                    socketWriter.write(commandArray[j] + "\n");
                    j++;
                }*/

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
    public void createFile(String fullFilePath, BufferedWriter socketWriter){
        try {
            newTB = new File(fullFilePath);

            if (!newTB.exists()) {
                newTB.createNewFile();
                socketWriter.write("File created: " + newTB.getName());
            } else {
                socketWriter.write("[ERROR] File already exists.");
            }
            socketWriter.flush();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe);
        }
    }

    //Method to return element number of array which matches symbol
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

    //Method to remove comma's
    public String removeComma(String element){
        return element.replace(",", "");
    }
}
