package DBParse;

//Class to parse CREATE DATABASE
public class ParseCreateDB extends DBParser{

    public ParseCreateDB(String[] commandArray){
        handleSyntax(commandArray);
    }

    private void handleSyntax(String[] commandArray){

        setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));
        if(getParse()){
            setParse(checkSemiColonandFollowing(commandArray, true));
        }
    }
}
