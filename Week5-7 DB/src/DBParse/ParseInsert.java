package DBParse;

public class ParseInsert extends DBParser{
    public ParseInsert(String[] commandArray) {
        handleSyntax(commandArray);
    }

    private void handleSyntax(String[] commandArray){

        //Check for alphaNumeric spelt table
        setParse(checkCommand(commandArray, "INTO", true));

        if (getParse()) {

            //Check for table name
            setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));

            if (getParse()) {

                //Check for VALUES
                setParse(checkCommand(commandArray, "VALUES", true));

                if (getParse()) {

                    //Check for open bracket
                    setParse(checkCommand(commandArray, "(", true));

                    if (getParse()) {

                        //Parse the valuelist
                        setParse(parseValueList(commandArray));

                        if (getParse()) {

                            //Check for open bracket
                            setParse(checkCommand(commandArray, ")", true));

                            if (getParse()) {

                                //Check semi-colon
                                setParse(checkSemiColonandFollowing(commandArray, true));
                            }
                        }
                    }
                }
            }
        }
    }

    //Method to parse attribute list
    public boolean parseValueList(String[] commandArray) {

        //Increment counter
        setIndex(getIndex() + 1);

        //Check all comma variables
        while (getIndex() < commandArray.length && commandArray[getIndex()].charAt(commandArray[getIndex()].length() - 1) == ',') {

            //Handle values
            if (checkValue(commandArray, false)) {
                setIndex(getIndex() + 1);
            }
            else {
                return false;
            }
        }

        //Check once more for final word (which has no comma after)
        if(getIndex() < commandArray.length) {

            if (checkValue(commandArray, false)) {
                return true;
            }
        }
        return false;
    }
}
