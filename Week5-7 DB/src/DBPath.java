import java.io.File;

public class DBPath {

    public String path;

    public DBPath() {

        if (getPath() == null) {
            setRootPath(System.getProperty("user.dir") + File.separator + "Databases");
            createRoot(getPath());
        }
    }

    //Method to create the root path
    public void setRootPath(String pathname){
        path = pathname;
    }

    public String getPath(){
        return path;
    }

    private static void createRoot(String filePath){

        File newDB = new File(filePath);

        //Return if exists
        if(newDB.exists()){
            return;
        }
        //Else create the folder and check it has done correctly
        else if(newDB.mkdir() == false) {
            System.exit(0);
        }
    }
}

