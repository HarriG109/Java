import java.io.*;
import java.net.*;

import DBCommands.DBPath;
import DBExceptions.*;

class DBServer
{

    public DBServer(int portNumber)
    {
        try {
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

    private void processNextCommand(BufferedReader socketReader, BufferedWriter socketWriter)
            throws IOException, NullPointerException
    {
        try {
            String incomingCommand = socketReader.readLine();
            DBController newController = new DBController(incomingCommand, socketWriter);
            /*System.out.println("Received message: " + incomingCommand);*/
            socketWriter.write("[OK]");
        }
        catch(SyntaxException e){
            socketWriter.write("[ERROR]: " + e.toString());
        }

        socketWriter.write("\n" + ((char)4) + "\n");
        socketWriter.flush();
    }

    public static void main(String args[])
    {
        DBServer server = new DBServer(8888);
    }
}
