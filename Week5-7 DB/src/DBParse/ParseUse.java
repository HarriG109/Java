package DBParse;

public class ParseUse extends DBParser {

    public ParseUse(int index, String[] commandArray){
        handleSyntax(index, commandArray);
    }

    private void handleSyntax(int index, String[] commandArray){

        //Increment counter
        index++;

        if (index < commandArray.length){

            if(commandArray[index].matches("[a-zA-Z0-9]+")) {

                index++;

                if (index < commandArray.length) {
                    if (commandArray[index].equals(";")) {
                        setParse(true);
                    } else {
                        setParse(false);
                    }
                }
                else{
                    setParse(false);
                }
            }
            else{
                setParse(false);
            }
        }
        else{
            setParse(false);
        }
    }
}
