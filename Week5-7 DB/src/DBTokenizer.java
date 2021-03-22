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

        int i, j;

        command = editOpenBracket(command);

        for(i = 0; i < command.length(); i++) {
            System.out.println(command);
        }

        command = editCloseBracket(command);

        for(j = 0; j < command.length(); j++) {
            System.out.println(command);
        }

        command = editSemiColon(command);

        tokenArray = command.split(" ");
        /*\\s|\W to keep only words*/
    }

    //Method to edit start bracket if it exists
    public String editOpenBracket(String command){

        int i, index;
        index = command.indexOf("(");

        if(index != -1){
            for(i = 0; i < (stringSpecicalCount(command, '(')); i++){
                command = command.substring(0, index + 1) + " " + command.substring(index + 1);
                index = command.indexOf("(", index + 1);
            }
        }
        return command;
    }

    //Method to edit ending bracket if it exists
    public String editCloseBracket(String command){

        int i, index;
        index = command.indexOf(")");

        if(index != -1){
            for(i = 0; i < (stringSpecicalCount(command, ')')); i++){
                command = command.substring(0, index) + " " + command.substring(index);

                //Plus two to include new space
                index = command.indexOf(")", index + 2);
            }
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

    //Method to count how many signs match parameter in string
    public int stringSpecicalCount(String command, char symbol){

        int j, count = 0;

        for(j = 0; j < command.length(); j++){
            if(command.charAt(j) == symbol){
                count++;
            }
        }
        return count;
    }
}
