package STAGCommand;
import STAGData.LocationData;
import STAGData.PlayerData;
import java.util.ArrayList;

public class STAGGoTo extends STAGLook {

    public boolean exists;

    public STAGGoTo(){
    }

    //Method to alter location
    public void alterLoc(String[] commands, PlayerData currPlayer, LocationData currLoc, ArrayList<LocationData> locations){

        //Increment index
        setIndex(getIndex() + 1);

        exists = pathExist(commands[getIndex()], currLoc.getPaths());
        if(exists){
            //Update player location
            currPlayer.setPlayerLocIndex(getNewLocIndex(commands[getIndex()], locations));
            setReturnString(getLocInfo(currLoc));
        }
        else{
            setReturnString("Path doesn't exist");
        }
    }

    //Check path exists in location
    public boolean pathExist(String commandLoc, ArrayList<String> paths){
        int i;

        for(i = 0; i < paths.size(); i++){
            if(paths.get(i).equals(commandLoc)){
                return true;
            }
        }
        return false;
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
