package DBParse;

public class ParseCreateTB extends DBParser{

    public ParseCreateTB(String[] commandArray){
        handleSyntax(commandArray);
    }

    private void handleSyntax(String[] commandArray) {

        setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));
        if (getParse()) {

            //Check for semi-colon
            setParse(checkSemiColonandFollowing(commandArray, true));

            //If none continue checks
            if (!getParse()) {

                //No increment as semi-colon check did this increment first
                setParse(checkCommand(commandArray, "(", false));

                if (getParse()) {

                    //Check attribute list
                    setParse(parseAttributeList(commandArray));

                    if (getParse()) {

                        //Check final bracket
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
