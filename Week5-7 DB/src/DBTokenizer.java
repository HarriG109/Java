import java.util.*;

public class DBTokenizer {

    public String[] tokenArray;

    public DBTokenizer(String incomingCommand) {

        setToken(incomingCommand);
    }

    //Return the converted token string
    public String[] getToken(){
        return tokenArray;
    }

    //Convert command string to array for better handling
    public void setToken(String command){

        int i;

        //Check for apostrophes
        if(apostropheCount(command)%2 == 0){
            //If apostrophes convert string literal spaces to dummy char before split
            command = alterStringLiteralSpaces(command);
        }

        //Split out the open bracket as it's own element
        command = editOpenBracket(command);
        //Split out the end bracket as it's own element
        command = editCloseBracket(command);
        //Split out the semi-token as it's own element
        command = editSemiColon(command);

        //Split out all operators in case of missing spacing;
        command = editLongOperator(command, "==");
        command = editLongOperator(command, ">=");
        command = editLongOperator(command, "<=");
        command = editLongOperator(command, "!=");
        command = editShortOperator(command, "=");
        command = editShortOperator(command, ">");
        command = editShortOperator(command, "<");

        //trim leading and trailing spaces
        command = command.trim();

        //Split into array
        tokenArray = command.split("\\s+");

        //Convert string literal dummy chars back to spaces
        revertStringLiteral(tokenArray);
    }

    //Method to replace string literal spaces with tabs so the token is not split up
    public String alterStringLiteralSpaces(String command){
        int i=0, index;
        String test;
        ArrayList<Integer> aposLoc = new ArrayList<Integer>();

        index = command.indexOf("'");

        while(index != -1) {

            //Create an arraylist of index's of every apostrophe occurence
            aposLoc.add(index);
            index = command.indexOf("'", index + 1);
        }
        //Loop through each pair of apostrophe and replace the space so the string literal is not split up
        while(i < aposLoc.size()){
            //If even then handle the pair of apostrophe's
            if(i%2 == 0){
                //
                test = command.substring(aposLoc.get(i), aposLoc.get(i+1) + 1);
                //Replace spaces with tabs
                test = test.replaceAll("\\s+", "~");
                //Add back into string
                command = command.substring(0, aposLoc.get(i)) + test + command.substring(aposLoc.get(i+1) + 1);

            }
            //Increment by two for the pairs of apostrophe's
            i = i+2;
        }
        return command;
    }

    //Method to loop through tokens replacing tabs with spaces
    public void revertStringLiteral(String[] tokenArray){
        int i;

        for(i = 0; i < tokenArray.length; i++) {
            tokenArray[i] = tokenArray[i].replace("~", " ");
        }
    }

    //Method to edit start bracket if it exists
    public String editOpenBracket(String command){

        int index;
        index = command.indexOf("(");

        while(index != -1){
            command = command.substring(0, index + 1) + " " + command.substring(index + 1);

            //Move past open bracket
            index = command.indexOf("(", index + 1);

        }
        return command;
    }

    //Method to edit ending bracket if it exists
    public String editCloseBracket(String command){

        int i, index;
        index = command.indexOf(")");

        while(index != -1){

            command = command.substring(0, index) + " " + command.substring(index);

            //Plus two to include new space
            index = command.indexOf(")", index + 2);

        }
        return command;
    }

    //Method to edit semi-colon
    public String editSemiColon(String command){
        if(command.indexOf(";") != -1){
            return command.substring(0, command.indexOf(";")) + " " + command.substring(command.indexOf(";"));
        }
        else{
            return command;
        }
    }

    //Method to check for long yoperators and space them out as tokens
    public String editLongOperator(String command, String operator){

        int index;
        index = command.indexOf(operator);

        while(index != -1) {
               command = command.substring(0, index) + " " + operator + " " + command.substring(index + operator.length());

               //Plus two to include new space
               index = command.indexOf(operator, index + 2);
        }
        return command;
    }

    //Method to check for short operators and space them out as tokens
    public String editShortOperator(String command, String operator){

        int index;
        index = command.indexOf(operator);

        while(index != -1){
                 //Make sure it is truly a single operator before editing
                if(command.charAt(index + 1) != '=' && command.charAt(index - 1) != '>'
                        && command.charAt(index - 1) != '<' && command.charAt(index - 1) != '!'
                        && command.charAt(index - 1) != '=') {
                    command = command.substring(0, index) + " " + operator + " " + command.substring(index + operator.length());
                }

                //Plus two to include new space
                index = command.indexOf(operator, index + 2);
        }
        return command;
    }

    //Method to count instances of apostrophes
    public int apostropheCount(String command){

        int index, count = 0;
        index = command.indexOf("'");

        while(index != -1){

            count++;
            index = command.indexOf("'", index + 1);
        }
        return count;
    }
}
