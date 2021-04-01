package DBParse;

public class ParseAlter extends DBParser{

    public ParseAlter(String[] commandArray){
        handleSyntax(commandArray);
    }

    //Method to handle syntax
    private void handleSyntax(String[] commandArray) {

        //Check for TABLE
        setParse(checkCommand(commandArray, "TABLE", true));

        if (getParse()) {

            //Check for acceptable table name
            setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));

            if (getParse()) {

                //Check for alteration type
                setParse(alterationType(commandArray));

                if (getParse()) {

                    //Check for acceptable column name
                    setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));

                    if (getParse()) {

                        //Check for semi-colon and following commands
                        setParse(checkSemiColonandFollowing(commandArray, true));
                    }
                }
            }
        }
    }

    //Method to check alteration type
    public boolean alterationType(String[] commandArray){

        //Increment counter
        setIndex(getIndex() + 1);

        if(checkCommand(commandArray, "ADD", false)){
            return true;
        }
        else if(checkCommand(commandArray, "DROP", false)){
            return true;
        }
        return false;
    }
}
