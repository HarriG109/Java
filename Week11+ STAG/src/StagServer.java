import STAGData.*;
import java.io.*;
import java.net.*;
import java.util.*;

class StagServer
{
    public static ArrayList<LocationData> location = new ArrayList<LocationData>();
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
            JSONFileParser JSONfp = new JSONFileParser(actionFilename, triggers);

            /*System.out.println(triggers.get(0).getTrig());
            System.out.println(triggers.get(0).getNarr());
            System.out.println(triggers.get(0).getSubject(1));
            System.out.println(triggers.get(0).getConsumed(0));
            System.out.println(triggers.get(0).getProduced(0));*/
            GraphParserExample GraphPE = new  GraphParserExample(entityFilename, location);


            /*System.out.println(location.get(0).getLoc());
            System.out.println(location.get(1).getLoc());
            System.out.println(location.get(2).getLoc());
            System.out.println(location.get(0).getPath(0));
            System.out.println(location.get(1).getPath(0));
            System.out.println(location.get(2).getPath(0));*/
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
        //See DB processNextCommand for setup
        //try {
        //    String incomingCommand = in.readLine();
        String line = in.readLine();
        out.write("You said... " + line + "\n");

        STAGController newController = new STAGController(line, players);
        System.out.println(players.get(0).getPlayer());
        //}
        //catch {
        //}
    }
}
