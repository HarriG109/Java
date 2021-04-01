package DBParse;

public class ParseDropTB extends DBParser {

    public ParseDropTB(String[] commandArray){

        setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));
        if(getParse()){

            //Check for semi-colon and following commands
            setParse(checkSemiColonandFollowing(commandArray, true));
        }
    }
}
