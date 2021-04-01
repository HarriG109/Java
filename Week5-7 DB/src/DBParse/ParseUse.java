package DBParse;

public class ParseUse extends DBParser {

    public ParseUse(String[] commandArray){
        handleSyntax(commandArray);
    }

    private void handleSyntax(String[] commandArray){

        setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));
        if (getParse()) {

            //Check for semi-colon and following commands
            setParse(checkSemiColonandFollowing(commandArray, true));
        }
    }
}
