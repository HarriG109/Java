import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class DeleteTB {

    public File newTB;
    public String fullFilePath;

    public DeleteTB(String filePath, String name, BufferedWriter socketWriter){

        fullFilePath = filePath + File.separator + name + ".tab";

        //Delete specified table
        removeTB(fullFilePath, socketWriter);
    }

    public void removeTB(String fullFilePath, BufferedWriter socketWriter){

        try {
            newTB = new File(fullFilePath);

            if (newTB.exists()) {
                newTB.delete();
                socketWriter.write("File deleted: " + newTB.getName());
            } else {
                socketWriter.write("[ERROR] File does not exist");
            }
            socketWriter.flush();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe);
        }
    }
}
