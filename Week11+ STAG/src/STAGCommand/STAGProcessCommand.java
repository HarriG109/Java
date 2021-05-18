package STAGCommand;
import STAGData.ActionsTriggerData;
import STAGData.LocationData;
import STAGData.PlayerData;
import java.util.ArrayList;

public class STAGProcessCommand {

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

    public void processCommand(String[] commands, ArrayList<LocationData> location,
                               ArrayList<ActionsTriggerData> triggers, ArrayList<PlayerData> players){

        //Check first command
        if(checkExpectedCommand(commands, "inv") ||
                checkExpectedCommand(commands, "inventory")) {

            //Create new instance of inventory
            STAGInv stgi = new STAGInv(currPlayer);
            setReturnString(stgi.getInvString());
        }
        else if(checkExpectedCommand(commands, "get")) {

            //Create new instance of get
            STAGGet stgGet = new STAGGet();
            stgGet.collectItemIfExists(commands, currPlayer, currLoc);
        }
        else if(checkExpectedCommand(commands, "drop")) {

            //Create new instance of get
            STAGDrop stgDrop = new STAGDrop();
            stgDrop.dropItemIfExists(commands, currPlayer, currLoc);
        }
        else if(checkExpectedCommand(commands, "goto")) {

            //Create new instance of goto
            STAGGoTo stgGo = new STAGGoTo();
            //Change the location
            stgGo.alterLoc(commands, currPlayer, currLoc, location, players);
        }
        else if(checkExpectedCommand(commands, "look")) {

            //Create new instance of look
            STAGLook stgLk = new STAGLook();
            //Create look string and set as return string
            setReturnString(stgLk.getLocInfo(currLoc, currPlayer, players, location));
        }
        else if(checkExpectedCommand(commands, "health")) {

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

            //Create new instance of look
            STAGLook stgLk = new STAGLook();
            //Create look string and set as return string
            setReturnString(stgLk.getLocInfo(currLoc, currPlayer, players, location));
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

    //Method to perform death actions
    public void playerDeath(PlayerData currPlayer, LocationData currLoc){

        addInvToLoc(currPlayer, currLoc);
        currPlayer.setHealth(3);
        currPlayer.setPlayerLocIndex(0);
        currPlayer.wipeInv();
        setReturnString(returnString() + "\nYou ran out of health and have died!");

    }

    //Method to move all player inventory items into current location
    public void addInvToLoc(PlayerData currPlayer, LocationData currLoc){
        int i;

        for(i=0; i < currPlayer.getPlayerInv(false).size(); i++){
            currLoc.addArtefact(currPlayer.getPlayerInv(false).get(i), currPlayer.getPlayerInv(true).get(i));
        }
    }

    //Find instance of word within tokens
    public boolean checkExpectedCommand(String[] commands, String command){

        for(String s: commands){

            if(s.equalsIgnoreCase(command)){
                return true;
            }
        }
        return false;
    }

    //See if instance of word within tokens matches object in list
    public String commandIsObject(String[] commands, ArrayList<String> list){

        for(String s: commands){
            for(String l: list){
                if(s.equalsIgnoreCase(l)){
                    return l;
                }
            }
        }
        return "NA";
    }
}