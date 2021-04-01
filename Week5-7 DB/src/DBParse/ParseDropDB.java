package DBParse;

public class ParseDropDB extends DBParser {

    public ParseDropDB(String[] commandArray){

        setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));
        if(getParse()){

            //Check for semi-colon and following commands
            setParse(checkSemiColonandFollowing(commandArray, true));
        }
    }
}
