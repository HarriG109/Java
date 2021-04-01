import java.io.*;
import DBCommands.DBPath;
import DBCommands.DBcmd;
import DBParse.DBParser;
import DBExceptions.*;

public class DBController extends DBPath {

    public String consoleText;

    public DBController(String incomingCommand, BufferedWriter socketWriter) throws SyntaxException{

        DBTokenizer newToken = new DBTokenizer(incomingCommand);

        //Parse the incoming command
        DBParser newParse = new DBParser(path, newToken.getToken());

        //If ok then interpret the incoming command
        if(newParse.getParse() == true){
            DBcmd newCommand = new DBcmd(path, newToken.getToken(), socketWriter);

            if(newCommand.returnText != null){
                setConsoleText("[OK]" + "\n" + newCommand.returnText);
            }
            else{
                setConsoleText("[OK]");
            }
        }
        else{
            SyntaxException e = new SyntaxException();
            throw e;
        }
    }

    //Method to populate console string
    public void setConsoleText(String text){
        consoleText = text;
    }

    //Method to return console string
    public String getConsoleText(){
        return consoleText;
    }
}
