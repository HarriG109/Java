package DBParse;

public class ParseJoin extends DBParser{

    public ParseJoin(String[] commandArray){
        handleSyntax(commandArray);
    }

    //Method to handle syntax
    private void handleSyntax(String[] commandArray) {

        //Check for acceptable table name
        setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));

        if (getParse()) {

            //Check for AND
            setParse(checkCommand(commandArray, "AND", true));

            if (getParse()) {

                //Check for acceptable table name
                setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));

                if (getParse()) {

                    //Check for ON
                    setParse(checkCommand(commandArray, "ON", true));

                    if (getParse()) {

                        //Check for acceptable column name
                        setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));

                        if (getParse()) {

                            //Check for AND
                            setParse(checkCommand(commandArray, "AND", true));

                            if (getParse()) {

                                //Check for acceptable column name
                                setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));

                                if(getParse()){

                                    //Check for semi-colon and following commands
                                    setParse(checkSemiColonandFollowing(commandArray, true));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
