package DBCommands;

import DBExceptions.FolderExistsException;
import DBExceptions.FolderMissingException;

import java.io.BufferedWriter;

public class DBcmd{

    public DBcmd(String filePath, String[] tokenArray, BufferedWriter socketWriter)
            throws FolderExistsException, FolderMissingException {
        processCMD(filePath, tokenArray, socketWriter);
    }

    public void processCMD(String filePath, String[] commandArray, BufferedWriter socketWriter)
            throws FolderExistsException, FolderMissingException {

        if(commandArray[0].equals("CREATE")){

            //CREATE DATABASE Class
            if(commandArray[1].equals("DATABASE")){

                //Instantiate object
                CreateDB newDB = new CreateDB(filePath, commandArray[2], socketWriter);
            }

            //CREATE TABLE Class
            else if(commandArray[1].equals("TABLE")){

                //Instantiate object
                CreateTB newTB = new CreateTB(filePath, commandArray, socketWriter);
            }
        }

        //USE Class
        else if(commandArray[0].equals("USE")) {

            //Instantiate object
            UseDB useDB = new UseDB(commandArray[1]);
        }

        //INSERT Class
        else if(commandArray[0].equals("INSERT")){

            if(commandArray[1].equals("INTO")){

                //Instantiate object
                InsertRow newRow = new InsertRow(filePath, commandArray, socketWriter);
            }
        }

        //SELECT Class
        else if(commandArray[0].equals("SELECT")){

            //Instantiate object
            SelectRow getRow = new SelectRow(filePath, commandArray, socketWriter);
        }

        //DROP Class
        else if(commandArray[0].equals("DROP")){

            if(commandArray[1].equals("TABLE")){

                //Instantiate object
                DeleteTB dTB = new DeleteTB(filePath, commandArray[2], socketWriter);
            }
            if(commandArray[1].equals("DATABASE")){

                //Instantiate object
                DeleteDB dDB = new DeleteDB(filePath, commandArray[2], socketWriter);
            }
        }
    }
}
