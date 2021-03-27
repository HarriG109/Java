package DBParse;

public class ParseUse extends DBParser {

    public ParseUse(String[] commandArray){
        handleSyntax(commandArray);
    }

    private void handleSyntax(String[] commandArray){

        setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+"));
        if(getParse()){
            setParse(checkSemiColonandFollowing(commandArray));
        }
    }
}
