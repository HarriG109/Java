import DBExceptions.SyntaxException;
import java.io.IOException;
import java.io.BufferedWriter;

public class DBcmd{

    public int index = 0;
    boolean use;

    public DBcmd(String filePath, String[] tokenArray, BufferedWriter socketWriter) throws SyntaxException {
        processCMD(filePath, tokenArray, socketWriter);
    }

    public void processCMD(String filePath, String[] commandArray, BufferedWriter socketWriter) throws SyntaxException {

        if(commandArray[0].equals("CREATE")){

            //CREATE DATABASE Class
            if(commandArray[1].equals("DATABASE")){

                //Need to check a 2nd argument or 4th and throw error

                //Instantiate object
                CreateDB newDB = new CreateDB(filePath, commandArray[2], socketWriter);
            }

            //CREATE TABLE Class
            else if(commandArray[1].equals("TABLE")){

                //Instantiate object
                CreateTB newTB = new CreateTB(filePath, commandArray, socketWriter, index);
            }
        }

        //USE Class
        else if(commandArray[0].equals("USE")) {

            //Instantiate object
            UseDB useDB = new UseDB(filePath, commandArray[1], socketWriter);
        }

        //INSERT Class
        else if(commandArray[index]).equals("INSERT"){

            //Instantiate object
            InsertRow newRow = new InsertRow(filePath, commandArray, socketWriter);

        }*/
    }
}
