import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class UseDB extends DBPath {

    String newDBPath, origDBPath;

    public UseDB(String filePath, String name, BufferedWriter socketWriter){
        setRootPath(setUsePath(name, socketWriter));
    }

    //Convert command string to array for better handling
    public String setUsePath(String name, BufferedWriter socketWriter){

        //Create new file paths from command name
        File newDB = new File(getPath() + File.separator + name);
        File origDB = new File(getPath());

        //Create Strings to store
        newDBPath =  getPath() + File.separator + name;
        origDBPath = getPath();

        if(newDB.isDirectory()){
            return newDBPath;
        }
        else{
            try{
                socketWriter.write("[Error] DB doesn't exist");
                socketWriter.flush();
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
                System.out.println(ioe);
            }
        }
        return origDBPath;
    }
}
