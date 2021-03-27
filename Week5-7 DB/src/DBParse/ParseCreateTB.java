package DBParse;

public class ParseCreateTB extends DBParser{

    public ParseCreateTB(String[] commandArray){
        handleSyntax(commandArray);
    }

    private void handleSyntax(String[] commandArray) {

        setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+"));
        if (getParse()) {

            //Check for semi-colon
            setParse(checkSemiColonandFollowing(commandArray));

            //If none continue checks
            if (!getParse()) {

                //No increment as semi-colon check did this increment first
                setParse(checkCommand(commandArray, "("));

                if (getParse()) {

                    //Check attribute list
                    setParse(parseAttributeList(commandArray));

                    if (getParse()) {

                        //Check final bracket
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
}
