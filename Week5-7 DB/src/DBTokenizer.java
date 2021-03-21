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

        command = editOpenBracket(command);
        command = editCloseBracket(command);
        command = editSemiColon(command);

        tokenArray = command.split(" ");
        /*\\s|\W to keep only words*/
    }

    //Method to edit start bracket if it exists
    public String editOpenBracket(String command){
        if(command.indexOf("(") != -1){
            return command.substring(0, command.indexOf("(") + 1) + " " + command.substring(command.indexOf("(") + 1);
        }
        else{
            return command;
        }
    }

    //Method to edit ending bracket if it exists
    public String editCloseBracket(String command){
        if(command.indexOf(")") != -1){
            return command.substring(0, command.indexOf(")")) + " " + command.substring(command.indexOf(")"));
        }
        else{
            return command;
        }
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

}
