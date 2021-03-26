package DBParse;

import DBCommands.CreateDB;
import DBExceptions.SyntaxException;
import java.io.BufferedWriter;
import java.io.IOException;

public class DBParser {

    public static boolean parsed;

    public DBParser(String filePath, String[] commandArray, BufferedWriter socketWriter){
        parseCMD(filePath, commandArray);
    }

    public DBParser() {
    }

    //Method to parse commands
    public void parseCMD(String filePath, String[] commandArray) {

        int i = 0;

        //Check a command exists
        if(i < commandArray.length){

            if(commandArray[i].equals("CREATE")) {

                //Increment counter
                i++;

                if (i < commandArray.length){

                    if(commandArray[i].equals("DATABASE")){

                        ParseCreateDB PcDB = new ParseCreateDB(i, commandArray);
                        return;
                    }
                    else if (commandArray[i].equals("TABLE")) {

                    }
                    setParse(false);
                    return;
                }
                else{
                    setParse(false);
                    return;
                }
            } else if (commandArray[i].equals("USE")) {


                ParseUse Pu = new ParseUse(i, commandArray);
                return;
            }

                /*USE
                case "USE":


                    break;

                //INSERT Class
                case "INSERT":

                    if (commandArray[i].equals("INTO")) {


                    }
                    break;

                //SELECT Class
                case "SELECT":


                    break;

                //DROP Class
                case "DROP":

                    if (commandArray[i].equals("TABLE")) {


                    }
                    if (commandArray[i].equals("DATABASE")) {


                    }
                    break;

                default:
                    return false;

            }*/
        }
        /*else {*/
        setParse(false);
        return;
        /*}*/
    }

    public void setParse(boolean tf){
        parsed = tf;
    }

    public boolean getParse(){
        return parsed;
    }
}
