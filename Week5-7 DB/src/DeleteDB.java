import java.io.*;

public class DeleteDB {

    public File newTB;
    public String fullFilePath, defaultPath;

    public DeleteDB(String filePath, String name, BufferedWriter socketWriter) {

        //Create full filepath string and file
        fullFilePath = filePath + File.separator + name;
        newTB = new File(fullFilePath);

        //Check folder is empty
        if (checkEmpty(newTB, socketWriter) == true) {

            //Delete specified table
            removeFolder(newTB, socketWriter);
        }
    }

    public void removeFolder(File folder, BufferedWriter socketWriter){

        try {
            folder.delete();
            socketWriter.write("Folder deleted: " + folder.getName());
            socketWriter.flush();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe);
        }
    }

    //Method to get check for empty folder
    public boolean checkEmpty(File folder, BufferedWriter socketWriter) {

        File[] contents = folder.listFiles();

        try {

            // the directory file is not really a directory..
            if (contents == null) {
                socketWriter.write("[ERROR] Folder does not exist");
                socketWriter.flush();
            }
            // Folder is empty
            else if (contents.length == 0) {
                return true;
            }
            // Folder contains files
            else {
                socketWriter.write("[ERROR] Folder contains files");
                socketWriter.flush();
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe);
        }

        return false;
    }
}
