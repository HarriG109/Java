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

    //Constructor which sets current player/location
    public STAGProcessCommand(PlayerData player, ArrayList<LocationData> location) {
        currPlayer = player;
        currLoc = location.get(currPlayer.getPlayerLocIndex());
    }

    //Constructor needed for extends
    public STAGProcessCommand(){
    }

    public void processCommand(String[] commands, ArrayList<LocationData> location, ArrayList<ActionsTriggerData> triggers){

        //Set the starting index value
        setIndex(0);

        //Check first command
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
            STAGLook stgLk = new STAGLook();
            //Create look string and set as return string
            setReturnString(stgLk.getLocInfo(currLoc));
        }
        else if(commands[getIndex()].equalsIgnoreCase("health")) {

            setReturnString("Health: " + String.valueOf(currPlayer.getHealth()));
        }
        else{

            //checkTriggers
            STAGProcessTrigger stgTrig = new STAGProcessTrigger();
            stgTrig.processTrigger(commands, currLoc, currPlayer, triggers, location);
        }

        //Check for death
        if(currPlayer.getHealth() == 0){
            playerDeath(currPlayer, currLoc);
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

    //Method to perform death actions
    public void playerDeath(PlayerData currPlayer, LocationData currLoc){

        addInvToLoc(currPlayer, currLoc);
        currPlayer.setHealth(3);
        currPlayer.setPlayerLocIndex(0);
        currPlayer.wipeInv();
        setReturnString(returnString() + "\nYou ran out of health and have died!");

    }

    //Method to move all inventory items into current location
    public void addInvToLoc(PlayerData currPlayer, LocationData currLoc){
        int i;

        for(i=0; i < currPlayer.getPlayerInv().size(); i++){
            currLoc.addArtefact(currPlayer.getPlayerInv().get(i).get(0), currPlayer.getPlayerInv().get(i).get(1));
        }
    }
}