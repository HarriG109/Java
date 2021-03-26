package DBCommands;

import java.io.*;

public class DeleteDB {

    public File newTB;
    public String fullFilePath;

    public DeleteDB(String filePath, String name, BufferedWriter socketWriter) {

        try {
            //Create full filepath string and file
            fullFilePath = filePath + File.separator + name;
            newTB = new File(fullFilePath);

            //Delete specified folder and contents
            if (newTB.isDirectory()) {
                removeFolder(newTB);
                socketWriter.write("[OK] Folder deleted does not exist");
            }
            else{
                socketWriter.write("[ERROR] Folder does not exist");
            }
            socketWriter.flush();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe);
        }
    }

    //Recursive method to delete all files/folders within folder specified
    public boolean removeFolder(File folder) {

        File[] contents = folder.listFiles();

        if (contents != null) {
            //Delete subcontents if full of files
            for (File file : contents) {
                removeFolder(file);
            }
        }
        return folder.delete();
    }
}
