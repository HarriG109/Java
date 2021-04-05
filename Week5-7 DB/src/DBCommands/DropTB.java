package DBCommands;

import DBExceptions.FileMissingException;

import java.io.File;

public class DropTB {

    public File newTB;
    public String fullFilePath;

    public DropTB(String filePath, String name) throws FileMissingException {

        fullFilePath = filePath + File.separator + name + ".tab";

        //Delete specified table
        removeTB(fullFilePath);
    }

    //Method to delete specified table
    public void removeTB(String fullFilePath) throws FileMissingException{

        newTB = new File(fullFilePath);

        if (newTB.exists()) {
            newTB.delete();
        } else {
            FileMissingException fme = new FileMissingException();
            throw fme;
        }
    }
}
