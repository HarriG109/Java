package DBParse;

public class ParseInsert extends DBParser{
    public ParseInsert(String[] commandArray) {
        handleSyntax(commandArray);
    }

    private void handleSyntax(String[] commandArray){

        //Check for alphaNumeric spelt table
        setParse(checkCommandWithIncrement(commandArray, "INTO"));

        if (getParse()) {

            //Check for INTO
            setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+"));

            if (getParse()) {

                //Check for VALUES
                setParse(checkCommandWithIncrement(commandArray, "VALUES"));

                if (getParse()) {

                    //Check for open bracket
                    setParse(checkCommandWithIncrement(commandArray, "("));

                    if (getParse()) {

                        //Parse the valuelist
                        setParse(parseValueList(commandArray));

                        if (getParse()) {

                            //Check for open bracket
                            setParse(checkCommandWithIncrement(commandArray, ")"));

                            if (getParse()) {

                                //Check semi-colon
                                setParse(checkSemiColonandFollowing(commandArray));
                            }
                        }
                    }
                }
            }
        }
    }

    //Method to parse attribute list
    public boolean parseValueList(String[] commandArray) {

        String nocommaStr;

        //Increment counter
        setIndex(getIndex() + 1);

        //Check all comma variables
        while (getIndex() < commandArray.length && commandArray[getIndex()].charAt(commandArray[getIndex()].length() - 1) == ',') {

            nocommaStr = commandArray[getIndex()].substring(0, commandArray[getIndex()].length() - 1);

            //Handle string literals
            if (nocommaStr.substring(0, 1).equals("'")) {
                if (!nocommaStr.substring(nocommaStr.length() - 1).equals("'")) {
                    return false;
                } else {
                    setIndex(getIndex() + 1);
                }
            }
            //Handle booleans
            else if (nocommaStr.equals("true") || nocommaStr.equals("false")) {
                setIndex(getIndex() + 1);
            }
            //Handle numerics
            else if (nocommaStr.matches("\\d+?\\.?\\d*")) {
                setIndex(getIndex() + 1);
            } else {
                return false;
            }
        }

        //Check once more for final word (which has no comma after)
        //Handle string literals
        System.out.println(commandArray[getIndex()].substring(0, 1));
        if (commandArray[getIndex()].substring(0, 1).equals("'")) {

            if (!commandArray[getIndex()].substring(commandArray[getIndex()].length() - 1).equals("'")) {
                return false;
            } else {
                return true;
            }
        }
        //Handle booleans
        else if (commandArray[getIndex()].equals("true") || commandArray[getIndex()].equals("false")) {

            return true;
        }
        //Handle numerics
        else if (commandArray[getIndex()].matches("\\d+?\\.?\\d*")) {
            return true;
        }
        return false;
    }
}
