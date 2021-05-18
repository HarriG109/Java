package STAGCommand;
import STAGData.LocationData;
import STAGData.PlayerData;
import java.util.ArrayList;

public class STAGGoTo extends STAGLook {

    public STAGGoTo(){
    }

    //Method to alter location
    public void alterLoc(String[] commands, PlayerData currPlayer, LocationData currLoc,
                         ArrayList<LocationData> locations, ArrayList<PlayerData> players){

        //Check command line for location path
        String getPath = commandIsObject(commands, currLoc.getPaths());

        if(!getPath.equals("NA")){
            //Update player location
            currPlayer.setPlayerLocIndex(getNewLocIndex(getPath, locations));
            setReturnString(getLocInfo(currPlayer, players, locations));
        }
        else{
            setReturnString("Path doesn't exist");
        }
    }

    //Get new index of location based on comparison with string
    public int getNewLocIndex(String commandLoc, ArrayList<LocationData> locations){
        int i;

        for(i = 0; i < locations.size(); i++){
            if(locations.get(i).getLoc().equals(commandLoc)){
                return i;
            }
        }
        //Should never hit this due to pathExist
        return -1;
    }
}
