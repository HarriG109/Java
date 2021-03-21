import java.io.*;
import java.net.*;
import java.util.*;
import DBExceptions.*;

class DBServer
{

    public DBServer(int portNumber)
    {
        try {
            /*Create the path method
            createRoot();*/
            DBPath createPath = new DBPath();
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            while(true) processNextConnection(serverSocket);
        } catch(IOException ioe) {
            ioe.printStackTrace();
            System.err.println(ioe);
        }
    }

    private void processNextConnection(ServerSocket serverSocket)
    {
        try {
            Socket socket = serverSocket.accept();
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Connection Established");
            while(true) processNextCommand(socketReader, socketWriter);
        } catch(IOException ioe) {
            ioe.printStackTrace();
            System.err.println(ioe);
        } catch(NullPointerException npe) {
            npe.printStackTrace();
            System.out.println("Connection Lost");
        }
    }

    private void processNextCommand(BufferedReader socketReader, BufferedWriter socketWriter) throws IOException, NullPointerException
    {
        try {
            String incomingCommand = socketReader.readLine();
            DBController parse = new DBController(incomingCommand, socketWriter);
            System.out.println("Received message: " + incomingCommand);
            socketWriter.write("[OK] Thanks for your message: " + incomingCommand);
            socketWriter.write("\n" + ((char)4) + "\n");
            socketWriter.flush();
        }
        catch(SyntaxException exception){
            exception.printStackTrace();
            System.out.println("DB Exception: " + exception);
        }
    }

    public static void main(String args[])
    {
        DBServer server = new DBServer(8888);
    }

    /*Create top level root folder if it doesn't exist
    private static void createRoot(){

        File newTopLevelDB = new File(System.getProperty("user.dir") + File.separator + "Databases");

        //Return if exists
        if(newTopLevelDB.exists()){
            return;
        }
        //Else create the folder and check it has done correctly
        else if(newTopLevelDB.mkdir() == false) {
            System.exit(0);
        }
    }*/

}
