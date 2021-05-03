package STAGCommand;

import STAGData.LocationData;
import STAGData.PlayerData;

public class STAGDrop extends STAGProcessCommand {

    boolean itemExists;

    public STAGDrop(){
    }

    //Method to collectItemIfExists
    //TODO: Need to evaluate if any object could be two words
    public void dropItemIfExists(String[] commands, PlayerData currPlayer, LocationData currLoc){

        setIndex(getIndex() + 1);

        itemExists = checkArtefactInInv(commands[getIndex()], currLoc, currPlayer);
        if(itemExists){
            setReturnString("You dropped a " + commands[getIndex()]);
        }
        else{
            setReturnString("Item doesn't exist in inventory");
        }
    }

    //Check artefact is in location and remove it if it is
    //Note: Need to get descriptions and also the inventory should present descriptions maybe?
    public boolean checkArtefactInInv(String commandLoc, LocationData currLoc, PlayerData currPlayer){
        int i;

        for(i = 0; i < currPlayer.getPlayerInv().size(); i++){
            if(currPlayer.getPlayerInv().get(i).get(0).equalsIgnoreCase(commandLoc)){
                //Add item to location
                currLoc.addArtefact(currPlayer.getPlayerInv().get(i).get(0), currPlayer.getPlayerInv().get(i).get(1));

                //Remove from player inventory
                currPlayer.removeInv(i);

                return true;
            }
        }
        return false;
    }
}
