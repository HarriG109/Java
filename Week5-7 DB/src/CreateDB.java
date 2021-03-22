import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class CreateDB {

    public CreateDB(String filePath, String name, BufferedWriter socketWriter) {
        createNewFolder(filePath, name, socketWriter);
    }

    public void createNewFolder(String filePath, String name, BufferedWriter socketWriter){

        //Check there are following commands...how?

        //Create new path from command name
        File newDB = new File(filePath + File.separator + name);

        //Check folder doesn't already exist and create it
        if(newDB.exists()){
            try{
                socketWriter.write("[Error] DB already exists");
                socketWriter.flush();
                return;
            } catch(IOException ioe) {
                ioe.printStackTrace();
                System.out.println(ioe);
            }
        }
        else if(newDB.mkdir() == false) {
            System.exit(0);
        }

    }
}
