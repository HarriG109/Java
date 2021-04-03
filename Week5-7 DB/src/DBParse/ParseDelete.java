package DBParse;

public class ParseDelete extends DBParser {

    public ParseDelete(String[] commandArray){
        handleSyntax(commandArray);
    }

    //Method to handle syntax
    private void handleSyntax(String[] commandArray) {
        //Check for FROM
        setParse(checkCommand(commandArray, "FROM", true));
        System.out.println("Here 1");
        if (getParse()) {

            //Check for acceptable table name
            setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));
            System.out.println("Here 2");

            if (getParse()) {

                //Check for WHERE
                setParse(checkCommand(commandArray, "WHERE", true));
                System.out.println("Here 3");

                if (getParse()) {

                    //Check conditions (NOTE: No need to check semi-colon as method does this!)
                    setParse(checkConditions(commandArray));
                    System.out.println("Here 4");
                }
            }
        }
    }
}
