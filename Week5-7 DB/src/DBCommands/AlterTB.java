package DBCommands;

import DBExceptions.ColumnDoesntExistException;
import DBExceptions.FileMissingException;

public class AlterTB extends DBcmd {

    public AlterTB(String filePath, String[] commandArray)
            throws FileMissingException, ColumnDoesntExistException {

        readInFile(filePath, commandArray[2]);

        if(commandArray[3].equalsIgnoreCase("ADD")){

            addColumn(commandArray[4]);
        }
        else if(commandArray[3].equalsIgnoreCase("DROP")){

            dropColumn(commandArray[4]);
        }

        //Write new altered dataset to txt file
        wipeAndWritetoFile(filePath, commandArray[2], dataset);
    }

    //Method to add a new column
    public void addColumn(String name){

        dataset.get(0).add(name);
    }

    //Method to drop a specified column
    public void dropColumn(String name) throws ColumnDoesntExistException{

        int colWhereIndex, i;

        //Index of dataset column which matches name specified in WHERE command
        colWhereIndex = colNum(name);

        if(colWhereIndex == -1){
            ColumnDoesntExistException cdee = new ColumnDoesntExistException();
            throw cdee;
        }

        //Loop through removing column
        for(i = 0; i < dataset.size(); i++){
            dataset.get(i).remove(colWhereIndex);
        }
    }
}
