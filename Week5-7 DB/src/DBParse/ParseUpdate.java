package DBParse;

public class ParseUpdate extends DBParser {

    public ParseUpdate(String[] commandArray){
        handleSyntax(commandArray);
    }

    //Method to handle syntax
    private void handleSyntax(String[] commandArray) {

        //Check for acceptable table name
        setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));

        if (getParse()) {

            //Check for SET
            setParse(checkCommand(commandArray, "SET", true));

            if (getParse()) {

                //Check name value pair list
                setParse(nameValuePairList(commandArray));

                if (getParse()) {

                    //Check WHERE without increment as semi-colon has incremented
                    setParse(checkCommand(commandArray, "WHERE", true));

                    if (getParse()) {

                        //Check conditions (NOTE: No need to check semi-colon as method does this!)
                        setParse(checkConditions(commandArray));
                    }
                }
            }
        }
    }

    //Method to evaluate name value pair list
    public boolean nameValuePairList(String[] commandArray){

        boolean goAgain = true;

        while(goAgain && getParse()) {

            if (getParse()) {

                //Check for column name
                setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));

                if (getParse()) {

                    //Check for =
                    setParse(checkCommand(commandArray, "=", true));

                    if (getParse()) {

                        //Check for value
                        setParse(checkValue(commandArray, true));

                        //If ending in a comma then go again
                        if (commandArray[getIndex()].charAt(commandArray[getIndex()].length() - 1) != ',') {
                            goAgain = false;
                        }
                    }
                }
            }
        }

        //Return if no more commands and true
        if (getParse()) {
            return true;
        }
        return false;
    }
}
