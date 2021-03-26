package DBCommands;
import DBExceptions.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class CreateDB {

    public CreateDB(String filePath, String name, BufferedWriter socketWriter) throws FolderExistsException {
        createNewFolder(filePath, name, socketWriter);
    }

    public void createNewFolder(String filePath, String name, BufferedWriter socketWriter) throws FolderExistsException {

        //Create new path from command name
        File newDB = new File(filePath + File.separator + name);

        //Check folder doesn't already exist and create it
        if(newDB.exists()){
            FolderExistsException e = new FolderExistsException();
            throw e;
        }
        else{
            if(newDB.mkdir() == false) {
                try {
                    socketWriter.write("Issue creating folder, please try again!");
                    socketWriter.flush();
                }
                catch(IOException ioe) {
                    ioe.printStackTrace();
                    System.err.println(ioe);
                }
            }
        }

    }
}
