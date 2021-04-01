package DBParse;

public class ParseSelect extends DBParser{

    public ParseSelect(String[] commandArray){
        handleSyntax(commandArray);
    }

    private void handleSyntax(String[] commandArray){

        //Check for '*' modifier
        setParse(checkCommand(commandArray, "*", true));

        //If not '*' check attributes
        if (!getParse()) {
            //Check attribute list
            setParse(parseAttributeList(commandArray, false));
        }

        //If true then continue
        if (getParse()) {

            //Check for FROM
            setParse(checkCommand(commandArray, "FROM", true));

            if (getParse()) {

                //Check for table name
                setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));

                if (getParse()) {

                    //Check semi-colon
                    setParse(checkSemiColonandFollowing(commandArray, true));

                    //If no semi-colon check where clause
                    if (!getParse()) {

                        //Check WHERE without increment as semi-colon has incremented
                        setParse(checkCommand(commandArray, "WHERE", false));

                        if (getParse()) {

                            //Check conditions (NOTE: No need to check semi-colon as method does this!)
                            setParse(checkConditions(commandArray));
                        }
                    }
                }
            }
        }
    }
}


