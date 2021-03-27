import java.io.*;
import DBCommands.DBPath;
import DBCommands.DBcmd;
import DBParse.DBParser;
import DBExceptions.*;


public class DBController extends DBPath {

    public DBController(String incomingCommand, BufferedWriter socketWriter) throws SyntaxException{

        DBTokenizer newToken = new DBTokenizer(incomingCommand);

        //Parse the incoming command
        DBParser newParse = new DBParser(path, newToken.getToken(), socketWriter);

        //If ok then interpret the incoming command
        if(newParse.getParse() == true){
            DBcmd newCommand = new DBcmd(path, newToken.getToken(), socketWriter);
        }
        else{
            SyntaxException e = new SyntaxException();
            throw e;
    }
    }
}
