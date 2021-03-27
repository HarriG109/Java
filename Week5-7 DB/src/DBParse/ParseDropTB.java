package DBParse;

public class ParseDropTB extends DBParser {

    public ParseDropTB(String[] commandArray){

        setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+"));
        if(getParse()){
            setParse(checkSemiColonandFollowing(commandArray));
        }
    }
}
