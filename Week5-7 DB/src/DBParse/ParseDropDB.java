package DBParse;

public class ParseDropDB extends DBParser {

    public ParseDropDB(String[] commandArray){

        setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));
        if(getParse()){
            setParse(checkSemiColonandFollowing(commandArray, true));
        }
    }
}
