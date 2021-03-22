import java.io.*;

public class InsertRow {

    public File newTB;
    public String fullFilePath;
    BufferedWriter bw = null;
    int id;

    public InsertRow(String filePath, String[] commandArray, BufferedWriter socketWriter){

        //Get full filepath from commands
        fullFilePath = filePath + File.separator + commandArray[2] + ".tab";

        //Try writing to file
        try {
            newTB = new File(fullFilePath);

            //Check for existing file
            if (newTB.exists()) {
                writeToFile(fullFilePath, newTB, commandArray, socketWriter, id);
            } else {
                socketWriter.write("[ERROR] File doesn't exist");
            }
            socketWriter.flush();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe);
        }
    }

    //Method to write to created file
    public void writeToFile(String fullFilePath, File newTB, String[] commandArray, BufferedWriter socketWriter, int id){

        //Find starting position of first bracket and take 1st subsequent element
        int i = whichArrayElementEqualTo(commandArray, "(") + 1;

        try {
            if(newTB.isFile()) {
                //Use append option in the filewriter to create new lines
                FileWriter fw = new FileWriter(fullFilePath, true);
                bw = new BufferedWriter(fw);

                //Printing commands for checking
                /*while (j < commandArray.length){
                    socketWriter.write(commandArray[j] + "\n");
                    j++;
                }*/

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

    //Method to remove apostrophe's
    public String removeApostrophe(String element){
        return element.replace("'", "");
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
            System.out.println(ioe);
        }

        //Remove top row as it shouldn't be couted in ID
        return lines;
    }
}
