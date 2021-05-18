import STAGCommand.STAGProcessCommand;
import STAGData.*;
import java.io.*;
import java.net.*;
import java.util.*;

class StagServer
{
    public static ArrayList<LocationData> locations = new ArrayList<LocationData>();
    public static ArrayList<ActionsTriggerData> triggers = new ArrayList<ActionsTriggerData>();
    public static ArrayList<PlayerData> players = new ArrayList<PlayerData>();

    public static void main(String args[])
    {
        if(args.length != 2) System.out.println("Usage: java StagServer <entity-file> <action-file>");
        else new StagServer(args[0], args[1], 8888);
    }

    public StagServer(String entityFilename, String actionFilename, int portNumber)
    {
        try {
            ServerSocket ss = new ServerSocket(portNumber);

            //Call parsers
            JSONFileParser JSONfp = new JSONFileParser();
            JSONfp.importActions(actionFilename, triggers);

            GraphParserExample GraphPE = new  GraphParserExample();
            GraphPE.importEntities(entityFilename, locations);

            System.out.println("Server Listening");
            while(true) acceptNextConnection(ss);
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void acceptNextConnection(ServerSocket ss)
    {
        try {
            // Next line will block until a connection is received
            Socket socket = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            processNextCommand(in, out);
            out.close();
            in.close();
            socket.close();
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processNextCommand(BufferedReader in, BufferedWriter out) throws IOException
    {
        //Create incoming commands as string
        String line = in.readLine();
        out.write("You said... " + line + "\n");

        //Instantiate object which breaks the incoming command into tokens and get current player
        STAGHandleIncomingCommand newCommand = new STAGHandleIncomingCommand(line);
        //Add player if new
        newCommand.addPlayerIfNew(players);

        //Instantiate object which captures the current player/location
        STAGProcessCommand pCommand = new STAGProcessCommand(players.get(newCommand.getCurrentPlayerIndex()), locations);
        //Process the command
        pCommand.processCommand(newCommand.getTokenArray(), locations, triggers, players);

        //Print return text
        if(pCommand.returnString() != null) {
            out.write(pCommand.returnString());
        }
    }
}
