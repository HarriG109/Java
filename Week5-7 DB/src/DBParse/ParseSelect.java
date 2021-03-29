package DBParse;

public class ParseSelect extends DBParser{

    public ParseSelect(String[] commandArray){
        handleSyntax(commandArray);
    }

    private void handleSyntax(String[] commandArray){

        System.out.println("Here 1");
        //Check for '*' modifier
        setParse(checkCommand(commandArray, "*", true));

        //If not '*' check attributes
        if (!getParse()) {
            System.out.println("Here 2");
            //Check attribute list
            setParse(parseAttributeList(commandArray));
        }

        //If true then continue
        if (getParse()) {

            System.out.println("Here 3");
            //Check for FROM
            setParse(checkCommand(commandArray, "FROM", true));

            if (getParse()) {

                System.out.println("Here 4");
                //Check for table name
                setParse(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true));

                if (getParse()) {

                    System.out.println("Here 5");
                    //Check semi-colon
                    setParse(checkSemiColonandFollowing(commandArray, true));

                    //If no semi-colon check where clause
                    if (!getParse()) {

                        System.out.println("Here 6");
                        //Check WHERE without increment as semi-colon has incremented
                        setParse(checkCommand(commandArray, "WHERE", false));

                        if (getParse()) {

                            System.out.println("Here 7");
                            //Check conditions
                            System.out.println(commandArray);
                            setParse(checkConditions(commandArray));
                            System.out.println(commandArray);
                        }
                    }
                }
            }
        }
    }

    public boolean checkConditions(String[] commandArray){

        //Check semi-colon
        if(checkSemiColonandFollowing(commandArray, false)){
            System.out.println("Here 21");
            return true;
        }
        else if(checkCommand(commandArray, "(", false)) {
            System.out.println("Here 22");
            checkConditions(commandArray);
        }
        else if(checkCommand(commandArray, ")", false)){
            System.out.println("Here 23");
            if(checkCommand(commandArray, "AND", true)){
                System.out.println("Here 24");
                if(checkCommand(commandArray, "(", true)){
                    System.out.println("Here 25");
                    checkConditions(commandArray);
                }
            }
            else if(checkCommand(commandArray, "OR", true)){
                System.out.println("Here 27");
                if(checkCommand(commandArray, "(", true)){
                    checkConditions(commandArray);
                }
            }
            System.out.println("Here 26");
            checkConditions(commandArray);
        }
        else{
            if(condition(commandArray)){
                System.out.println("Here 28");
                checkConditions(commandArray);
            }
        }
        System.out.println(commandArray);
        System.out.println("Here 29");
        return false;
    }

    public boolean condition(String[] commandArray){

        if(checkAlphaNumeric(commandArray, "[a-zA-Z0-9]+", true)){
            if(checkOperator(commandArray, true)){
                if(checkValue(commandArray, commandArray[getIndex()], true)){
                    return true;
                }
            }
        }
        return false;
    }
}


