import java.io.*;
import java.util.*;
import DBExceptions.*;

public class DBController extends DBPath{

    public DBController(String incomingCommand, BufferedWriter socketWriter) throws SyntaxException{

        DBTokenizer newToken = new DBTokenizer(incomingCommand);

        //Parse the incoming command
        //DBParser

        //If ok then interpret the incoming command
        DBcmd newCommand = new DBcmd(path, newToken.getToken(), socketWriter);

    }
}
