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
        if (checkCommand(commandArray, "CREATE", false)) {

            //Increment counter
            setIndex(getIndex() + 1);

            if (checkCommand(commandArray, "DATABASE", false)) {

                ParseCreateDB PcDB = new ParseCreateDB(commandArray);
                return;

            } else if (checkCommand(commandArray, "TABLE", false)) {

                ParseCreateTB PcTB = new ParseCreateTB(commandArray);
                return;

            } else {
                setParse(false);
                return;
            }
        } else if (checkCommand(commandArray, "USE", false)) {

            ParseUse Pu = new ParseUse(commandArray);
            return;

        } else if (checkCommand(commandArray, "INSERT", false)) {

            ParseInsert PcDB = new ParseInsert(commandArray);
            return;
        }
        else if (checkCommand(commandArray, "DROP", false)) {

            //Increment counter
            setIndex(getIndex() + 1);

            if (checkCommand(commandArray, "DATABASE", false)) {

                ParseDropDB PcDB = new ParseDropDB(commandArray);
                return;

            } else if (checkCommand(commandArray, "TABLE", false)) {

                ParseDropTB PcTB = new ParseDropTB(commandArray);
                return;

            }
        }
        else if (checkCommand(commandArray, "SELECT", false)) {
            ParseSelect Ps = new ParseSelect(commandArray);
            return;
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

    //Method to evaluate each command with: (INCREMENT Switch)
    public boolean checkCommand(String [] commandArray, String command, boolean increment){

        if(increment == true) {
            //Increment counter
            setIndex(getIndex() + 1);
        }

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

    //Method to evaluate alphanumeric (INCREMENT Switch)
    public boolean checkAlphaNumeric(String [] commandArray, String regexText, boolean increment){

        if(increment == true) {
            //Increment counter
            setIndex(getIndex() + 1);
        }

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

    //Method to check semi-colon (INCREMENT Switch)
    public boolean checkSemiColonandFollowing(String[] commandArray, boolean increment){

        if(increment == true) {
            //Increment counter
            setIndex(getIndex() + 1);
        }

        if(checkCommand(commandArray, ";", false)){

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

    //Method to parse attribute list (INCREMENT)
    public boolean parseAttributeList(String[] commandArray){

        //Increment counter
        setIndex(getIndex() + 1);

        String nocommaStr;

        //Loop through variables with commas
        while(getIndex() < commandArray.length && commandArray[getIndex()].charAt(commandArray[getIndex()].length() -1) == ','){

            nocommaStr = commandArray[getIndex()].substring(0, commandArray[getIndex()].length() -1);
            if(nocommaStr.matches("[a-zA-Z0-9]+")){
                setIndex(getIndex() + 1);
            }
            else{
                return false;
            }
        }
        //Check once more for final word (which has no comma after)
        if(commandArray[getIndex()].matches("[a-zA-Z0-9]+")){
            return true;
        }
        else{
            return false;
        }
    }

    //Method to evaluate operator (INCREMENT Switch)
    public boolean checkOperator(String [] commandArray, boolean increment){

        if(increment == true) {
            //Increment counter
            setIndex(getIndex() + 1);
        }

        //If there are more commands then continue
        if(getIndex() < commandArray.length) {

            if (commandArray[getIndex()].equals("==") || commandArray[getIndex()].equals("!=") ||
                commandArray[getIndex()].equals(">=") || commandArray[getIndex()].equals("<=") ||
                commandArray[getIndex()].equals(">") || commandArray[getIndex()].equals("<") ||
                commandArray[getIndex()].equals("LIKE")){

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

    //Method to check value references (INCREMENT Switch)
    public boolean checkValue(String[] commandArray, String command, boolean increment) {

        if(increment == true) {
            //Increment counter
            setIndex(getIndex() + 1);
        }

        //If there are more commands then continue
        if (getIndex() < commandArray.length) {

            if (command.charAt(0) == '\'') {

                if (!command.substring(command.length() - 1).equals("'")) {
                    return false;
                } else {
                    return true;
                }
            }
            //Handle booleans
            else if (command.equals("true") || command.equals("false")) {

                return true;
            }
            //Handle numerics
            else if (command.matches("\\d+?\\.?\\d*")) {
                return true;
            }
            return false;
        }
        return false;

    }
}
