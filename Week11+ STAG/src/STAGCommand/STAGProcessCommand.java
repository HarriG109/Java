package STAGCommand;
import STAGData.ActionsTriggerData;
import STAGData.LocationData;
import STAGData.PlayerData;

import java.util.ArrayList;

public class STAGProcessCommand {

    public static int index;
    //Text to return to console
    public static String returnText;
    public PlayerData currPlayer;
    public LocationData currLoc;

    /*"inventory" (or "inv" for short): lists all of the artefacts currently being carried by the player
        "get x": picks up a specified artefact from current location and puts it into player's inventory
        "drop x": puts down an artefact from player's inventory and places it into the current location
        "goto x": moves from one location to another (if there is a path between the two)
        "look": describes the entities in the current location and lists the paths to other locations*/

    //Constructor which sets current player/location
    public STAGProcessCommand(PlayerData player, ArrayList<LocationData> location) {
        currPlayer = player;
        currLoc = location.get(currPlayer.getPlayerLocIndex());
    }

    //Constructor needed for extends
    public STAGProcessCommand(){

    }

    public void processCommand(String[] commands, ArrayList<LocationData> location, ArrayList<ActionsTriggerData> triggers){

        setIndex(0);

        /*System.out.println(commands[getIndex()]);
        System.out.println(commands[0]);*/

        if(commands[getIndex()].equalsIgnoreCase("inv") ||
                commands[getIndex()].equalsIgnoreCase("inventory")) {

            //Create new instance of inventory
            STAGInv stgi = new STAGInv(currPlayer);
            setReturnString(stgi.getInvString());
        }
        else if(commands[getIndex()].equalsIgnoreCase("get")) {

            //Create new instance of get
            STAGGet stgGet = new STAGGet();
            stgGet.collectItemIfExists(commands, currPlayer, currLoc);
        }
        else if(commands[getIndex()].equalsIgnoreCase("drop")) {

            //Create new instance of get
            STAGDrop stgDrop = new STAGDrop();
            stgDrop.dropItemIfExists(commands, currPlayer, currLoc);
        }
        else if(commands[getIndex()].equalsIgnoreCase("goto")) {

            //Create new instance of goto
            STAGGoTo stgGo = new STAGGoTo();
            //Change the location
            stgGo.alterLoc(commands, currPlayer, currLoc, location);
        }
        else if(commands[getIndex()].equalsIgnoreCase("look")) {

            //Create new instance of look
            STAGLook stgLk = new STAGLook(currLoc);
            setReturnString(stgLk.getLookString());
        }
        else{
            //checkTriggers
            STAGProcessTrigger stgTrig = new STAGProcessTrigger();
            //stgTrig.checkTrigger(commands, currPlayer, currLoc, trigger);
            //Need boolean to state if trigger activated
            //setReturnString("Unknown Action");
        }
    }

    //Method to return string for console
    public String returnString(){
        return returnText;
    }

    //Method to set return String for console
    public void setReturnString(String text){
        returnText = text;
    }

    //Method to set index to walk through commands
    public void setIndex(int i) {
        index = i;
    }

    //Method to return index value
    public int getIndex() {
        return index;
    }
}