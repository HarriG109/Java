package STAGCommand;
import STAGData.LocationData;
import STAGData.PlayerData;

//Class to allow current player to drop items
public class STAGDrop extends STAGProcessCommand {

    //Method to collectItemIfExists
    public void dropItemIfExists(String[] commands, PlayerData currPlayer, LocationData currLoc){

        //Get object name
        String droppedItem = commandIsObject(commands, currPlayer.getPlayerInv(false));

        checkArtefactInInv(droppedItem, currLoc, currPlayer);
        if(!droppedItem.equals("NA")){
            setReturnString("You dropped a " + droppedItem);
        }
        else{
            setReturnString("Item doesn't exist in inventory");
        }
    }

    //Check artefact is in location and remove it if it is
    //Note: Need to get descriptions and also the inventory should present descriptions maybe?
    public void checkArtefactInInv(String commandLoc, LocationData currLoc, PlayerData currPlayer){
        int i;

        for(i = 0; i < currPlayer.getPlayerInv(false).size(); i++){
            if(currPlayer.getPlayerInv(false).get(i).equalsIgnoreCase(commandLoc)){
                //Add item to location
                currLoc.addArtefact(currPlayer.getPlayerInv(false).get(i),
                        currPlayer.getPlayerInv(true).get(i));

                //Remove from player inventory
                currPlayer.removeInv(i);
            }
        }
    }
}
