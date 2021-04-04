package DBParse;

import DBCommands.CreateDB;
import DBExceptions.SyntaxException;
import java.io.BufferedWriter;
import java.io.IOException;

public class DBParser {

    public static boolean parsed;
    public static int index;

    public DBParser(String filePath, String[] commandArray){
        parseCMD(commandArray);
    }

    public DBParser() {
    }

    //Method to parse commands
    public void parseCMD(String[] commandArray) {

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
        else if (checkCommand(commandArray, "UPDATE", false)) {

            ParseUpdate Pu = new ParseUpdate(commandArray);
            return;
        }
        else if (checkCommand(commandArray, "DELETE", false)) {

            ParseDelete Pd = new ParseDelete(commandArray);
            return;
        }
        else if (checkCommand(commandArray, "ALTER", false)) {

            ParseAlter Pa = new ParseAlter(commandArray);
            return;
        }
        else if (checkCommand(commandArray, "JOIN", false)) {

            ParseJoin Pj = new ParseJoin(commandArray);
            return;
        }
        setParse(false);
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

    //Method to parse attribute list (INCREMENT SWITCH)
    public boolean parseAttributeList(String[] commandArray, boolean increment){

        if(increment == true) {
            //Increment counter
            setIndex(getIndex() + 1);
        }

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
    public boolean checkValue(String[] commandArray, boolean increment) {

        String noCommaStr;

        if(increment == true) {
            //Increment counter
            setIndex(getIndex() + 1);
        }

        //Remove the comma
        noCommaStr = commandArray[getIndex()].replace(",", "");

        //If there are more commands then continue
        if (getIndex() < commandArray.length) {

            if (noCommaStr.charAt(0) == '\'') {

                if (noCommaStr.charAt(noCommaStr.length() - 1) == '\'') {
                    return true;
                }
                return false;
            }
            //Handle booleans
            else if (noCommaStr.equals("true") || noCommaStr.equals("false")) {

                return true;
            }
            //Handle numerics
            else if (noCommaStr.matches("^-?\\d*\\.{0,1}\\d+$")) {
                return true;
            }
        }
        return false;

    }

    //Method to recursively handle conditions (NOTE: Not perfect but it works for most!)
    public boolean checkConditions(String[] commandArray){

        int bracketCount = 0;

        //Check semi-colon
        if(checkSemiColonandFollowing(commandArray, true)){
            if(bracketCount == 0) {
                return true;
            }
            return false;
        }
        else if(checkCommand(commandArray, "(", false)) {
            bracketCount++;
            if(condition(commandArray, true)){
                return checkConditions(commandArray);
            }
        }
        else if(checkCommand(commandArray, ")", false)){
            bracketCount--;
            if(checkCommand(commandArray, "AND", true)){
                if(checkCommand(commandArray, "(", true)){
                    bracketCount++;
                    return checkConditions(commandArray);
                }
            }
            else if(checkCommand(commandArray, "OR", false)){
                if(checkCommand(commandArray, "(", true)){
                    bracketCount++;
                    return checkConditions(commandArray);
                }
            }
            else if(checkCommand(commandArray, ")", false)){
                bracketCount--;
                return checkConditions(commandArray);
            }
            else if(checkSemiColonandFollowing(commandArray, false)){
                return true;
            }
            return false;
        }
        else{
            if(condition(commandArray, false)){
                return checkConditions(commandArray);
            }
        }
        return false;
    }

    //Method to evaluate a condition
    public boolean condition(String[] commandArray, boolean increment){

        if(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", increment)){
            if(checkOperator(commandArray, true)){
                if(checkValue(commandArray, true)){
                    return true;
                }
            }
        }
        return false;
    }
}
