package DBCommands;

import DBExceptions.FolderMissingException;
import DBExceptions.UsingDatabaseException;

import java.io.*;

public class DropDB extends DBCommands.DBPath {

    public File newTB;
    public String fullFilePath;

    public DropDB(String name) throws FolderMissingException, UsingDatabaseException {

        //Create full filepath string and file
        fullFilePath = System.getProperty("user.dir") + File.separator + "Databases" + File.separator + name;
        newTB = new File(fullFilePath);

        //Delete specified folder and contents
        if(!getPath().equals(fullFilePath)) {
            if (newTB.isDirectory()) {
                removeFolder(newTB);
            } else {
                FolderMissingException fme = new FolderMissingException();
                throw fme;
            }
        }
        else{
            UsingDatabaseException ube = new UsingDatabaseException();
            throw ube;
        }
    }

    //Recursive method to delete all files/folders within folder specified
    public boolean removeFolder(File folder) {

        File[] contents = folder.listFiles();

        if (contents != null) {
            //Delete subcontents if full of files
            for (File file : contents) {
                removeFolder(file);
            }
        }
        return folder.delete();
    }
}
