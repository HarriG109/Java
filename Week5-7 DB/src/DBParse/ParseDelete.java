package DBParse;

public class ParseDelete extends DBParser {

    public ParseDelete(String[] commandArray){
        handleSyntax(commandArray);
    }

    private void handleSyntax(String[] commandArray) {
        //Check for FROM
        setParse(checkCommand(commandArray, "FROM", true));
        if (getParse()) {

            //Check for acceptable table name
            setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));

            if (getParse()) {

                //Check for WHERE
                setParse(checkCommand(commandArray, "WHERE", true));

                if (getParse()) {

                    //Check conditions (NOTE: No need to check semi-colon as method does this!)
                    setParse(checkConditions(commandArray));
                }
            }
        }
    }
}
