package DBCommands;

import java.io.*;

public class SelectRow {

    static int k = 0;
    public String[][] incomingData = new String[1000][100];
    public String[][] outgoingData = new String[1000][100];
    public String[] columns;
    public String[] rows;
    public int[] columnNum;
    public BufferedReader br = null;

    public SelectRow(String filePath, String[] commandArray, BufferedWriter socketWriter){

        //Read in the file and store as data
        if (returnName(commandArray) != null) {
            readInFile(filePath, returnName(commandArray), socketWriter);
        }

        //Output columns
        outputCols(columns, commandArray);
    }

    //Method to return name of table selected
    public String returnName(String[] commandArray){
        int i = 0;

        while(i < commandArray.length){
            if(commandArray[i].equals("FROM")){
                return commandArray[i + 1];
            }
            i++;
        }
        return null;
    }

    //Method to read in file
    public void readInFile(String filePath, String name, BufferedWriter socketWriter){

        int i, j;

        try {
            File selectTB = new File(filePath + File.separator + name + ".tab");
            br = new BufferedReader(new FileReader(filePath + File.separator + name + ".tab"));

            if (selectTB.exists()) {

                //Store first line as column headers
                storeCol(br, socketWriter);

                //Store remaining data
                storeData(br);

                /*Print
                for(i=0; i < outgoingData.length; i++){
                    for(j=0; j < outgoingData[i].length; j++){
                        if(outgoingData[i][j] != null){
                            System.out.println(outgoingData[i][j]);
                        }
                    }
                }*/

                br.close();
            }
            else {
                socketWriter.write("[Error] File doesn't exist");
                socketWriter.flush();
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe);
        }
    }

    //Method to store column headers
    public void storeCol(BufferedReader br, BufferedWriter socketWriter){

        String columnStr = null;

        try {
            //Read in first line
            columnStr = br.readLine();

            //Split into column array
            if(columnStr != null) {
                columns = columnStr.split("\t");
            }
            else{
                socketWriter.write("[Error] No columns in file");
                socketWriter.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method to store data
    public void storeData(BufferedReader br){

        int i = 0, j;
        String rowStr = null;

        try {
            //Get into string
            rowStr = br.readLine();

            //Loop through lines
            while(rowStr != null){


                //Split into 1D array
                rows = rowStr.split("\t");

                //Copy into 2D array
                for (j = 0; j < rows.length; j++) {
                    incomingData[i][j] = rows[j];
                }

                i++;
                rowStr = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method to output a new dataset of columns
    public void outputCols(String[] columns, String[] commandArray){

        int i = 1, j = 0;

        if(commandArray[1] == "*"){
            outgoingData = incomingData;
        }
        else {
            while (!commandArray[i].equals("FROM")) {
                System.out.println("Here");
                while(j < columns.length) {
                    if (commandArray[i] == columns[j]) {
                        populateNewData(outgoingData, incomingData, j);
                    }
                    j++;
                }
                i++;
            }
        }
    }

    //Method to store the new data
    public void populateNewData(String[][] outgoingData, String[][] incomingData, int j){

        int i = 0;

        while(incomingData[i][j] != null){
            outgoingData[i][k] = incomingData[i][j];
            System.out.println(outgoingData[i][k]);
            i++;
        }
        k++;
    }

    /*Method to create temporary dataset
    public convertData(String filePath, String[] commandArray, BufferedWriter socketWriter){
    }

    //Method to analyze variables
    public void(String filePath, String[] commandArray, BufferedWriter socketWriter)*/

    /*Print the data selected
    public void printSelect(){
        int i, j;

        for(i=0; i < incomingData.length; i++){
            for(j=0; j < incomingData[i].length; j++){
                if(incomingData[i][j] != null){
                    System.out.println(incomingData[i][j]);
                }
            }
        }
    }*/
}
