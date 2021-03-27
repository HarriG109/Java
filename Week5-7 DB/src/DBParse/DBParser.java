package DBParse;

import DBCommands.CreateDB;
import DBExceptions.SyntaxException;
import java.io.BufferedWriter;
import java.io.IOException;

public class DBParser {

    public static boolean parsed;
    public static int index;

    public DBParser(String filePath, String[] commandArray, BufferedWriter socketWriter){
        parseCMD(filePath, commandArray);
    }

    public DBParser() {
    }

    //Method to parse commands
    public void parseCMD(String filePath, String[] commandArray) {

        setIndex(0);

        //Check a command exists
        if (checkCommand(commandArray, "CREATE")) {

            //Increment counter
            setIndex(getIndex() + 1);

            if (checkCommand(commandArray, "DATABASE")) {

                ParseCreateDB PcDB = new ParseCreateDB(commandArray);
                return;

            } else if (checkCommand(commandArray, "TABLE")) {

                ParseCreateTB PcTB = new ParseCreateTB(commandArray);
                return;

            } else {
                setParse(false);
                return;
            }
        } else if (checkCommand(commandArray, "USE")) {

            ParseUse Pu = new ParseUse(commandArray);
            return;

        } else if (checkCommand(commandArray, "INSERT")) {

            ParseInsert PcDB = new ParseInsert(commandArray);
            return;
        }
        else if (checkCommand(commandArray, "DROP")) {

            //Increment counter
            setIndex(getIndex() + 1);

            if (checkCommand(commandArray, "DATABASE")) {

                ParseDropDB PcDB = new ParseDropDB(commandArray);
                return;

            } else if (checkCommand(commandArray, "TABLE")) {

                ParseDropTB PcTB = new ParseDropTB(commandArray);
                return;

            }
        }
    }

    public void setParse(boolean tf){
        parsed = tf;
    }

    public boolean getParse(){
        return parsed;
    }

    public void setIndex(int i){
        index = i;
    }

    public int getIndex(){

        return index;
    }

    //Method to evaluate each command with: (NO INCREMENT)
    public boolean checkCommand(String [] commandArray, String command){

        //If there are more commands then continue
        if(getIndex() < commandArray.length) {

            if (commandArray[getIndex()].equals(command)) {

                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    //Method to evaluate each command with: (INCREMENT)
    public boolean checkCommandWithIncrement(String [] commandArray, String command){

        //Increment counter
        setIndex(getIndex() + 1);

        //If there are more commands then continue
        if(getIndex() < commandArray.length) {

            if (commandArray[getIndex()].equals(command)) {

                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    //Method to evaluate alphanumeric (INCREMENT)
    public boolean checkAlphaNumeric(String [] commandArray, String regexText){

        //Increment counter
        setIndex(getIndex() + 1);

        //If there are more commands then continue
        if(getIndex() < commandArray.length) {

            if (commandArray[getIndex()].matches(regexText)) {

                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    //Method to check semi-colon (INCREMENT)
    public boolean checkSemiColonandFollowing(String[] commandArray){


        //Increment counter
        setIndex(getIndex() + 1);

        if(checkCommand(commandArray, ";")){

            //Increment counter
            setIndex(getIndex() + 1);

            //Check if more commands thereafter
            if(getIndex() == commandArray.length) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
}
