package DBCommands;

import DBExceptions.*;

import java.io.BufferedWriter;

public class DBcmd {

    public String returnText;

    public DBcmd(String filePath, String[] tokenArray, BufferedWriter socketWriter)
            throws FolderExistsException, FolderMissingException, FileExistsException, FileMissingException,
            UsingDatabaseException, NoColumnsException, DiffInNumOfColsException, ColumnDoesntExistException {
        processCMD(filePath, tokenArray, socketWriter);
    }

    public void processCMD(String filePath, String[] commandArray, BufferedWriter socketWriter)
            throws FolderExistsException, FolderMissingException, FileExistsException, FileMissingException,
            UsingDatabaseException, NoColumnsException, DiffInNumOfColsException, ColumnDoesntExistException {

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
            SelectRow getRow = new SelectRow(filePath, commandArray);
            /*setReturnString(getRow.returnSelectString());*/
        }

        //DROP Class
        else if(commandArray[0].equals("DROP")){

            if(commandArray[1].equals("TABLE")){

                //Instantiate object
                DropTB dTB = new DropTB(filePath, commandArray[2]);
            }
            if(commandArray[1].equals("DATABASE")){

                //Instantiate object
                DropDB dDB = new DropDB(filePath, commandArray[2]);
            }
        }
    }

    //Method to return string
    public String returnString(){
        return returnText;
    }

    //Method to set return String
    public void setReturnString(String text){
        returnText = text;
    }
}
