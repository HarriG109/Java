package DBCommands;

import DBExceptions.FolderMissingException;
import java.io.File;

public class UseDB extends DBCommands.DBPath {

    String newDBPath, origDBPath;

    public UseDB(String name) throws FolderMissingException {
        setUsePath(name);
    }

    //Convert command string to array for better handling
    public void setUsePath(String name) throws FolderMissingException {

        //Create new file paths from command name
        File newDB = new File(System.getProperty("user.dir") + File.separator + "Databases" + File.separator + name);

        //Create Strings to store
        newDBPath =  System.getProperty("user.dir") + File.separator + "Databases" + File.separator + name;
        origDBPath = getPath();

        if(newDB.isDirectory()){
            setPath(newDBPath);
        }
        else{
            setPath(origDBPath);
            FolderMissingException e = new FolderMissingException();
            throw e;
        }
    }
}
